package ie.setu.placemark.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.data.room.RoomRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ReportViewModel @Inject
constructor(private val repository: RetrofitRepository) : ViewModel() {
    private val _runs
            = MutableStateFlow<List<RunModel>>(emptyList())
    val uiRuns: StateFlow<List<RunModel>>
            = _runs.asStateFlow()
    var isErr = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

//    init {
//        viewModelScope.launch {
//            repository.getAll().collect { listOfRuns ->
//                _runs.value = listOfRuns
//            }
//        }
//    }

    init { getRuns() }

    fun getRuns() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                _runs.value = repository.getAll()
                isErr.value = false
                isLoading.value = false
            }
            catch(e:Exception) {
                isErr.value = true
                isLoading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteRun(run: RunModel) {
        viewModelScope.launch {
               repository.delete(run)
        }
    }
}

