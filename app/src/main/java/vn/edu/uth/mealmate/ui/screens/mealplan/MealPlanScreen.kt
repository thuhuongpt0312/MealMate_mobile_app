package vn.edu.uth.mealmate.ui.screens.mealplan

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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.data.sample.SampleData
import vn.edu.uth.mealmate.ui.theme.AppColors

private data class DayItem(val dayName: String, val date: Int)

private val weekDays = listOf(
    DayItem("T2", 11),
    DayItem("T3", 12),
    DayItem("T4", 13),
    DayItem("T5", 14),
    DayItem("T6", 15),
    DayItem("T7", 16),
    DayItem("CN", 17),
)

@Composable
fun MealPlanScreen(
    onOpenMeal: () -> Unit = {},
    onShopping: () -> Unit = {},
    vm: MealPlanViewModel = viewModel(),
) {
    var selectedDayIndex by remember { mutableIntStateOf(1) } // T3 mặc định như Figma
    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

        // Header
        Text(
            text = "Thực đơn tuần",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Text(
            text = "11-17/03 · 3 người",
            fontSize = 13.sp,
            color = AppColors.Muted,
        )

        Spacer(Modifier.height(16.dp))

        // Summary Card xanh đậm theo Figma
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(AppColors.GreenDark)
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "TỔNG DỰ KIẾN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA3E2BC),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "650.000đ",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "92% ngân sách 700.000đ",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }

                // Vòng tròn tỷ lệ 92%
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(2.dp, Color(0xFFA3E2BC), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "92%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        // Thanh chọn ngày trong tuần
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(weekDays.size) { index ->
                val day = weekDays[index]
                val isSelected = index == selectedDayIndex
                Box(
                    modifier = Modifier
                        .width(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) AppColors.GreenDark else AppColors.Surface)
                        .border(1.dp, if (isSelected) AppColors.GreenDark else AppColors.Line, RoundedCornerShape(12.dp))
                        .clickable { selectedDayIndex = index }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day.dayName,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) Color.White.copy(alpha = 0.7f) else AppColors.Muted,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = "${day.date}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else AppColors.Ink,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        val currentDayTitle = when (selectedDayIndex) {
            0 -> "Bữa ăn Thứ Hai"
            1 -> "Bữa ăn Thứ Ba"
            2 -> "Bữa ăn Thứ Tư"
            3 -> "Bữa ăn Thứ Năm"
            4 -> "Bữa ăn Thứ Sáu"
            5 -> "Bữa ăn Thứ Bảy"
            else -> "Bữa ăn Chủ Nhật"
        }

        Text(
            text = currentDayTitle,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )

        Spacer(Modifier.height(12.dp))

        // Danh sách 4 bữa ăn trong ngày theo mẫu Figma
        SampleData.meals.forEach { meal ->
            MealCardItem(
                slotName = meal.slot.label.uppercase(),
                title = meal.name,
                subtitle = "${meal.minutes} phút · ${meal.pricePerServing}đ/phần",
                onClick = onOpenMeal,
            )
            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun MealCardItem(
    slotName: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.GreenSoft),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Restaurant,
                    contentDescription = null,
                    tint = AppColors.Green,
                    modifier = Modifier.size(24.dp),
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = slotName,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Green,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
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
}
