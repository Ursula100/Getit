package ie.setu.getit.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.data.ListingModel
import ie.setu.getit.data.repository.RoomRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {

    fun insert(listings: ListingModel)
            = viewModelScope.launch {
        repository.insert(listings)
    }
}
