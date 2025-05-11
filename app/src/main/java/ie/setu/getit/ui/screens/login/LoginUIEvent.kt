package ie.setu.getit.ui.screens.login

sealed class LoginUIEvent {
    data class EmailChanged(val email: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()
    object Submit : LoginUIEvent()
}