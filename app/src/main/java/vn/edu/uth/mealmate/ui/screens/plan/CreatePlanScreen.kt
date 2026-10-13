package vn.edu.uth.mealmate.ui.screens.plan

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S05 · Tạo thực đơn. Phụ trách: B.
 * TODO: wizard 4 bước: chọn 3/5/7 ngày, ngân sách, khẩu vị, xác nhận; thẻ Tiết kiệm thông minh
 */
@Composable
fun CreatePlanScreen(onBack: () -> Unit = {}, onCreate: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S05",
        title = "Tạo thực đơn",
        actions = listOf("Quay lại" to onBack, "Tạo thực đơn" to onCreate),
    )
}
