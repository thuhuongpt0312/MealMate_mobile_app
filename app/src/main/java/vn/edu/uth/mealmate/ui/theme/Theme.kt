package vn.edu.uth.mealmate.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = AppColors.Green,
    onPrimary = Color.White,
    primaryContainer = AppColors.GreenSoft,
    background = AppColors.Cream,
    surface = AppColors.Surface,
    onBackground = AppColors.Ink,
    onSurface = AppColors.Ink,
    outline = AppColors.Line,
)

// TODO: thêm font Be Vietnam Pro (res/font) vào Type.kt để chữ tiếng Việt giống Figma.
@Composable
fun MealMateTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, content = content)
}
