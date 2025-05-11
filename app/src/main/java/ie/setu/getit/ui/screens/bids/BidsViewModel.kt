package ie.setu.getit.ui.screens.bids

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.BidModel
import ie.setu.getit.data.model.BidStatus
import ie.setu.getit.data.repository.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BidsViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val userId = 1 // Simulate logged-in user

    var myBids = mutableStateListOf<BidModel>()

    init {
        viewModelScope.launch {
            repository.getBidsForUser(userId).collect { allBids ->
                myBids.clear()
                myBids.addAll(allBids)
            }
        }
    }

    val topBids: List<BidModel>
        get() = myBids.filter { it.status == BidStatus.TOP }

    val lostBids: List<BidModel>
        get() = myBids.filter { it.status != BidStatus.TOP }
}
