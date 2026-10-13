package vn.edu.uth.mealmate.ui.screens.shopping

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S09 · Mua sắm. Phụ trách: C.
 * TODO: tổng dự kiến, tiến độ đã mua, nhóm nguyên liệu, tick, nút thêm; dùng ShoppingListBuilder
 */
@Composable
fun ShoppingScreen(onPantry: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S09",
        title = "Mua sắm",
        actions = listOf("Tủ bếp" to onPantry),
    )
}
