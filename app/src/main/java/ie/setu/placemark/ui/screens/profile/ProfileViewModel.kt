package ie.setu.placemark.ui.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.data.model.RunModel
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val auth: FirebaseAuth,
    private val repository: FirestoreService,
) : ViewModel() {

    val displayName get() = auth.currentUser?.displayName.toString()
    val photoUrl get() = auth.currentUser?.photoUrl.toString()
    val email get() = auth.currentUser?.email.toString()
    val userId get() = auth.currentUser?.uid.toString()

    val photoUri get() = authService.customPhotoUri

    // Use mutableStateOf for userProfile
    var userProfile = mutableStateOf<UserProfileModel?>(null)

    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                Timber.i("Fetching profile") // Log the ID
                // Fetch the user profile and update the state
                val fetchedProfile = repository.getUserProfile(email)!!
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

    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }
}
