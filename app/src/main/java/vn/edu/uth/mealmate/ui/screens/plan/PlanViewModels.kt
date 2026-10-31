package vn.edu.uth.mealmate.ui.screens.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.repository.MealMateRepository

/** S05 · Số người và ngân sách lấy từ tùy chọn cá nhân, người dùng có thể đổi số người cho riêng tuần này. */
data class CreatePlanUiState(
    val servings: Int = 2,
    val weeklyBudget: Long? = null,
)

class CreatePlanViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePlanUiState())
    val uiState: StateFlow<CreatePlanUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getPreferences().onSuccess { prefs ->
                _uiState.value = CreatePlanUiState(servings = prefs.servings, weeklyBudget = prefs.weeklyBudget)
            }
        }
    }

    fun changeServings(delta: Int) {
        _uiState.update { it.copy(servings = (it.servings + delta).coerceIn(MIN_SERVINGS, MAX_SERVINGS)) }
    }

    companion object {
        const val MIN_SERVINGS = 1
        const val MAX_SERVINGS = 10
    }
}

sealed interface GeneratingUiState {
    data object Idle : GeneratingUiState
    data object Running : GeneratingUiState
    data class Done(val planId: String) : GeneratingUiState
    data class Error(val message: String) : GeneratingUiState
}

/** S06 · Gọi POST /meal-plans, server tạo thực đơn và danh sách mua sắm đi kèm. */
class GeneratingViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<GeneratingUiState>(GeneratingUiState.Idle)
    val uiState: StateFlow<GeneratingUiState> = _uiState.asStateFlow()

    private var days = 7
    private var servings: Int? = null

    /** Gọi nhiều lần (xoay màn hình, recompose) vẫn chỉ tạo một thực đơn. */
    fun start(days: Int, servings: Int?) {
        if (_uiState.value != GeneratingUiState.Idle) return
        this.days = days
        this.servings = servings
        generate()
    }

    fun retry() {
        if (_uiState.value is GeneratingUiState.Error) generate()
    }

    private fun generate() {
        _uiState.value = GeneratingUiState.Running
        viewModelScope.launch {
            repository.createPlan(days = days, servings = servings)
                .onSuccess { _uiState.value = GeneratingUiState.Done(it.id) }
                .onFailure { _uiState.value = GeneratingUiState.Error(it.message.orEmpty()) }
        }
    }
}
