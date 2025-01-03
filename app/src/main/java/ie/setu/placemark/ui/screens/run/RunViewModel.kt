package ie.setu.placemark.ui.screens.run

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RunViewModel @Inject
constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
)
    : ViewModel() {
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    private val _unitType = MutableStateFlow("kilometres")
    val unitType: StateFlow<String> = _unitType

    init {
        viewModelScope.launch {
            val userProfile = repository.getUserProfile(authService.email!!)
            _unitType.value = userProfile?.preferredUnit ?: "kilometres"
        }
    }

    fun insert(run: RunModel) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.insert(authService.email!!, run)
                isErr.value = false
                isLoading.value = false
                timber.log.Timber.i("Run Inserted for ${authService.email!!}")
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
}

