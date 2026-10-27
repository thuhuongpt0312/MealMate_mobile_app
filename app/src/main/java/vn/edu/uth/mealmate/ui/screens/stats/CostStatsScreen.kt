package vn.edu.uth.mealmate.ui.screens.stats

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.theme.AppColors

enum class StatsPeriod(val label: String) {
    WEEK("Tuần"),
    MONTH("Tháng"),
    QUARTER("3 tháng"),
    YEAR("Năm"),
}

@Composable
fun CostStatsScreen(onBack: () -> Unit = {}) {
    var period by remember { mutableStateOf(StatsPeriod.WEEK) }

    val periodTitle = when (period) {
        StatsPeriod.WEEK -> "11–17/03"
        StatsPeriod.MONTH -> "Tháng 03/2026"
        StatsPeriod.QUARTER -> "Quý 1/2026"
        StatsPeriod.YEAR -> "Năm 2026"
    }

    val totalSpent = when (period) {
        StatsPeriod.WEEK -> "650.000đ"
        StatsPeriod.MONTH -> "2.480.000đ"
        StatsPeriod.QUARTER -> "7.350.000đ"
        StatsPeriod.YEAR -> "28.900.000đ"
    }

    val budgetText = when (period) {
        StatsPeriod.WEEK -> "Ngân sách 700.000đ"
        StatsPeriod.MONTH -> "Ngân sách 2.800.000đ"
        StatsPeriod.QUARTER -> "Ngân sách 8.400.000đ"
        StatsPeriod.YEAR -> "Ngân sách 33.600.000đ"
    }

    val dayBars = listOf(
        "T2" to 0.45f,
        "T3" to 0.55f,
        "T4" to 0.60f,
        "T5" to 0.85f,
        "T6" to 0.60f,
        "T7" to 0.95f,
        "CN" to 0.50f,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Thống kê chi phí",
            onBack = onBack,
        )

        Spacer(Modifier.height(8.dp))

        // Segmented Control 4 tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            StatsPeriod.entries.forEach { p ->
                val isSelected = p == period
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) AppColors.Ink else Color.Transparent)
                        .clickable { period = p }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = p.label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else AppColors.Ink,
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        // Period Navigator (< 11-17/03 >)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Filled.ChevronLeft, contentDescription = null, tint = AppColors.Muted)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = periodTitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = { }, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = AppColors.Muted)
            }
        }

        Spacer(Modifier.height(14.dp))

        // Card Tổng chi
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .padding(18.dp),
        ) {
            Column {
                Text(
                    text = "TỔNG CHI",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Muted,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = totalSpent,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.Ink,
                )

                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = budgetText, fontSize = 12.sp, color = AppColors.Muted)
                    Text(text = "92%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AppColors.Green)
                }

                Spacer(Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { 0.92f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AppColors.Green,
                    trackColor = AppColors.Line,
                )

                Spacer(Modifier.height(14.dp))

                // Badge so với kỳ trước
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = "+4,2% so với kỳ trước",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.GreenDark,
                    )
                }

                Spacer(Modifier.height(14.dp))

                // 2 Thẻ con
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppColors.Cream)
                            .padding(12.dp),
                    ) {
                        Column {
                            Text(text = "31.000đ", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                            Text(text = "Trung bình/bữa", fontSize = 11.sp, color = AppColors.Muted)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppColors.Cream)
                            .padding(12.dp),
                    ) {
                        Column {
                            Text(text = "10.300đ", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                            Text(text = "Trung bình/người", fontSize = 11.sp, color = AppColors.Muted)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Biểu đồ Chi tiêu theo thời gian
        Text(
            text = "Chi tiêu theo thời gian",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )

        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(18.dp))
                .padding(18.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                dayBars.forEach { (day, fraction) ->
                    val isPeak = fraction > 0.8f
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height((110 * fraction).dp)
                                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                .background(if (isPeak) AppColors.Orange else AppColors.Green),
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = day,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.Muted,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
