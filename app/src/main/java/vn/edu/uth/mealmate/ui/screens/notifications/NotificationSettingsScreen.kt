package vn.edu.uth.mealmate.ui.screens.notifications

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S14 · Cài đặt thông báo. Phụ trách: A.
 * TODO: công tắc từng loại, mốc giờ/ngưỡng, giờ yên lặng 22:00–06:30
 */
@Composable
fun NotificationSettingsScreen(onBack: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S14",
        title = "Cài đặt thông báo",
        actions = listOf("Quay lại" to onBack),
    )
}
