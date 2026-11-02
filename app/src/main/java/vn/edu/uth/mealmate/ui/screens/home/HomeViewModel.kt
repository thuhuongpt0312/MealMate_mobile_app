package vn.edu.uth.mealmate.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.RecipeDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository
import vn.edu.uth.mealmate.domain.mealSlotForHour
import vn.edu.uth.mealmate.util.parseApiDate

/** S04 · Trang chủ gom tên người dùng, tùy chọn và thực đơn đang chọn. */
data class HomeUiState(
    val loading: Boolean = true,
    val userName: String? = null,
    val servings: Int? = null,
    val weeklyBudget: Long? = null,
    val plan: MealPlanDto? = null,
    /** Món của bữa sắp tới hôm nay, null nếu hôm nay không nằm trong thực đơn. */
    val nextMeal: RecipeDto? = null,
    val error: String? = null,
)

class HomeViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /** Màn hình gọi mỗi lần hiện ra, để thấy ngay thực đơn vừa tạo hoặc ngân sách vừa sửa. */
    fun load() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true, error = null)
            val me = async { repository.getMe() }
            val prefs = async { repository.getPreferences() }
            val plan = async { repository.getActivePlan() }

            val planResult = plan.await()
            val activePlan = planResult.getOrNull()
            val preferences = prefs.await().getOrNull()
            _uiState.value = HomeUiState(
                loading = false,
                userName = me.await().getOrNull()?.name,
                servings = preferences?.servings,
                weeklyBudget = activePlan?.budget ?: preferences?.weeklyBudget,
                plan = activePlan,
                nextMeal = activePlan?.let(::nextMealToday),
                error = planResult.exceptionOrNull()?.message,
            )
        }
    }

    private fun nextMealToday(plan: MealPlanDto): RecipeDto? {
        val today = LocalDate.now()
        val day = plan.days.find { parseApiDate(it.date) == today } ?: return null
        val meals = day.meals.mapNotNull { plan.recipes?.get(it) }
        val slot = mealSlotForHour(LocalTime.now().hour).name
        return meals.find { it.slot == slot } ?: meals.firstOrNull()
    }
}
