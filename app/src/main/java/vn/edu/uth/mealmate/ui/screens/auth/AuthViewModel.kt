package vn.edu.uth.mealmate.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.LoginRequest
import vn.edu.uth.mealmate.data.remote.RegisterRequest
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Success(val user: UserDto) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val res = ApiClient.api.login(LoginRequest(email, password))
                ApiClient.token = res.token
                _uiState.value = AuthUiState.Success(res.user)
                onSuccess()
            } catch (e: Throwable) {
                _uiState.value = AuthUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun register(name: String, email: String, password: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val res = ApiClient.api.register(RegisterRequest(name, email, password))
                ApiClient.token = res.token
                _uiState.value = AuthUiState.Success(res.user)
                onSuccess()
            } catch (e: Throwable) {
                _uiState.value = AuthUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}
