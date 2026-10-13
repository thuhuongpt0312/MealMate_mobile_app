package vn.edu.uth.mealmate.ui.screens.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vn.edu.uth.mealmate.ui.theme.AppColors

enum class StatsPeriod(val label: String) {
    WEEK("Tuần"),
    MONTH("Tháng"),
    QUARTER("3 tháng"),
    YEAR("Năm"),
}

/**
 * S15 · Thống kê chi phí. Phụ trách: C.
 * 6 trạng thái trong Figma: tuần, tháng, 3 tháng, năm, trống, đang tải.
 * TODO: thẻ tổng quan (tổng chi, % ngân sách, so với kỳ trước, TB/bữa, TB/người),
 *       biểu đồ cột có đường ngân sách (cột vượt ngân sách tô cam), biểu đồ theo nhóm thực phẩm,
 *       trạng thái trống và skeleton.
 */
@Composable
fun CostStatsScreen(onBack: () -> Unit = {}) {
    var period by remember { mutableStateOf(StatsPeriod.WEEK) }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Thống kê chi phí", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Figma: S15", color = AppColors.Muted)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            StatsPeriod.values().forEach { p ->
                FilterChip(selected = p == period, onClick = { period = p }, label = { Text(p.label) })
            }
        }
        Text("Khoảng thời gian đang chọn: ${period.label}", color = AppColors.Muted)
        OutlinedButton(onClick = onBack) { Text("Quay lại") }
    }
}
