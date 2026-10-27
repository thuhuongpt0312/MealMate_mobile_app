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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@Composable
fun NotificationSettingsScreen(onBack: () -> Unit = {}) {
    var mealReminderOn by remember { mutableStateOf(true) }
    var selectedMealLeadTime by remember { mutableIntStateOf(30) }

    var marketReminderOn by remember { mutableStateOf(true) }

    var budgetAlertOn by remember { mutableStateOf(true) }
    var selectedBudgetThreshold by remember { mutableIntStateOf(85) }

    var weeklySummaryOn by remember { mutableStateOf(true) }
    var recipeSuggestionOn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Cài đặt thông báo",
            onBack = onBack,
        )

        Spacer(Modifier.height(14.dp))

        // Cài đặt 1: Nhắc bữa ăn
        SettingItemWithChips(
            title = "Nhắc bữa ăn",
            subtitle = "Trước $selectedMealLeadTime phút",
            checked = mealReminderOn,
            onCheckedChange = { mealReminderOn = it },
            chips = listOf(15, 30, 60),
            chipLabels = listOf("15", "30", "60 phút"),
            selectedChip = selectedMealLeadTime,
            onChipSelect = { selectedMealLeadTime = it },
        )

        Spacer(Modifier.height(12.dp))

        // Cài đặt 2: Nhắc đi chợ
        SettingItemSimple(
            title = "Nhắc đi chợ",
            subtitle = "Thứ Bảy · 08:00",
            checked = marketReminderOn,
            onCheckedChange = { marketReminderOn = it },
        )

        Spacer(Modifier.height(12.dp))

        // Cài đặt 3: Cảnh báo ngân sách
        SettingItemWithChips(
            title = "Cảnh báo ngân sách",
            subtitle = "Khi đạt $selectedBudgetThreshold%",
            checked = budgetAlertOn,
            onCheckedChange = { budgetAlertOn = it },
            chips = listOf(70, 85, 100),
            chipLabels = listOf("70%", "85%", "100%"),
            selectedChip = selectedBudgetThreshold,
            onChipSelect = { selectedBudgetThreshold = it },
        )

        Spacer(Modifier.height(12.dp))

        // Cài đặt 4: Tổng kết cuối tuần
        SettingItemSimple(
            title = "Tổng kết cuối tuần",
            subtitle = "Chủ Nhật",
            checked = weeklySummaryOn,
            onCheckedChange = { weeklySummaryOn = it },
        )

        Spacer(Modifier.height(12.dp))

        // Cài đặt 5: Gợi ý món mới
        SettingItemSimple(
            title = "Gợi ý món mới",
            subtitle = "Mỗi tuần",
            checked = recipeSuggestionOn,
            onCheckedChange = { recipeSuggestionOn = it },
        )

        Spacer(Modifier.height(20.dp))

        // Khung Giờ yên lặng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = "Giờ yên lặng",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Ink,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Không gửi thông báo trong khung\ngiờ này",
                        fontSize = 12.sp,
                        color = AppColors.Muted,
                        lineHeight = 16.sp,
                    )
                }
                Text(
                    text = "22:00 –\n06:30",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Bạn có thể thay đổi bất cứ lúc nào.",
            fontSize = 12.sp,
            color = AppColors.Muted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SettingItemSimple(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                Spacer(Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 12.sp, color = AppColors.Muted)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = AppColors.Green,
                ),
            )
        }
    }
}

@Composable
private fun SettingItemWithChips(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    chips: List<Int>,
    chipLabels: List<String>,
    selectedChip: Int,
    onChipSelect: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
                    Spacer(Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 12.sp, color = AppColors.Muted)
                }
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = AppColors.Green,
                    ),
                )
            }

            if (checked) {
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    chips.forEachIndexed { i, value ->
                        val isSelected = value == selectedChip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) AppColors.GreenSoft else AppColors.Surface)
                                .border(1.dp, if (isSelected) AppColors.Green else AppColors.Line, RoundedCornerShape(8.dp))
                                .clickable { onChipSelect(value) }
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = chipLabels[i],
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) AppColors.GreenDark else AppColors.Ink,
                            )
                        }
                    }
                }
            }
        }
    }
}
