//package ie.setu.placemark.ui.screens.achievements
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import ie.setu.placemark.data.model.RunModel
//import ie.setu.placemark.data.api.RetrofitRepository
//import ie.setu.placemark.firebase.services.AuthService
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import timber.log.Timber
//
//@HiltViewModel
//class AchievementsViewModel @Inject constructor(
//    private val repository: RetrofitRepository,
//    private val authService: AuthService
//) : ViewModel() {
//
//    private val _runs
//            = MutableStateFlow<List<RunModel>>(emptyList())
//    val uiRuns: StateFlow<List<RunModel>>
//            = _runs.asStateFlow()
//
//    // Most recent and longest run states
//    val mostRecentRun = mutableStateOf<RunModel?>(null)
//    val longestRun = mutableStateOf<RunModel?>(null)
//
//    // Loading and error state
//    var isLoading = mutableStateOf(false)
//    var isError = mutableStateOf(false)
//    var error = mutableStateOf<Exception?>(null)
//
//    init {
//        getAchievements()
//    }
//
//    fun getAchievements() {
//        viewModelScope.launch {
//            try {
//                isLoading.value = true
//                // Fetch all runs for the logged-in user
//                val runs = repository.getAll(authService.email!!)
//
//                // Check if runs list is not empty and compute achievements
//                if (runs.isNotEmpty()) {
//                    mostRecentRun.value = runs.maxByOrNull { it.dateRan.time } // Most recent run
//                    longestRun.value = runs.maxByOrNull { it.distanceAmount } // Longest distance run
//                }
//
//                // Reset error state on success
//                isError.value = false
//                error.value = null
//                isLoading.value = false
//            } catch (e: Exception) {
//                // Handle errors gracefully
//                isError.value = true
//                isLoading.value = false
//                error.value = e
//                Timber.e("AchievementsViewModel Error: ${e.message}")
//            }
//        }
//    }
//}
