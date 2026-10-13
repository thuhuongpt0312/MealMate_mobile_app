package vn.edu.uth.mealmate.ui.screens.setup

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S03 · Vài điều về bạn. Phụ trách: A.
 * TODO: 3 bước: số người (Chỉ mình tôi / Gia đình tôi), mục tiêu, khẩu vị + món cần tránh
 */
@Composable
fun SetupScreen(onFinish: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S03",
        title = "Vài điều về bạn",
        actions = listOf("Hoàn tất thiết lập" to onFinish),
    )
}
