package ie.setu.placemark.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.firebase.services.AuthService
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    var name = mutableStateOf("")
    var email = mutableStateOf("")
    val currentUser: FirebaseUser?
        get() = authService.currentUser

    init {
        if (currentUser != null) {
            name.value = currentUser!!.displayName.toString()
            email.value = currentUser!!.email.toString()
            Timber.i("User email: ${email.value} User name: ${name.value}")
        } else {
            Timber.i("No user is currently logged in.")
        }
    }

    fun isAuthenticated() = authService.isUserAuthenticatedInFirebase

}