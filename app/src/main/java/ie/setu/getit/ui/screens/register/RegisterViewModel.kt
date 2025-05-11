package ie.setu.getit.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.firebase.auth.Response
import ie.setu.getit.firebase.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.NameChanged -> _uiState.update { it.copy(name = event.value) }
            is RegisterUIEvent.EmailChanged -> _uiState.update { it.copy(email = event.value) }
            is RegisterUIEvent.PhoneChanged -> _uiState.update { it.copy(phone = event.value) }
            is RegisterUIEvent.CountryCodeChanged -> _uiState.update { it.copy(countryCode = event.value) }
            is RegisterUIEvent.PasswordChanged -> _uiState.update { it.copy(password = event.value) }
            is RegisterUIEvent.ConfirmPasswordChanged -> _uiState.update { it.copy(confirmPassword = event.value) }

            RegisterUIEvent.Submit -> register()
        }
    }

    private fun register() {
        val state = _uiState.value

        // Input validation
        if (state.name.isBlank() || state.email.isBlank() ||
            state.password.isBlank() || state.confirmPassword.isBlank() || state.phone.isBlank()
        ) {
            _uiState.update { it.copy(error = "All fields are required.") }
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(error = "Passwords do not match.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = authService.registerWithEmailPassword(
                email = state.email,
                password = state.password,
                displayName = state.name
            )

            when (result) {
                is Response.Success -> {
                    _uiState.update { it.copy(success = true, isLoading = false) }
                }

                is Response.Failure -> {
                    _uiState.update { it.copy(error = result.toString(), isLoading = false) }
                }

                is Response.Loading -> {
                }
            }
        }
    }
}