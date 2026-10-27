package vn.edu.uth.mealmate.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun ProfileScreen(
    onNotificationSettings: () -> Unit = {},
    onCostStats: () -> Unit = {},
    onImageCredits: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

        // Title
        Text(
            text = "Cá nhân",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Text(
            text = "Sở thích của bạn tạo nên mỗi thực đơn",
            fontSize = 13.sp,
            color = AppColors.Muted,
        )

        Spacer(Modifier.height(16.dp))

        // Thẻ người dùng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(AppColors.GreenSoft),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = AppColors.Green,
                            modifier = Modifier.size(28.dp),
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column {
                        Text(
                            text = "Nguyễn Minh Anh",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Ink,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "minhanh@example.com",
                            fontSize = 12.sp,
                            color = AppColors.Muted,
                        )
                    }
                }

                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text("Sửa", fontSize = 12.sp, color = AppColors.GreenDark, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Banner Mục tiêu hiện tại (xanh đậm)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.GreenDark)
                .padding(18.dp),
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color(0xFFA3E2BC),
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "MỤC TIÊU HIỆN TẠI",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA3E2BC),
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Ăn uống cân bằng mỗi ngày",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Ưu tiên đa dạng, lành mạnh và phù hợp gia đình.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Danh sách mục cài đặt
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
        ) {
            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                title = "Thông tin cá nhân",
                sub = "Nguyễn Minh Anh",
                onClick = { },
            )
            ProfileMenuItem(
                icon = Icons.Outlined.FavoriteBorder,
                title = "Mục tiêu ăn uống",
                sub = "Cân bằng",
                onClick = { },
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Home,
                title = "Số người trong nhà",
                sub = "3 người",
                onClick = { },
            )
            ProfileMenuItem(
                icon = Icons.Outlined.ShoppingBag,
                title = "Mức chi tiêu",
                sub = "Vừa phải",
                onClick = { },
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                title = "Thông báo",
                sub = "Bật",
                onClick = onNotificationSettings,
            )
            ProfileMenuItem(
                icon = Icons.Outlined.BarChart,
                title = "Thống kê chi phí",
                onClick = onCostStats,
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Image,
                title = "Nguồn hình ảnh",
                onClick = onImageCredits,
            )
            ProfileMenuItem(
                icon = Icons.AutoMirrored.Outlined.Logout,
                title = "Đăng xuất",
                onClick = { ApiClient.token = null },
                isDestructive = true,
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    sub: String? = null,
    onClick: () -> Unit,
    isDestructive: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isDestructive) Color.Red else AppColors.Green,
            modifier = Modifier.size(20.dp),
        )
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isDestructive) Color.Red else AppColors.Ink,
            )
            if (!sub.isNullOrBlank()) {
                Text(text = sub, fontSize = 12.sp, color = AppColors.Muted)
            }
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = AppColors.Muted,
            modifier = Modifier.size(18.dp),
        )
    }
}
