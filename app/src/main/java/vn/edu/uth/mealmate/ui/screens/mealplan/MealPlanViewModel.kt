package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface MealPlanUiState {
    data object Loading : MealPlanUiState
    data class Success(val plan: MealPlanDto) : MealPlanUiState
    data object Empty : MealPlanUiState
    data class Error(val message: String) : MealPlanUiState
}

/** S07 · Thực đơn đang chọn (mới tạo, hoặc chọn lại từ Lịch sử), mặc định là thực đơn mới nhất. */
class MealPlanViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealPlanUiState>(MealPlanUiState.Loading)
    val uiState: StateFlow<MealPlanUiState> = _uiState.asStateFlow()

    init {
        // Tải lại mỗi khi đổi thực đơn đang chọn (vừa tạo mới hoặc chọn từ Lịch sử)
        viewModelScope.launch {
            repository.activePlanId.collect { load() }
        }
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = MealPlanUiState.Loading
            repository.getActivePlan()
                .onSuccess { _uiState.value = if (it == null) MealPlanUiState.Empty else MealPlanUiState.Success(it) }
                .onFailure { _uiState.value = MealPlanUiState.Error(it.message.orEmpty()) }
        }
    }
}
