package ie.setu.placemark.ui.screens.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlinx.coroutines.flow.update

enum class SortOption(val isAscending: Boolean) {
    DATE_ASC(true),
    DATE_DESC(false),
    DISTANCE_ASC(true),
    DISTANCE_DESC(false);

    val field: String
        get() = when (this) {
            DATE_ASC, DATE_DESC -> "Date"
            DISTANCE_ASC, DISTANCE_DESC -> "Distance"
        }
}

@HiltViewModel
class ReportViewModel @Inject
constructor(
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {
    private val _runs
            = MutableStateFlow<List<RunModel>>(emptyList())
    val uiRuns: StateFlow<List<RunModel>>
            = _runs.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    // Use mutableStateOf for userProfile
    var userProfile = mutableStateOf<UserProfileModel?>(null)

    var sortOption = mutableStateOf(SortOption.DATE_DESC)


    init {
        getRuns()
        getUserProfiles()
    }

    fun getRuns() {
        viewModelScope.launch {
            try {
                isloading.value = true
                repository.getAll(authService.email!!).collect { items ->
                    _runs.value = items
                    iserror.value = false
                    isloading.value = false
                }
                Timber.i("DVM RVM = : ${_runs.value}")
            }
            catch(e:Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

    fun deleteRun(run: RunModel)
        = viewModelScope.launch {
            repository.delete(authService.email!!,run._id)
        }

    fun setSortOption(option: SortOption) {
        sortOption.value = option
        _runs.update { sortRuns(it, option) }
    }

    private fun sortRuns(runs: List<RunModel>, option: SortOption): List<RunModel> {
        return when (option) {
            SortOption.DATE_ASC -> runs.sortedBy { it.dateRan }
            SortOption.DATE_DESC -> runs.sortedByDescending { it.dateRan }
            SortOption.DISTANCE_ASC -> runs.sortedBy { it.distanceAmount }
            SortOption.DISTANCE_DESC -> runs.sortedByDescending { it.distanceAmount }
        }
    }

    fun getUserProfiles() {
        viewModelScope.launch {
            try {
                isloading.value = true
//                val userProfileItem = repository.getUserProfile(authService.email!!)
                userProfile.value = repository.getUserProfile(authService.email!!)!!
                iserror.value = false
                isloading.value = false
                Timber.i("DVM RVM = : ${userProfile.value}")
            } catch (e: Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }
}

