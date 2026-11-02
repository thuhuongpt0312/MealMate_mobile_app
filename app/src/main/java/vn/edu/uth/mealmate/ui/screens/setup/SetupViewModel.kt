package vn.edu.uth.mealmate.ui.screens.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.PreferencesDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

/** S03 · Ba bước: số người → mục tiêu → ngân sách và món cần tránh. */
data class SetupUiState(
    val step: Int = 1,
    val servings: Int = 2,
    val goal: String = GOALS.first(),
    val weeklyBudget: Long = 700_000,
    val avoid: Set<String> = emptySet(),
    /** Giữ nguyên khẩu vị đã lưu trên server (màn này chưa cho sửa). */
    val tastes: List<String> = emptyList(),
    val saving: Boolean = false,
    val error: String? = null,
) {
    companion object {
        const val STEPS = 3
        val GOALS = listOf("Ăn uống cân bằng", "Giảm cân lành mạnh", "Tăng cơ", "Tiết kiệm chi phí")
        val AVOID_OPTIONS = listOf("Hải sản", "Thịt bò", "Thịt heo", "Đậu phộng", "Sữa", "Đồ cay")
        const val BUDGET_STEP = 50_000L
        const val MIN_BUDGET = 100_000L
        const val MAX_BUDGET = 5_000_000L
    }
}

class SetupViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SetupUiState())
    val uiState: StateFlow<SetupUiState> = _uiState.asStateFlow()

    init {
        // Mở lại từ màn Cá nhân thì điền sẵn tùy chọn đang có
        viewModelScope.launch {
            repository.getPreferences().onSuccess { prefs ->
                _uiState.update {
                    it.copy(
                        servings = prefs.servings,
                        goal = prefs.goal,
                        weeklyBudget = prefs.weeklyBudget,
                        avoid = prefs.avoid.toSet(),
                        tastes = prefs.tastes,
                    )
                }
            }
        }
    }

    fun setServings(value: Int) = _uiState.update { it.copy(servings = value.coerceIn(1, 10)) }

    fun setGoal(value: String) = _uiState.update { it.copy(goal = value) }

    fun changeBudget(delta: Long) = _uiState.update {
        it.copy(weeklyBudget = (it.weeklyBudget + delta).coerceIn(SetupUiState.MIN_BUDGET, SetupUiState.MAX_BUDGET))
    }

    fun toggleAvoid(item: String) = _uiState.update {
        it.copy(avoid = if (item in it.avoid) it.avoid - item else it.avoid + item)
    }

    /** Trả về false nếu đang ở bước đầu (để màn hình tự đóng). */
    fun back(): Boolean {
        val step = _uiState.value.step
        if (step <= 1) return false
        _uiState.update { it.copy(step = step - 1, error = null) }
        return true
    }

    /** Sang bước sau; ở bước cuối thì lưu lên server rồi gọi [onFinished]. */
    fun next(onFinished: () -> Unit) {
        val state = _uiState.value
        if (state.saving) return
        if (state.step < SetupUiState.STEPS) {
            _uiState.update { it.copy(step = it.step + 1) }
            return
        }
        _uiState.update { it.copy(saving = true, error = null) }
        viewModelScope.launch {
            val prefs = PreferencesDto(
                servings = state.servings,
                goal = state.goal,
                weeklyBudget = state.weeklyBudget,
                tastes = state.tastes,
                avoid = state.avoid.toList(),
            )
            repository.updatePreferences(prefs)
                .onSuccess {
                    _uiState.update { it.copy(saving = false) }
                    onFinished()
                }
                .onFailure { e -> _uiState.update { it.copy(saving = false, error = e.message) } }
        }
    }
}
