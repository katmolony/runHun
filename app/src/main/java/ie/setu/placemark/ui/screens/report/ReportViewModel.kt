package ie.setu.placemark.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ReportViewModel @Inject
constructor(
    private val repository: RetrofitRepository,
    private val authService: AuthService
) : ViewModel() {
    private val _runs
            = MutableStateFlow<List<RunModel>>(emptyList())
    val uiRuns: StateFlow<List<RunModel>>
            = _runs.asStateFlow()
    var isErr = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    // Use mutableStateOf for userProfile
    var userProfile = mutableStateOf<UserProfileModel?>(null)


    init {
        getRuns()
        getUserProfiles()
    }

    fun getRuns() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                _runs.value = repository.getAll(authService.email!!)
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
            repository.delete(authService.email!!,run)
        }
    }

    fun getUserProfiles() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                Timber.i("Fetching profile") // Log the ID
                // Fetch the user profile and update the state
                val fetchedProfile = repository.getUserProfile(authService.email!!)
                Timber.i("Profile fetched: $fetchedProfile") // Log the response
                userProfile.value = fetchedProfile  // Update the state
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
}

