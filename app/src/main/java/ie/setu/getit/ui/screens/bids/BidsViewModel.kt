package ie.setu.getit.ui.screens.bids

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.BidModel
import ie.setu.getit.data.model.BidStatus
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BidsViewModel @Inject constructor(
    private val firestore: FirestoreService,
    authService: AuthService
) : ViewModel() {

    private val userId = authService.getCurrentUserId() ?: ""

    var myBids = mutableStateListOf<BidModel>()

    init {
        viewModelScope.launch {
            when (val result = firestore.getBidsForUser(userId)) {
                is Response.Success -> {
                    myBids.clear()
                    myBids.addAll(result.result)
                }

                is Response.Failure -> {
                    // Will handle later (log or show error state)
                }

                else -> Unit
            }
        }
    }

    val topBids: List<BidModel>
        get() = myBids.filter { it.status == BidStatus.TOP }

    val lostBids: List<BidModel>
        get() = myBids.filter { it.status != BidStatus.TOP }
}
