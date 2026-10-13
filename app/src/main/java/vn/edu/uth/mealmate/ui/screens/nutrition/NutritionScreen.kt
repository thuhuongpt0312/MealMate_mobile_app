package vn.edu.uth.mealmate.ui.screens.nutrition

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S11 · Dinh dưỡng. Phụ trách: C.
 * TODO: vòng % mục tiêu, biểu đồ năng lượng mỗi ngày T2–CN
 */
@Composable
fun NutritionScreen(onBack: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S11",
        title = "Dinh dưỡng",
        actions = listOf("Quay lại" to onBack),
    )
}
