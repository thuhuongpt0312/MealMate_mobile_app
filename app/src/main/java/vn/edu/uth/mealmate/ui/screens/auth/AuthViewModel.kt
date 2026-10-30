package vn.edu.uth.mealmate.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Success(val user: UserDto) : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit = {}) =
        submit(onSuccess) { repository.login(email, password) }

    fun register(name: String, email: String, password: String, onSuccess: () -> Unit = {}) =
        submit(onSuccess) { repository.register(name, email, password) }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    private fun submit(onSuccess: () -> Unit, action: suspend () -> Result<UserDto>) {
        if (_uiState.value is AuthUiState.Loading) return // chặn bấm nút nhiều lần
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            action()
                .onSuccess {
                    _uiState.value = AuthUiState.Success(it)
                    onSuccess()
                }
                .onFailure { _uiState.value = AuthUiState.Error(it.message.orEmpty()) }
        }
    }
}
