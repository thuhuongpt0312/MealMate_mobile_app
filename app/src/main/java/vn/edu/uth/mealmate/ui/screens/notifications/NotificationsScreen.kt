package vn.edu.uth.mealmate.ui.screens.notifications

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun NotificationsScreen(
    onBack: () -> Unit = {},
    onSettings: () -> Unit = {},
) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }
    val filters = listOf("Tất cả", "Bữa ăn", "Mua sắm", "Ngân sách")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Thông báo",
            onBack = onBack,
            rightContent = {
                Text(
                    text = "Đánh dấu đã đọc tất cả",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.Green,
                    modifier = Modifier.clickable { },
                )
            },
        )

        Spacer(Modifier.height(8.dp))

        // Filter chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filters) { filter ->
                val isSelected = filter == selectedFilter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(if (isSelected) AppColors.Ink else AppColors.Surface)
                        .border(1.dp, if (isSelected) AppColors.Ink else AppColors.Line, RoundedCornerShape(20.dp))
                        .clickable { selectedFilter = filter }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = filter,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else AppColors.Ink,
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // HÔM NAY
        Text(
            text = "HÔM NAY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Muted,
            letterSpacing = 0.5.sp,
        )

        Spacer(Modifier.height(10.dp))

        NotificationCard(
            icon = Icons.Outlined.AccessTime,
            title = "Nhắc bữa ăn",
            body = "Bữa tối lúc 18:30: Cơm gà gừng. Còn 25 phút để nấu.",
            time = "5 phút trước",
            unread = true,
        )

        Spacer(Modifier.height(10.dp))

        NotificationCard(
            icon = Icons.Outlined.ShoppingBag,
            title = "Nhắc đi chợ",
            body = "Bạn còn 9 món chưa mua cho thực đơn tuần này.",
            time = "30 phút trước",
            unread = true,
        )

        Spacer(Modifier.height(10.dp))

        // Thẻ Cảnh báo ngân sách màu hồng đỏ nhạt
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFBEAE8))
                .border(1.dp, Color(0xFFF5C6CB), RoundedCornerShape(16.dp))
                .padding(14.dp),
        ) {
            Column {
                Text(
                    text = "Cảnh báo ngân sách",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC0392B),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Bạn đã dùng 85% ngân sách tuần (595.000đ/700.000đ).",
                    fontSize = 13.sp,
                    color = AppColors.Ink,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "2 giờ trước",
                    fontSize = 11.sp,
                    color = AppColors.Muted,
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // TRƯỚC ĐÓ
        Text(
            text = "TRƯỚC ĐÓ",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Muted,
            letterSpacing = 0.5.sp,
        )

        Spacer(Modifier.height(10.dp))

        NotificationCard(
            icon = Icons.Outlined.AutoAwesome,
            title = "Gợi ý nguyên liệu",
            body = "Cà rốt và rau bó xôi có thể dùng lại cho bữa trưa Thứ Năm.",
            time = "Hôm qua",
            unread = false,
        )

        Spacer(Modifier.height(10.dp))

        NotificationCard(
            icon = Icons.Outlined.BarChart,
            title = "Tổng kết tuần",
            body = "Tuần trước bạn tiết kiệm được 60.000đ. Xem thống kê.",
            time = "Hôm qua",
            unread = false,
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun NotificationCard(
    icon: ImageVector,
    title: String,
    body: String,
    time: String,
    unread: Boolean,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
            .padding(14.dp),
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppColors.GreenSoft),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = AppColors.Green, modifier = Modifier.size(20.dp))
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                Spacer(Modifier.height(2.dp))
                Text(text = body, fontSize = 13.sp, color = AppColors.Muted, lineHeight = 18.sp)
                Spacer(Modifier.height(4.dp))
                Text(text = time, fontSize = 11.sp, color = AppColors.Muted)
            }

            if (unread) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(AppColors.Green),
                )
            }
        }
    }
}
