package vn.edu.uth.mealmate.ui.screens.home

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S04 · Trang chủ. Phụ trách: B.
 * TODO: lời chào, chuông thông báo, banner Tạo thực đơn, thực đơn hôm nay, chi tiêu tuần, lối tắt
 */
@Composable
fun HomeScreen(onCreatePlan: () -> Unit = {}, onNotifications: () -> Unit = {}, onPantry: () -> Unit = {}, onNutrition: () -> Unit = {}, onFavorites: () -> Unit = {}, onCostStats: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S04",
        title = "Trang chủ",
        actions = listOf("Tạo thực đơn" to onCreatePlan, "Thông báo" to onNotifications, "Tủ bếp" to onPantry, "Dinh dưỡng" to onNutrition, "Yêu thích" to onFavorites, "Thống kê chi phí" to onCostStats),
    )
}
