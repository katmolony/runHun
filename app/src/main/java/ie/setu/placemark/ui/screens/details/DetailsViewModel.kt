package ie.setu.placemark.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(private val repository: FirestoreService,
            savedStateHandle: SavedStateHandle,
            private val authService: AuthService
) : ViewModel() {

    var run = mutableStateOf(RunModel())
    val id: String = checkNotNull(savedStateHandle["id"])
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)



    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                Timber.i("DetailsViewModel. Fetching run with id: $id") // Log the ID
                run.value = repository.get(authService.email!!,id)!!
                val fetchedRun = repository.get(authService.email!!,id)!!
                Timber.i("DetailsViewModel. Run fetched: $fetchedRun") // Log the response
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                Timber.i("DetailsViewModel. Error fetching run: ${e.message}", e) // Log the error
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }

    fun updateRun(run: RunModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.update(authService.email!!, run)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }
}

