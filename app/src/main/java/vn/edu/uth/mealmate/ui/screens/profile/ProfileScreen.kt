package vn.edu.uth.mealmate.ui.screens.profile

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S16 · Cá nhân. Phụ trách: A.
 * TODO: thẻ người dùng + Sửa, mục tiêu hiện tại, danh sách cài đặt, Đăng xuất
 */
@Composable
fun ProfileScreen(onNotificationSettings: () -> Unit = {}, onCostStats: () -> Unit = {}, onImageCredits: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S16",
        title = "Cá nhân",
        actions = listOf("Cài đặt thông báo" to onNotificationSettings, "Thống kê chi phí" to onCostStats, "Nguồn hình ảnh" to onImageCredits),
    )
}
