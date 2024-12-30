package ie.setu.placemark.ui.screens.achievements

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import ie.setu.placemark.ui.components.badges.Badge
import ie.setu.placemark.ui.components.badges.BadgeAdapter
import ie.setu.placemark.ui.components.badges.badges
import kotlinx.coroutines.async
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val authService: AuthService,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _runs = MutableStateFlow<List<RunModel>>(emptyList())
    val uiRuns: StateFlow<List<RunModel>> = _runs.asStateFlow()

    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    private val _longestRun = MutableStateFlow<RunModel?>(null)
    val longestRun: StateFlow<RunModel?> = _longestRun.asStateFlow()

    private val _mostRecentRun = MutableStateFlow<RunModel?>(null)
    val mostRecentRun: StateFlow<RunModel?> = _mostRecentRun.asStateFlow()

    // Use mutableStateOf for userProfile
    var userProfile = mutableStateOf<UserProfileModel?>(null)
    val email get() = auth.currentUser?.email.toString()

    init {
        viewModelScope.launch {
            getAchievements()
            val profile = repository.getUserProfile(email)
            if (profile != null) {
                userProfile.value = profile
                Timber.i("User profile loaded: $profile")
            }
        }
    }


    fun getAwardedBadges(userProfile: UserProfileModel): List<Badge> {
        return badges.filter { badge -> badge.condition(userProfile) }
    }

    fun setupBadgeCarousel(userProfile: UserProfileModel, recyclerView: RecyclerView) {
        val awardedBadges = getAwardedBadges(userProfile)
        val badgeAdapter = BadgeAdapter(awardedBadges)

        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = badgeAdapter
    }

    fun fetchAchievements() {
        viewModelScope.launch {
            try {
                val email = authService.email ?: return@launch
                Timber.i("Fetching achievements for email: $email")

                // Fetch longest and most recent runs concurrently
                val longestRunDeferred = async { repository.getLongestRun(email) }
                val mostRecentRunDeferred = async { repository.getMostRecentRun(email) }

                _longestRun.value = longestRunDeferred.await()
                _mostRecentRun.value = mostRecentRunDeferred.await()

                Timber.i("Longest Run: ${_longestRun.value}")
                Timber.i("Most Recent Run: ${_mostRecentRun.value}")
            } catch (e: Exception) {
                Timber.e("Error fetching achievements: ${e.message}")
            }
        }
    }

    fun getAchievements() {
        viewModelScope.launch {
            try {
                isloading.value = true
                Timber.i("Fetching all runs for email: ${authService.email}")
                repository.getAll(authService.email!!).collect { items ->
                    Timber.i("Fetched runs: $items")
                    _runs.value = items
                    iserror.value = false
                    isloading.value = false
                }
            } catch (e: Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.e("Error fetching achievements: ${e.message}")
            }
        }
    }
}