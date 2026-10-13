package vn.edu.uth.mealmate.ui.screens.favorites

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S12 · Yêu thích. Phụ trách: B.
 * TODO: lưới món yêu thích 2 cột
 */
@Composable
fun FavoritesScreen(onBack: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S12",
        title = "Yêu thích",
        actions = listOf("Quay lại" to onBack),
    )
}
