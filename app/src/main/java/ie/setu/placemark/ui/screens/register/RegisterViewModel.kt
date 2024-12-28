package ie.setu.placemark.ui.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.api.RetrofitRepository
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.data.rules.Validator
import ie.setu.placemark.firebase.auth.Response
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirebaseSignInResponse
import ie.setu.placemark.firebase.services.FirestoreService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: FirestoreService,
    private val auth: FirebaseAuth,
    private val authService: AuthService,
)
    : ViewModel() {

   var registrationUIState = mutableStateOf(RegisterUIState())
   var allValidationsPassed = mutableStateOf(false)
   var signUpInProgress = mutableStateOf(false)

   private val _signupFlow = MutableStateFlow<FirebaseSignInResponse?>(null)
   val signupFlow: StateFlow<FirebaseSignInResponse?> = _signupFlow

    fun signUpUser() = viewModelScope.launch {
        _signupFlow.value = Response.Loading

        val result = authService.createUser(
            name = registrationUIState.value.firstName,
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )

        if (result is Response.Success) {
            // Now that user is authenticated, create the profile
            createUserProfile()
        }

        _signupFlow.value = result
    }

    private fun createUserProfile() = viewModelScope.launch {
       _signupFlow.value = Response.Loading
        Timber.i("RegisterViewModel Creating user profile...") // Add this log

        val userProfile = UserProfileModel(
            userId = 0,
            name = registrationUIState.value.firstName ?: "",
            email = registrationUIState.value.email ?: "",
            profilePictureUrl = registrationUIState.value.profilePictureUrl,
            totalDistanceRun = 0.0,
            totalRuns = 0,
            averagePace = 0.0,
            preferredUnit = registrationUIState.value.preferredUnit ?: "km" // Default if null
        )

//        val result = authService.createUserProfile(userProfile)
        val result = repository.createUserProfile(userProfile)
        Timber.i("RegisterViewModel, User profile creation result: $result") // Log the result

//        _signupFlow.value = result
    }

    fun onEvent(event: RegisterUIEvent) {
        Timber.i("RegisterViewModel Event triggered: $event")
        when (event) {
            is RegisterUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }
            is RegisterUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }
            is RegisterUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }
            is RegisterUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
            is RegisterUIEvent.PreferredUnitChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    preferredUnit = event.unit
                )
            }
            is RegisterUIEvent.RegisterButtonClicked -> {
                if (allValidationsPassed.value) {
                    signUpUser()  // Call the sign-up user function
//                    createUserProfile()
                }
            }

            is RegisterUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        validateDataWithRules()
    }



    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )

        registrationUIState.value = registrationUIState.value.copy(
           firstNameError = fNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        allValidationsPassed.value = fNameResult.status && emailResult.status &&
                                passwordResult.status && privacyPolicyResult.status
    }

    fun resetRegisterFlow() {
        _signupFlow.value = null
    }
}
