package ie.setu.getit.ui.screens.listing_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.BidModel
import ie.setu.getit.data.BidStatus
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val repository: RoomRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val listingId: Int = checkNotNull(savedStateHandle["id"])

    var listing = mutableStateOf<ListingModel?>(null)
        private set

    var bids = mutableStateListOf<BidModel>()
        private set

    val currentTopBid: Int?
        get() = bids.maxByOrNull { it.amount }?.amount

    init {
        viewModelScope.launch {
            repository.get(listingId).collect {
                listing.value = it
            }
        }

        viewModelScope.launch {
            repository.getBidsForListing(listingId).collect {
                bids.clear()
                bids.addAll(it.sortedByDescending { bid -> bid.amount })
            }
        }
    }

    fun placeBid(amount: Int) {
        val listing = listing.value ?: return
        val previousTopBid = bids.maxByOrNull { it.amount }

        // New bid must be higher than current top bid, or equal to listing price if no bids exist
        val baseAmount = previousTopBid?.amount ?: listing.price
        if (previousTopBid != null && amount <= baseAmount) return
        if (previousTopBid == null && amount < baseAmount) return

        viewModelScope.launch {
            //Downgrade previous top bid (if exists)
            previousTopBid?.let {
                repository.updateBid(it.copy(status = BidStatus.OUTBID))
            }

            //Add new top bid
            val newTopBid = BidModel(
                listingId = listing.id,
                userId = 1, // placeholder
                amount = amount,
                status = BidStatus.TOP,
                bidTime = Date()
            )
            repository.insertBid(newTopBid)

            viewModelScope.launch {
                repository.insertBid(newTopBid)
            }
        }
    }
}