package ie.setu.getit.ui.screens.listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.FirestoreService
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListingsViewModel @Inject constructor(
    private val firestoreService: FirestoreService
) : ViewModel() {

    private val _uiListings = MutableStateFlow<List<ListingModel>>(emptyList())
    val uiListings: StateFlow<List<ListingModel>> = _uiListings.asStateFlow()

    init {
        getListings()
    }

    private fun getListings() {
        viewModelScope.launch {
            when (val result = firestoreService.getAllListings()) {
                is Response.Success -> _uiListings.value = result.result
                is Response.Failure -> {
                    // Optionally handle error
                    _uiListings.value = emptyList()
                }
                Response.Loading -> TODO()
            }
        }
    }

    fun deleteListing(listing: ListingModel) {
        viewModelScope.launch {
            firestoreService.deleteListing(listing.id)
            getListings() // Refresh listings after deletion
        }
    }
}
