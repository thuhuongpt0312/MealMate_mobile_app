package vn.edu.uth.mealmate.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.theme.AppColors

/**
 * S13b · Mẫu thông báo (Màn hình khóa / Banner mô phỏng theo Figma S13b_MauThongBao)
 */
@Composable
fun NotificationMockupScreen(onBack: () -> Unit = {}) {
    val lockScreenGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8EFE8),
            Color(0xFFF3ECE0),
            Color(0xFFFAF7F0),
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lockScreenGradient)
            .padding(horizontal = 20.dp),
    ) {
        HeaderBar(
            title = "S13b_MauThongBao",
            subtitle = "Mẫu thông báo trên màn hình khóa",
            onBack = onBack,
        )

        Spacer(Modifier.height(30.dp))

        // Đồng hồ màn hình khóa
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "18:05",
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2C3E2D),
                letterSpacing = (-1).sp,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Thứ Ba, 12/03",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF4A5D4B),
            )
        }

        Spacer(Modifier.height(36.dp))

        // Notification Banner 1: Nhắc bữa ăn
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                .padding(16.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(AppColors.Green),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckBox,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "MealMate",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Ink,
                        )
                    }

                    Text(
                        text = "vừa xong",
                        fontSize = 11.sp,
                        color = AppColors.Muted,
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Đến giờ chuẩn bị bữa tối",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Cơm gà gừng · 25 phút để nấu",
                    fontSize = 13.sp,
                    color = AppColors.Muted,
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Notification Banner 2: Nhắc đi chợ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .border(1.dp, Color.White, RoundedCornerShape(20.dp))
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppColors.GreenSoft),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = AppColors.Green,
                        modifier = Modifier.size(20.dp),
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Nhắc đi chợ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Bạn còn 9 món chưa mua.",
                        fontSize = 13.sp,
                        color = AppColors.Muted,
                    )
                }
            }
        }
    }
}
