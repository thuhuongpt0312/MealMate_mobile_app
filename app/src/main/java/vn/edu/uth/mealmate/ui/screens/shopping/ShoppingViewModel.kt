package vn.edu.uth.mealmate.ui.screens.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.ShoppingListDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface ShoppingUiState {
    data object Loading : ShoppingUiState
    data class Success(
        val plan: MealPlanDto,
        val list: ShoppingListDto,
        /** Lỗi khi tick một món, hiển thị nhưng vẫn giữ danh sách. */
        val message: String? = null,
    ) : ShoppingUiState
    data object NoPlan : ShoppingUiState
    data class Error(val message: String) : ShoppingUiState
}

/** S09 · Danh sách mua sắm của thực đơn đang chọn. */
class ShoppingViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShoppingUiState>(ShoppingUiState.Loading)
    val uiState: StateFlow<ShoppingUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.activePlanId.collect { load() }
        }
    }

    fun load() {
        viewModelScope.launch {
            _uiState.value = ShoppingUiState.Loading
            val plan = repository.getActivePlan().getOrElse {
                _uiState.value = ShoppingUiState.Error(it.message.orEmpty())
                return@launch
            }
            if (plan == null) {
                _uiState.value = ShoppingUiState.NoPlan
                return@launch
            }
            repository.getShoppingList(plan.id)
                .onSuccess { _uiState.value = ShoppingUiState.Success(plan, it) }
                .onFailure { _uiState.value = ShoppingUiState.Error(it.message.orEmpty()) }
        }
    }

    /** Đổi trạng thái ngay trên màn hình, gọi API sau; lỗi thì trả lại như cũ và báo cho người dùng. */
    fun toggleBought(itemId: String) {
        val current = _uiState.value as? ShoppingUiState.Success ?: return
        val item = current.list.items.find { it.id == itemId } ?: return
        val bought = !item.bought
        setBought(itemId, bought, message = null)
        viewModelScope.launch {
            repository.setBought(current.plan.id, itemId, bought).onFailure {
                setBought(itemId, !bought, message = it.message)
            }
        }
    }

    fun clearMessage() {
        _uiState.update { if (it is ShoppingUiState.Success) it.copy(message = null) else it }
    }

    private fun setBought(itemId: String, bought: Boolean, message: String?) {
        _uiState.update { state ->
            if (state !is ShoppingUiState.Success) return@update state
            val items = state.list.items.map { if (it.id == itemId) it.copy(bought = bought) else it }
            state.copy(
                list = state.list.copy(items = items, remaining = items.filterNot { it.bought }.sumOf { it.cost }),
                message = message,
            )
        }
    }
}
