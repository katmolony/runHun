package ie.setu.placemark.ui.screens.register

data class RegisterUIState(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var privacyPolicyAccepted: Boolean = false,
    var preferredUnit: String = "km",
    var profilePictureUrl: String? = null,

    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var privacyPolicyError: Boolean = false
)
