package ie.setu.placemark.ui.screens.register

sealed class RegisterUIEvent {
    data class FirstNameChanged(val firstName: String) : RegisterUIEvent()
    data class EmailChanged(val email: String) : RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()
    data class PrivacyPolicyCheckBoxClicked(val status: Boolean) : RegisterUIEvent()
    data class PreferredUnitChanged(val unit: String) : RegisterUIEvent()

    object RegisterButtonClicked : RegisterUIEvent()
}
