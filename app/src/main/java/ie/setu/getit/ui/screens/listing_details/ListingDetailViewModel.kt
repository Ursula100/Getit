package ie.setu.getit.ui.screens.listing_details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.BidModel
import ie.setu.getit.data.model.BidStatus
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val firestore: FirestoreService,
    savedStateHandle: SavedStateHandle,
    authService: AuthService
) : ViewModel() {

    private val listingId: String = checkNotNull(savedStateHandle["id"])
    private val loggedUserId = authService.getCurrentUserId()!!

    var listing = mutableStateOf<ListingModel?>(null)
        private set

    var bids = mutableStateListOf<BidModel>()
        private set

    val currentTopBid: Int?
        get() = bids.maxByOrNull { it.amount }?.amount

    init {
        fetchListing()
        fetchBids()
    }

    private fun fetchListing() {
        viewModelScope.launch {
            when (val result = firestore.getListingById(listingId)) {
                is Response.Success -> listing.value = result.result
                else -> {} // Handle error if needed
            }
        }
    }

    private fun fetchBids() {
        viewModelScope.launch {
            when (val result = firestore.getBidsForListing(listingId)) {
                is Response.Success -> {
                    bids.clear()
                    bids.addAll(result.result.sortedByDescending { it.amount })
                }
                else -> {} // Handle error if needed
            }
        }
    }

    fun placeBid(amount: Int) {
        val currentListing = listing.value ?: return
        val previousTopBid = bids.maxByOrNull { it.amount }

        val baseAmount = previousTopBid?.amount ?: currentListing.price
        if (previousTopBid != null && amount <= baseAmount) return
        if (previousTopBid == null && amount < baseAmount) return

        viewModelScope.launch {
            // Update previous top bid's status if exists
            previousTopBid?.let { bid ->
                firestore.updateBidStatus(bid.id, BidStatus.OUTBID)
            }

            val newTopBid = BidModel(
                listingId = listingId,
                userId = loggedUserId,
                amount = amount,
                status = BidStatus.TOP,
                bidTime = Date()
            )

            firestore.saveBid(newTopBid)
            fetchBids() // Refresh bids
        }
    }

    fun topFiveBids(): List<BidModel> {
        return bids.sortedByDescending { it.amount }.take(5)
    }
}