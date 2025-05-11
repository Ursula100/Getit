package ie.setu.getit.ui.screens.my_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListingsViewModel @Inject constructor(
    private val repository: RoomRepository,
    authService: AuthService
) : ViewModel() {

    private val userId = authService.getCurrentUserId()!! // Simulate a logged-in user

    private val _userListings = MutableStateFlow<List<ListingModel>>(emptyList())
    val userListings: StateFlow<List<ListingModel>> = _userListings.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getListingsForUser(userId).collect { listings ->
                _userListings.value = listings
            }
        }
    }

    fun deleteListing(listing: ListingModel) {
        viewModelScope.launch {
            repository.delete(listing)
        }
    }
}