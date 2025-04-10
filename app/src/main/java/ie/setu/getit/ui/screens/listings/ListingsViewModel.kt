package ie.setu.getit.ui.screens.listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListingsViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {
    private val _listings
            = MutableStateFlow<List<ListingModel>>(emptyList())
    val uiListings: StateFlow<List<ListingModel>>
            = _listings.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listOfListings ->
                _listings.value = listOfListings
            }
        }
    }
}
