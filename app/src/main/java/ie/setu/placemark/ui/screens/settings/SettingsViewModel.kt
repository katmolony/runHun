package ie.setu.placemark.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirestoreService
import ie.setu.placemark.ui.theme.ThemeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeViewModel: ThemeViewModel,
    private val repository: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    private val _isDarkTheme = MutableStateFlow(themeViewModel.isDarkTheme.value)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _unitType = MutableStateFlow("kilometres")
    val unitType: StateFlow<String> = _unitType

    init {
        viewModelScope.launch {
            val userProfile = repository.getUserProfile(authService.email!!)
            _unitType.value = userProfile?.preferredUnit ?: "kilometres"
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            _isDarkTheme.value = newTheme
            themeViewModel.setDarkTheme(newTheme)
        }
    }

    fun toggleUnitType() {
        viewModelScope.launch {
            val newUnitType = if (_unitType.value == "kilometres") "miles" else "kilometres"
            _unitType.value = newUnitType
            updateUnitType(newUnitType)
        }
    }

    fun updateUnitType(newUnitType: String) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val userProfile = repository.getUserProfile(authService.email!!)
                if (userProfile != null) {
                    userProfile.preferredUnit = newUnitType
                    repository.updateUserProfile(authService.email!!, userProfile)
                    // Log the updated userProfile
                    println("Updated userProfile: $userProfile")
                } else {
                    println("UserProfile is null")
                }
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
                // Log the error
                println("Error updating userProfile: ${e.message}")
            }
        }
    }
}
