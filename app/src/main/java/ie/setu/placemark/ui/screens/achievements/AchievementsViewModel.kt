package ie.setu.placemark.ui.screens.achievements


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.firebase.services.AuthService
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: RetrofitRepository,
    private val authService: AuthService
) : ViewModel() {

    private val _uiRuns = MutableStateFlow<List<RunModel>>(emptyList())
    val uiRuns: StateFlow<List<RunModel>> = _uiRuns.asStateFlow()

    private val _mostRecentRun = MutableStateFlow<RunModel?>(null)
    val mostRecentRun: StateFlow<RunModel?> = _mostRecentRun.asStateFlow()

    private val _longestRun = MutableStateFlow<RunModel?>(null)
    val longestRun: StateFlow<RunModel?> = _longestRun.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _error = MutableStateFlow<Exception?>(null)
    val error: StateFlow<Exception?> = _error.asStateFlow()

    init {
        getAchievements()
    }

    fun getRuns() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val runs = repository.getAll(authService.email!!)
                _uiRuns.value = runs
            } catch (e: Exception) {
                _isError.value = true
                _error.value = e
                Timber.e("Error fetching runs: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getAchievements() {
        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            try {
                val runs = repository.getAll(authService.email!!)
                if (runs.isNotEmpty()) {
                    _mostRecentRun.value = runs.maxByOrNull { it.dateRan.time }
                    _longestRun.value = runs.maxByOrNull { it.distanceAmount }
                }
            } catch (e: Exception) {
                _isError.value = true
                _error.value = e
                Timber.e("Error fetching achievements: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
