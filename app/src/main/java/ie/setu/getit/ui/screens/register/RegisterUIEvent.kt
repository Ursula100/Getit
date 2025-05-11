package ie.setu.getit.ui.screens.register

sealed class RegisterUIEvent {
    data class NameChanged(val value: String) : RegisterUIEvent()
    data class EmailChanged(val value: String) : RegisterUIEvent()
    data class PhoneChanged(val value: String) : RegisterUIEvent()
    data class CountryCodeChanged(val value: String) : RegisterUIEvent()
    data class PasswordChanged(val value: String) : RegisterUIEvent()
    data class ConfirmPasswordChanged(val value: String) : RegisterUIEvent()
    object Submit : RegisterUIEvent()
}