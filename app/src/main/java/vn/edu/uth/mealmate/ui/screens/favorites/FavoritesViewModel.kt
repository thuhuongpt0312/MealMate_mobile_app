package vn.edu.uth.mealmate.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.local.FavoritesStore
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val meals: List<Meal>) : FavoritesUiState
    data class Error(val message: String) : FavoritesUiState
}

/** S12 · Lấy các món đã duyệt từ API rồi lọc theo id đã thả tim (lưu trên máy). */
class FavoritesViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val recipes = MutableStateFlow<Result<List<Meal>>?>(null)

    val uiState: StateFlow<FavoritesUiState> = combine(recipes, FavoritesStore.ids) { result, ids ->
        when {
            result == null -> FavoritesUiState.Loading
            result.isFailure -> FavoritesUiState.Error(result.exceptionOrNull()?.message.orEmpty())
            else -> FavoritesUiState.Success(result.getOrThrow().filter { it.id in ids })
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), FavoritesUiState.Loading)

    init {
        load()
    }

    fun load() {
        recipes.value = null
        viewModelScope.launch { recipes.value = repository.getRecipes() }
    }
}
