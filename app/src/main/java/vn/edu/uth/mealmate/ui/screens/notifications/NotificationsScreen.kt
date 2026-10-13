package vn.edu.uth.mealmate.ui.screens.notifications

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S13 · Thông báo. Phụ trách: A.
 * TODO: chip lọc, nhóm Hôm nay / Trước đó, vuốt để xóa, đánh dấu đã đọc, trạng thái trống
 */
@Composable
fun NotificationsScreen(onBack: () -> Unit = {}, onSettings: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S13",
        title = "Thông báo",
        actions = listOf("Quay lại" to onBack, "Cài đặt thông báo" to onSettings),
    )
}
