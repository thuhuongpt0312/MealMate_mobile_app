package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.uth.mealmate.data.remote.PantryItemDto
import vn.edu.uth.mealmate.data.repository.MealMateRepository

sealed interface PantryUiState {
    data object Loading : PantryUiState
    data class Success(
        val items: List<PantryItemDto>,
        /** Lỗi khi thêm/xóa, vẫn giữ danh sách đang có. */
        val message: String? = null,
        val saving: Boolean = false,
    ) : PantryUiState
    data class Error(val message: String) : PantryUiState
}

/** S10 · Tủ bếp. Nguyên liệu trong tủ được trừ ra khi tạo thực đơn mới. */
class PantryViewModel(
    private val repository: MealMateRepository = MealMateRepository.instance,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PantryUiState>(PantryUiState.Loading)
    val uiState: StateFlow<PantryUiState> = _uiState.asStateFlow()

    init {
        loadPantry()
    }

    fun loadPantry() {
        viewModelScope.launch {
            _uiState.value = PantryUiState.Loading
            repository.getPantry()
                .onSuccess { _uiState.value = PantryUiState.Success(it) }
                .onFailure { _uiState.value = PantryUiState.Error(it.message.orEmpty()) }
        }
    }

    /** [onSuccess] dùng để đóng hộp thoại thêm nguyên liệu. */
    fun addPantryItem(name: String, amount: Double?, unit: String, onSuccess: () -> Unit = {}) {
        val current = _uiState.value as? PantryUiState.Success ?: return
        if (current.saving) return
        when {
            name.isBlank() -> return showMessage("Nhập tên nguyên liệu")
            amount == null || amount <= 0 -> return showMessage("Số lượng phải lớn hơn 0")
            unit.isBlank() -> return showMessage("Nhập đơn vị (g, quả, bó…)")
        }
        _uiState.value = current.copy(saving = true, message = null)
        viewModelScope.launch {
            repository.addPantryItem(name.trim(), amount!!, unit.trim())
                .onSuccess { item ->
                    _uiState.update { if (it is PantryUiState.Success) it.copy(items = it.items + item, saving = false) else it }
                    onSuccess()
                }
                .onFailure { error ->
                    _uiState.update { if (it is PantryUiState.Success) it.copy(saving = false, message = error.message) else it }
                }
        }
    }

    /** Xóa ngay trên màn hình, lỗi thì đưa nguyên liệu trở lại vị trí cũ. */
    fun deletePantryItem(id: String) {
        val current = _uiState.value as? PantryUiState.Success ?: return
        val index = current.items.indexOfFirst { it.id == id }
        if (index < 0) return
        val removed = current.items[index]
        _uiState.value = current.copy(items = current.items - removed, message = null)
        viewModelScope.launch {
            repository.deletePantryItem(id).onFailure { error ->
                _uiState.update { state ->
                    if (state !is PantryUiState.Success) return@update state
                    val items = state.items.toMutableList().apply { add(index.coerceAtMost(size), removed) }
                    state.copy(items = items, message = error.message)
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { if (it is PantryUiState.Success) it.copy(message = null) else it }
    }

    private fun showMessage(message: String) {
        _uiState.update { if (it is PantryUiState.Success) it.copy(message = message) else it }
    }
}
