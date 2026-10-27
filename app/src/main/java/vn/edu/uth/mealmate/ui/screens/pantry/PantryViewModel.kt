package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.NewPantryItem
import vn.edu.uth.mealmate.data.remote.PantryItemDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

sealed interface PantryUiState {
    data object Loading : PantryUiState
    data class Success(val items: List<PantryItemDto>, val total: Int) : PantryUiState
    data class Error(val message: String) : PantryUiState
}

class PantryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PantryUiState>(PantryUiState.Loading)
    val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()

    init {
        loadPantry()
    }

    fun loadPantry() {
        viewModelScope.launch {
            _uiState.value = PantryUiState.Loading
            try {
                val res = ApiClient.api.pantry()
                _uiState.value = PantryUiState.Success(res.items, res.total)
            } catch (e: Throwable) {
                _uiState.value = PantryUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun addPantryItem(name: String, amount: Double, unit: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                ApiClient.api.addPantry(NewPantryItem(name, amount, unit))
                loadPantry()
                onSuccess()
            } catch (e: Throwable) {
                _uiState.value = PantryUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun deletePantryItem(id: String) {
        viewModelScope.launch {
            try {
                ApiClient.api.deletePantry(id)
                loadPantry()
            } catch (e: Throwable) {
                _uiState.value = PantryUiState.Error(e.toUserErrorMessage())
            }
        }
    }
}
