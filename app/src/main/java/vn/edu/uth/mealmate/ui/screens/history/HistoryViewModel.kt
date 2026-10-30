package vn.edu.uth.mealmate.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface HistoryUiState {
    data object Loading : HistoryUiState
    data class Success(val plans: List<MealPlanDto>, val total: Int) : HistoryUiState
    data class Error(val message: String) : HistoryUiState
}

class HistoryViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HistoryUiState>(HistoryUiState.Loading)
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = HistoryUiState.Loading
            repository.getPlans()
                .onSuccess { _uiState.value = HistoryUiState.Success(it, it.size) }
                .onFailure { _uiState.value = HistoryUiState.Error(it.message.orEmpty()) }
        }
    }

    /** Chọn một tuần để xem lại ở tab Thực đơn và Mua sắm. */
    fun openPlan(id: String) = repository.selectPlan(id)
}
