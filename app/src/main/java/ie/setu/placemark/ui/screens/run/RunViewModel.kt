package ie.setu.placemark.ui.screens.run

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.RunModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import ie.setu.placemark.data.room.RoomRepository

@HiltViewModel
class RunViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {

    fun insert(runs: RunModel)
            = viewModelScope.launch {
        repository.insert(runs)
    }
}