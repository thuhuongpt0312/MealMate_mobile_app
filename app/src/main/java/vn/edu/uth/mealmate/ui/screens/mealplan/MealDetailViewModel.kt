package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface MealDetailUiState {
    data object Loading : MealDetailUiState
    data class Success(val meal: Meal) : MealDetailUiState
    data class Error(val message: String) : MealDetailUiState
}

/** S08 · Chi tiết một món (GET /recipes/{id}). */
class MealDetailViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Loading)
    val uiState: StateFlow<MealDetailUiState> = _uiState.asStateFlow()

    private var mealId: String? = null

    fun load(id: String) {
        if (id == mealId && _uiState.value is MealDetailUiState.Success) return
        mealId = id
        viewModelScope.launch {
            _uiState.value = MealDetailUiState.Loading
            repository.getRecipe(id)
                .onSuccess { _uiState.value = MealDetailUiState.Success(it) }
                .onFailure { _uiState.value = MealDetailUiState.Error(it.message.orEmpty()) }
        }
    }

    fun retry() {
        mealId?.let(::load)
    }
}
