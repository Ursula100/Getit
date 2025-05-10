package ie.setu.getit.ui.screens.my_listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListingsViewModel @Inject constructor(
    private val repository: RoomRepository
) : ViewModel() {

    private val userId = 1 // Simulate a logged-in user

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