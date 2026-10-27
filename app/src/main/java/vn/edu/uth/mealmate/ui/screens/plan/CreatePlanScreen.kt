package vn.edu.uth.mealmate.ui.screens.plan

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun CreatePlanScreen(
    onBack: () -> Unit = {},
    onCreate: () -> Unit = {},
) {
    var selectedDays by remember { mutableIntStateOf(7) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        HeaderBar(
            title = "Tạo thực đơn",
            subtitle = "Bước 1/4",
            onBack = onBack,
        )

        Spacer(Modifier.height(20.dp))

        // Calendar Icon Box
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(AppColors.GreenSoft),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = AppColors.Green,
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Lên kế hoạch cho tuần mới",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "$selectedDays ngày · 3 người",
            fontSize = 14.sp,
            color = AppColors.Muted,
        )

        Spacer(Modifier.height(24.dp))

        // Days duration options: 3 ngày, 5 ngày, 7 ngày
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            listOf(3, 5, 7).forEach { days ->
                val selected = days == selectedDays
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (selected) AppColors.GreenSoft else AppColors.Surface)
                        .border(
                            width = if (selected) 2.dp else 1.dp,
                            color = if (selected) AppColors.Green else AppColors.Line,
                            shape = RoundedCornerShape(14.dp),
                        )
                        .clickable { selectedDays = days }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "$days ngày",
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                        color = if (selected) AppColors.GreenDark else AppColors.Ink,
                        fontSize = 14.sp,
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Tiết kiệm thông minh Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(AppColors.GreenSoft.copy(alpha = 0.6f))
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AppColors.Green,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Tiết kiệm thông minh",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.GreenDark,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "MealMate sẽ tái sử dụng nguyên liệu hợp lý để giảm lãng phí.",
                        fontSize = 13.sp,
                        color = AppColors.Muted,
                        lineHeight = 18.sp,
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            label = "Tiếp tục",
            onClick = onCreate,
        )
    }
}
