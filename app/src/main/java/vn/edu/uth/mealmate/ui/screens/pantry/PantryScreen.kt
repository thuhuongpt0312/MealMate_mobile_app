package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S10 · Tủ bếp. Phụ trách: C.
 * TODO: danh sách nguyên liệu đang có, thẻ Tiết kiệm hơn với tủ bếp, thêm/sửa/xóa
 */
@Composable
fun PantryScreen(onBack: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S10",
        title = "Tủ bếp",
        actions = listOf("Quay lại" to onBack),
    )
}
