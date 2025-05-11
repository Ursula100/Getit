package ie.setu.getit.ui.screens.register

data class RegisterUIState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val countryCode: String = "+353", // default Ireland
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)