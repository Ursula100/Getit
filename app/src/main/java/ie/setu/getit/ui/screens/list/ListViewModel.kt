package ie.setu.getit.ui.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val firestore: FirestoreService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var listing = mutableStateOf(ListingModel(uid = ""))  // Will be set properly below

    val id: String? = savedStateHandle["id"]  // Make sure your routes use String for Firestore IDs

    init {
        if (id != null) {
            viewModelScope.launch {
                when (val result = firestore.getListingById(id)) {
                    is Response.Success -> {
                        listing.value = result.result
                    }
                    is Response.Failure -> {
                        // Optionally log error
                    }
                    Response.Loading -> Unit
                }
            }
        }
    }

    fun insert(listing: ListingModel) = viewModelScope.launch {
        firestore.saveListing(listing)
    }

    fun update(listing: ListingModel) = viewModelScope.launch {
        firestore.updateListing(listing)
    }
}
