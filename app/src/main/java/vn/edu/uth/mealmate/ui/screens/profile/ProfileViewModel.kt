package vn.edu.uth.mealmate.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.PreferencesDto
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage
import vn.edu.uth.mealmate.data.session.SessionManager

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val user: UserDto, val preferences: PreferencesDto?) : ProfileUiState
    data object LoggedOut : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        if (!SessionManager.isLoggedIn) {
            _uiState.value = ProfileUiState.LoggedOut
            return
        }
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val me = ApiClient.api.me()
                val prefs = runCatching { ApiClient.api.preferences() }.getOrNull()
                _uiState.value = ProfileUiState.Success(me.user, prefs)
            } catch (e: Throwable) {
                _uiState.value = ProfileUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit = {}) {
        viewModelScope.launch {
            runCatching { ApiClient.api.logout() }
            SessionManager.clear()
            _uiState.value = ProfileUiState.LoggedOut
            onLoggedOut()
        }
    }
}
