package vn.edu.uth.mealmate.ui.screens.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.BoughtRequest
import vn.edu.uth.mealmate.data.remote.ShoppingListDto
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

sealed interface ShoppingUiState {
    data object Loading : ShoppingUiState
    data class Success(val list: ShoppingListDto, val planId: String) : ShoppingUiState
    data object NoPlan : ShoppingUiState
    data class Error(val message: String) : ShoppingUiState
}

class ShoppingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ShoppingUiState>(ShoppingUiState.Loading)
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    private var currentPlanId: String? = null

    init {
        loadLatestShoppingList()
    }

    fun loadLatestShoppingList() {
        viewModelScope.launch {
            _uiState.value = ShoppingUiState.Loading
            try {
                val plans = ApiClient.api.plans()
                if (plans.items.isNotEmpty()) {
                    val planId = plans.items.first().id
                    currentPlanId = planId
                    val shopping = ApiClient.api.shoppingList(planId)
                    _uiState.value = ShoppingUiState.Success(shopping, planId)
                } else {
                    _uiState.value = ShoppingUiState.NoPlan
                }
            } catch (e: Throwable) {
                _uiState.value = ShoppingUiState.Error(e.toUserErrorMessage())
            }
        }
    }

    fun toggleBought(itemId: String, bought: Boolean) {
        val planId = currentPlanId ?: return
        viewModelScope.launch {
            try {
                ApiClient.api.setBought(planId, itemId, BoughtRequest(bought))
                val updated = ApiClient.api.shoppingList(planId)
                _uiState.value = ShoppingUiState.Success(updated, planId)
            } catch (e: Throwable) {
                _uiState.value = ShoppingUiState.Error(e.toUserErrorMessage())
            }
        }
    }
}
