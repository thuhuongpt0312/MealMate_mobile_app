package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.CreatePlanRequest
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

sealed interface MealPlanUiState {
    data object Loading : MealPlanUiState
    data class Success(val plan: MealPlanDto) : MealPlanUiState
    data object Empty : MealPlanUiState
    data class Error(val message: String) : MealPlanUiState
}

class MealPlanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<MealPlanUiState>(MealPlanUiState.Loading)
    val uiState: StateFlow<MealPlanUiState> = _uiState.asStateFlow()

    init {
        loadLatestPlan()
    }

    fun loadLatestPlan() {
        viewModelScope.launch {
            _uiState.value = MealPlanUiState.Loading
            try {
                val plans = ApiClient.api.plans()
                if (plans.items.isNotEmpty()) {
                    val latest = ApiClient.api.plan(plans.items.first().id)
                    _uiState.value = MealPlanUiState.Success(latest)
                } else {
                    _uiState.value = MealPlanUiState.Empty
                }
            } catch (e: Throwable) {
                _uiState.value = MealPlanUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun createPlan(startDate: String? = null, servings: Int? = null, onSuccess: (String) -> Unit = {}) {
        viewModelScope.launch {
            _uiState.value = MealPlanUiState.Loading
            try {
                val newPlan = ApiClient.api.createPlan(CreatePlanRequest(startDate, servings))
                val fullPlan = ApiClient.api.plan(newPlan.id)
                _uiState.value = MealPlanUiState.Success(fullPlan)
                onSuccess(newPlan.id)
            } catch (e: Throwable) {
                _uiState.value = MealPlanUiState.Error(e.toUserErrorMessage())
            }
        }
    }
}
