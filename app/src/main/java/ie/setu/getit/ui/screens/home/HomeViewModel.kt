package ie.setu.getit.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.model.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirestoreService
) : ViewModel() {

    private val _featuredListings = MutableStateFlow<List<ListingModel>>(emptyList())
    val featuredListings: StateFlow<List<ListingModel>> = _featuredListings.asStateFlow()

    init {
        viewModelScope.launch {
            when (val response = firestore.getAllListings()) {
                is Response.Success -> {
                    _featuredListings.value = response.result.take(4)
                }
                is Response.Failure -> {
                    // Handle error logging if necessary
                }
                Response.Loading -> Unit
            }
        }
    }
}
