package vn.edu.uth.mealmate.ui.screens.home

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Kitchen
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun HomeScreen(
    onCreatePlan: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onPantry: () -> Unit = {},
    onNutrition: () -> Unit = {},
    onFavorites: () -> Unit = {},
    onCostStats: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

        // --- Header ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "Thứ Ba, 12/03",
                    fontSize = 12.sp,
                    color = AppColors.Muted,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = "Chào buổi sáng, Minh Anh",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
            }

            Box {
                IconButton(
                    onClick = onNotifications,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AppColors.Surface)
                        .border(1.dp, AppColors.Line, CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Thông báo",
                        tint = AppColors.Ink,
                        modifier = Modifier.size(20.dp),
                    )
                }
                // Notification unread dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(AppColors.Green)
                        .align(Alignment.TopEnd)
                        .padding(2.dp),
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        // --- Hero Banner (Xanh đậm) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(AppColors.GreenDark)
                .padding(20.dp),
        ) {
            Column {
                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "TUẦN MỚI THẬT NHẸ NHÀNG",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA3E2BC),
                        letterSpacing = 0.5.sp,
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Ăn ngon mà\nchẳng cần đắn\nđo.",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 32.sp,
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "7 ngày · 3 người · ngân sách 700.000đ",
                    fontSize = 13.sp,
                    color = Color.White.copy(alpha = 0.8f),
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onCreatePlan,
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Surface),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = AppColors.GreenDark,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Tạo thực đơn",
                        color = AppColors.GreenDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                    )
                }
            }
        }

        Spacer(Modifier.height(22.dp))

        // --- Thực đơn hôm nay ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Thực đơn hôm nay",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )
            Text(
                text = "Xem cả tuần",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.Green,
                modifier = Modifier.clickable { /* đi đến thực đơn tuần */ },
            )
        }

        Spacer(Modifier.height(12.dp))

        // Thẻ món ăn hôm nay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(18.dp))
                .padding(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Dish thumbnail
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColors.GreenSoft),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AppColors.Green,
                        modifier = Modifier.size(28.dp),
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BỮA TỐI · 18:30",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Green,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Cơm gà gừng",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "25 phút · 42.000đ/phần",
                        fontSize = 12.sp,
                        color = AppColors.Muted,
                    )
                }

                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = AppColors.Muted,
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // --- Chi tiêu tuần này ---
        Text(
            text = "Chi tiêu tuần này",
            fontSize = 13.sp,
            color = AppColors.Muted,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "650.000đ",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.Ink,
            )
            LinearProgressIndicator(
                progress = { 0.92f },
                modifier = Modifier
                    .width(100.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = AppColors.Green,
                trackColor = AppColors.Line,
            )
        }

        Spacer(Modifier.height(22.dp))

        // --- Lối tắt tiện ích ---
        Text(
            text = "Tiện ích & Khám phá",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ShortcutItem(icon = Icons.Outlined.Kitchen, label = "Tủ bếp", onClick = onPantry)
            ShortcutItem(icon = Icons.Outlined.PieChart, label = "Dinh dưỡng", onClick = onNutrition)
            ShortcutItem(icon = Icons.Outlined.FavoriteBorder, label = "Yêu thích", onClick = onFavorites)
            ShortcutItem(icon = Icons.Outlined.AutoGraph, label = "Thống kê", onClick = onCostStats)
        }

        Spacer(Modifier.height(30.dp))
    }
}

@Composable
private fun ShortcutItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = AppColors.Green, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(text = label, fontSize = 12.sp, color = AppColors.Ink, fontWeight = FontWeight.Medium)
    }
}
