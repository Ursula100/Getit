package ie.setu.getit.ui.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject
constructor(
    private val repository: RoomRepository,
    savedStateHandle: SavedStateHandle // to get listing id passed via nav
) : ViewModel() {

    // Holds the listing for edit screen
    var listing = mutableStateOf(ListingModel())

    // Get the id from the nav graph
    val id: Int? = savedStateHandle["id"]

    init {
        if (id != null) {
            // If an id is passed, fetch the existing listing from the repository
            viewModelScope.launch {
                repository.get(id).collect { existingListing ->
                    listing.value = existingListing
                }
            }
        }
    }

    fun insert(listing: ListingModel) = viewModelScope.launch {
        repository.insert(listing)
    }

    fun update(listing: ListingModel) = viewModelScope.launch {
        repository.update(listing)
    }
}
