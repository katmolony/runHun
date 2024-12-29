package ie.setu.placemark.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.placemark.data.rules.Validator
import ie.setu.placemark.firebase.auth.Response
import ie.setu.placemark.firebase.services.AuthService
import ie.setu.placemark.firebase.services.FirebaseSignInResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import timber.log.Timber
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.firebase.auth.GoogleAuthProvider
import ie.setu.placemark.data.model.UserProfileModel
import ie.setu.placemark.firebase.services.FirestoreService
import ie.setu.placemark.ui.screens.register.RegisterViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authService: AuthService,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest,
    private val repository: FirestoreService,
)
    : ViewModel() {

    private val _loginFlow = MutableStateFlow<FirebaseSignInResponse?>(null)
    val loginFlow: StateFlow<FirebaseSignInResponse?> = _loginFlow

    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)

    val currentUser: FirebaseUser?
        get() = authService.currentUser

    init {
        if (authService.currentUser != null) {
            _loginFlow.value = Response.Success(authService.currentUser!!)
        }
    }

    fun loginUser() = viewModelScope.launch {

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        _loginFlow.value = Response.Loading
        val result = authService.authenticateUser(email, password)
        _loginFlow.value = result
    }

    private fun loginGoogleUser(googleIdToken: String) = viewModelScope.launch {
        _loginFlow.value = Response.Loading
        val result = authService.authenticateGoogleUser(googleIdToken)
        _loginFlow.value = result
    }

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> { loginUser() }

            else -> {}
        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status

    }

    fun resetLoginFlow() {
        _loginFlow.value = null
    }
    fun signInWithGoogleCredentials(credentialsContext: Context) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = credentialsContext,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                Timber.e(e, "Google Sign-In Failed")
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        loginGoogleUser(googleIdToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Timber.e(e, "Invalid Google ID Token")
                    }
                }
            }
        }
    }
}


