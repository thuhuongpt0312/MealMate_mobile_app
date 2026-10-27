package vn.edu.uth.mealmate.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

sealed interface HistoryUiState {
    data object Loading : HistoryUiState
    data class Success(val plans: List<MealPlanDto>, val total: Int) : HistoryUiState
    data class Error(val message: String) : HistoryUiState
}

class HistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            try {
                val res = ApiClient.api.plans()
                _uiState.value = HistoryUiState.Success(res.items, res.total)
            } catch (e: Throwable) {
                _uiState.value = HistoryUiState.Error(e.toUserErrorMessage())
            }
        }
    }
}
