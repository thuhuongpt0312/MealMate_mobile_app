package vn.edu.uth.mealmate.ui.screens.auth

import androidx.compose.runtime.Composable
import vn.edu.uth.mealmate.ui.components.PlaceholderScreen

/**
 * S02 · Tạo tài khoản. Phụ trách: A.
 * TODO: Họ và tên, email, mật khẩu (≥ 8 ký tự), kiểm tra hợp lệ, link Đăng nhập
 */
@Composable
fun RegisterScreen(onRegister: () -> Unit = {}) {
    PlaceholderScreen(
        figmaId = "S02",
        title = "Tạo tài khoản",
        actions = listOf("Tạo tài khoản" to onRegister),
    )
}
