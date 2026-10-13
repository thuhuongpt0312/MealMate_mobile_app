package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.data.sample.SampleData
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen
import vn.edu.uth.mealmate.util.formatVnd

/**
 * S08 · Chi tiết món. Phụ trách: B.
 * TODO: ảnh món, thời gian / giá / calo, nguyên liệu theo số phần, cách làm, nút yêu thích.
 */
@Composable
fun MealDetailScreen(mealId: String, onBack: () -> Unit = {}) {
    val meal = SampleData.meals.find { it.id == mealId }
    PlaceholderScreen(
        figmaId = "S08",
        title = meal?.let { "${it.name} · ${formatVnd(it.pricePerServing)}/phần" } ?: "Chi tiết món",
        actions = listOf("Quay lại" to onBack),
    )
}
