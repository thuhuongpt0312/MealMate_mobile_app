package vn.edu.uth.mealmate.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.ui.components.MealMateBrandBadge
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun RegisterScreen(
    onRegister: () -> Unit = {},
    vm: AuthViewModel = viewModel(),
) {
    var isLoginMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Nguyễn Minh Anh") }
    var email by remember { mutableStateOf("minhanh@example.com") }
    var password by remember { mutableStateOf("12345678") }
    var showPassword by remember { mutableStateOf(false) }

    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream),
    ) {
        // --- Phần hình ảnh trên ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.38f)
                .background(AppColors.GreenDark),
        ) {
            // Placeholder hình ảnh món ăn đặc trưng
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.GreenSoft.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.size(64.dp),
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Món ngon dinh dưỡng mỗi ngày",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp,
                    )
                }
            }

            MealMateBrandBadge(
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .align(Alignment.TopStart),
            )
        }

        // --- Card form dưới (trắng, bo tròn đỉnh) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.62f)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(AppColors.Surface)
                .padding(horizontal = 24.dp, vertical = 20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    text = if (isLoginMode) "Đăng nhập" else "Tạo tài khoản",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (isLoginMode) "Chào mừng bạn quay lại với MealMate!" else "Một tuần khỏe mạnh, nhẹ nhàng bắt đầu từ đây.",
                    fontSize = 13.sp,
                    color = AppColors.Muted,
                )

                Spacer(Modifier.height(18.dp))

                if (!isLoginMode) {
                    Text("Họ và tên", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Ink)
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Green,
                            unfocusedBorderColor = AppColors.Line,
                        ),
                    )
                    Spacer(Modifier.height(14.dp))
                }

                Text("Địa chỉ email", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Ink)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.Green,
                        unfocusedBorderColor = AppColors.Line,
                    ),
                )

                Spacer(Modifier.height(14.dp))

                Text("Mật khẩu", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Ink)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    placeholder = { Text("ít nhất 8 ký tự") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = AppColors.Muted,
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AppColors.Green,
                        unfocusedBorderColor = AppColors.Line,
                    ),
                )

                if (uiState is AuthUiState.Error) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = (uiState as AuthUiState.Error).message,
                        color = Color.Red,
                        fontSize = 12.sp,
                    )
                }

                Spacer(Modifier.height(20.dp))

                if (uiState is AuthUiState.Loading) {
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.Green)
                    }
                } else {
                    PrimaryButton(
                        label = if (isLoginMode) "Đăng nhập" else "Tạo tài khoản",
                        onClick = {
                            if (isLoginMode) {
                                vm.login(email.trim(), password, onSuccess = onRegister)
                            } else {
                                vm.register(name.trim(), email.trim(), password, onSuccess = onRegister)
                            }
                        },
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                ) {
                    Text(
                        text = if (isLoginMode) "Chưa có tài khoản? " else "Đã có tài khoản? ",
                        fontSize = 13.sp,
                        color = AppColors.Muted,
                    )
                    Text(
                        text = if (isLoginMode) "Đăng ký" else "Đăng nhập",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Green,
                        modifier = Modifier.clickable {
                            isLoginMode = !isLoginMode
                            vm.resetState()
                        },
                    )
                }
            }
        }
    }
}
