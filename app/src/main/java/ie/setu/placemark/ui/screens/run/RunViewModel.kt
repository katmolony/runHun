package ie.setu.placemark.ui.screens.run

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RunViewModel @Inject
constructor(private val repository: RetrofitRepository)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

//    fun insert(run: RunModel)
//            = viewModelScope.launch {
//                repository.insert(run)
//    }

    fun insert(run: RunModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(run)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
}

