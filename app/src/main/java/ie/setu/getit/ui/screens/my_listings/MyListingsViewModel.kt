package ie.setu.getit.ui.screens.my_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.AuthService
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListingsViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    authService: AuthService
) : ViewModel() {

    private val userId = authService.getCurrentUserId()!!

    private val _userListings = MutableStateFlow<List<ListingModel>>(emptyList())
    val userListings: StateFlow<List<ListingModel>> = _userListings.asStateFlow()

    init {
        loadUserListings()
    }

    private fun loadUserListings() {
        viewModelScope.launch {
            when (val result = firestoreService.getListingsForUser(userId)) {
                is Response.Success -> _userListings.value = result.result
                is Response.Failure -> _userListings.value = emptyList() // handle failure
                Response.Loading -> TODO()
            }
        }
    }

    fun deleteListing(listing: ListingModel) {
        viewModelScope.launch {
            firestoreService.deleteListing(listing.id)
            loadUserListings() // Refresh list after deletion
        }
    }
}