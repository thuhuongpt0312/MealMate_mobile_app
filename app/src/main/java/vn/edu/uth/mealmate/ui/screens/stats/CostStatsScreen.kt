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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

enum class StatsViewState {
    NORMAL,
    EMPTY,
    LOADING,
}

@Composable
fun CostStatsScreen(onBack: () -> Unit = {}) {
    var period by remember { mutableStateOf(StatsPeriod.WEEK) }
    var viewState by remember { mutableStateOf(StatsViewState.NORMAL) }

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

    val comparisonText = when (period) {
        StatsPeriod.WEEK -> "+4,2% so với kỳ trước"
        StatsPeriod.MONTH -> "-8% so với kỳ trước"
        StatsPeriod.QUARTER -> "+2,1% so với kỳ trước"
        StatsPeriod.YEAR -> "-5,4% so với kỳ trước"
    }
    val isPositiveComparison = period == StatsPeriod.WEEK || period == StatsPeriod.QUARTER

    // Biểu đồ thời gian theo chu kỳ được chọn
    val timeBars = when (period) {
        StatsPeriod.WEEK -> listOf(
            "T2" to 0.45f,
            "T3" to 0.55f,
            "T4" to 0.60f,
            "T5" to 0.85f,
            "T6" to 0.60f,
            "T7" to 0.95f,
            "CN" to 0.50f,
        )
        StatsPeriod.MONTH -> listOf(
            "Tuần 1" to 0.60f,
            "Tuần 2" to 0.75f,
            "Tuần 3" to 0.95f,
            "Tuần 4" to 0.70f,
            "Tuần 5" to 0.45f,
        )
        StatsPeriod.QUARTER -> listOf(
            "T1" to 0.65f,
            "T2" to 0.95f,
            "T3" to 0.85f,
        )
        StatsPeriod.YEAR -> (1..12).map {
            "T$it" to if (it == 6) 0.95f else (0.45f + ((it * 7) % 5) * 0.1f)
        }
    }

    // Chi tiêu theo nhóm thực phẩm
    val groupStats = listOf(
        Triple("Thịt & đạm", "310.000đ", 0.48f),
        Triple("Rau củ", "180.000đ", 0.28f),
        Triple("Đồ khô", "110.000đ", 0.17f),
        Triple("Sữa & trứng", "50.000đ", 0.07f),
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
                        .clickable {
                            period = p
                            viewState = StatsViewState.NORMAL
                        }
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
            IconButton(
                onClick = {
                    viewState = if (viewState == StatsViewState.NORMAL) StatsViewState.EMPTY else StatsViewState.NORMAL
                },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(Icons.Filled.ChevronLeft, contentDescription = "Trước", tint = AppColors.Muted)
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = periodTitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {
                    viewState = if (viewState == StatsViewState.NORMAL) StatsViewState.EMPTY else StatsViewState.NORMAL
                },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(Icons.Filled.ChevronRight, contentDescription = "Sau", tint = AppColors.Muted)
            }
        }

        Spacer(Modifier.height(14.dp))

        when (viewState) {
            StatsViewState.EMPTY -> {
                // S15 - Trạng thái trống
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(AppColors.Surface)
                        .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                        .padding(vertical = 48.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(AppColors.GreenSoft),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.BarChart,
                                contentDescription = null,
                                tint = AppColors.Green,
                                modifier = Modifier.size(32.dp),
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Chưa có dữ liệu chi tiêu cho giai đoạn này",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Ink,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "Hãy thêm chi phí mua sắm để xem báo cáo.",
                            fontSize = 13.sp,
                            color = AppColors.Muted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        )

                        Spacer(Modifier.height(18.dp))

                        Button(
                            onClick = {
                                period = StatsPeriod.WEEK
                                viewState = StatsViewState.NORMAL
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Green),
                            shape = RoundedCornerShape(20.dp),
                        ) {
                            Text("Xem tuần này", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            StatsViewState.LOADING -> {
                // S15 - Đang tải (Skeleton)
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(AppColors.Line.copy(alpha = 0.5f)),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(AppColors.Line.copy(alpha = 0.5f)),
                    )
                }
            }

            StatsViewState.NORMAL -> {
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
                                .background(if (isPositiveComparison) Color(0xFFE8F5E9) else Color(0xFFFBEAE8))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = comparisonText,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isPositiveComparison) AppColors.GreenDark else Color(0xFFC0392B),
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
                        timeBars.forEach { (label, fraction) ->
                            val isPeak = fraction > 0.9f
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(if (timeBars.size > 7) 16.dp else 24.dp)
                                        .height((110 * fraction).dp)
                                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                        .background(if (isPeak) AppColors.Orange else AppColors.Green),
                                )
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = label,
                                    fontSize = if (timeBars.size > 7) 9.sp else 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppColors.Muted,
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Chi tiêu theo nhóm thực phẩm (S15 Figma)
                Text(
                    text = "Chi tiêu theo nhóm thực phẩm",
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
                        .padding(16.dp),
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        groupStats.forEach { (name, cost, percent) ->
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(text = name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Ink)
                                    Text(text = cost, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                                }
                                Spacer(Modifier.height(6.dp))
                                LinearProgressIndicator(
                                    progress = { percent },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = AppColors.Green,
                                    trackColor = AppColors.Line,
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
