package vn.edu.uth.mealmate.ui.screens.profile

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * HT · Nguồn hình ảnh. Phụ trách: A.
 * TODO: bảng layer / màn hình / loại / nguồn / link
 */
@Composable
fun ImageCreditsScreen(onBack: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "HT",
        title = "Nguồn hình ảnh",
        actions = listOf("Quay lại" to onBack),
    )
}
