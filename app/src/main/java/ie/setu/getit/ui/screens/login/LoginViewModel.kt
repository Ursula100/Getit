package ie.setu.getit.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.getit.firebase.auth.AuthRepository
import ie.setu.getit.firebase.auth.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> _uiState.value = _uiState.value.copy(email = event.email)
            is LoginUIEvent.PasswordChanged -> _uiState.value = _uiState.value.copy(password = event.password)
            is LoginUIEvent.Submit -> login()
        }
    }

    private fun login() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)

            val result = authRepository.login(state.email, state.password)
            when (result) {
                is Response.Success -> _uiState.value = state.copy(isLoading = false, isLoggedIn = true)
                is Response.Failure -> _uiState.value = state.copy(isLoading = false, errorMessage = result.e.message)
                else -> Unit
            }
        }
    }
}