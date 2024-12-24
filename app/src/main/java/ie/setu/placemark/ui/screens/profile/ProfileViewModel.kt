package ie.setu.placemark.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.firebase.services.AuthService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val auth: FirebaseAuth,
) : ViewModel() {

    val displayName get() = auth.currentUser?.displayName.toString()
    val photoUrl get() = auth.currentUser?.photoUrl.toString()

    fun signOut() {
        viewModelScope.launch { authService.signOut() }
    }
}