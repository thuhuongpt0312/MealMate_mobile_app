package vn.edu.uth.mealmate.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.PreferencesDto
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(val user: UserDto, val preferences: PreferencesDto?) : ProfileUiState
    data object LoggedOut : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

class ProfileViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        if (!repository.isLoggedIn) {
            _uiState.value = ProfileUiState.LoggedOut
            return
        }
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            repository.getMe()
                .onSuccess { user ->
                    val prefs = repository.getPreferences().getOrNull()
                    _uiState.value = ProfileUiState.Success(user, prefs)
                }
                .onFailure { _uiState.value = ProfileUiState.Error(it.message.orEmpty()) }
        }
    }

    /** Xóa phiên; MealMateApp tự chuyển về màn đăng nhập khi token bị xóa. */
    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.value = ProfileUiState.LoggedOut
        }
    }
}
