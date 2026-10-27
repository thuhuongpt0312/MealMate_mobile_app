package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S07 · Thực đơn tuần. Phụ trách: B.
 * TODO: tổng dự kiến + vòng % ngân sách, chọn ngày T2–CN, danh sách bữa ăn theo ngày
 */
@Composable
fun MealPlanScreen(onOpenMeal: () -> Unit = {}, onShopping: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S07",
        title = "Thực đơn tuần",
        actions = listOf("Xem chi tiết món (mẫu)" to onOpenMeal, "Danh sách mua sắm" to onShopping),
    )
}
