package vn.edu.uth.mealmate.ui.screens.history

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S12b · Lịch sử. Phụ trách: C.
 * TODO: thẻ tuần này (Xem / Lên lại thực đơn), danh sách các tuần trước
 */
@Composable
fun HistoryScreen(onOpenPlan: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S12b",
        title = "Lịch sử",
        actions = listOf("Xem thực đơn" to onOpenPlan),
    )
}
