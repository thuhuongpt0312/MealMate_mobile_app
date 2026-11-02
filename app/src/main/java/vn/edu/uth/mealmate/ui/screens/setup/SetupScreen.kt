package vn.edu.uth.mealmate.ui.screens.setup

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.formatVnd

@Composable
fun SetupScreen(
    onFinish: () -> Unit = {},
    onBack: (() -> Unit)? = null,
    vm: SetupViewModel = viewModel(),
) {
    val state by vm.uiState.collectAsState()
    val goBack: () -> Unit = { if (!vm.back()) onBack?.invoke() }
    // Nút Back của hệ thống lùi về bước trước thay vì thoát màn hình
    BackHandler(enabled = state.step > 1, onBack = goBack)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        HeaderBar(
            title = "Vài điều về bạn",
            subtitle = "Bước ${state.step}/${SetupUiState.STEPS}",
            onBack = if (state.step > 1 || onBack != null) goBack else null,
        )

        Spacer(Modifier.height(16.dp))

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            when (state.step) {
                1 -> ServingsStep(servings = state.servings, onChange = vm::setServings)
                2 -> GoalStep(selected = state.goal, onSelect = vm::setGoal)
                else -> BudgetStep(
                    budget = state.weeklyBudget,
                    onChange = vm::changeBudget,
                    avoid = state.avoid,
                    onToggleAvoid = vm::toggleAvoid,
                )
            }
        }

        if (state.error != null) {
            Text(text = state.error!!, fontSize = 13.sp, color = AppColors.Orange)
            Spacer(Modifier.height(10.dp))
        }

        PrimaryButton(
            label = when {
                state.saving -> "Đang lưu…"
                state.step < SetupUiState.STEPS -> "Tiếp tục"
                else -> "Hoàn tất"
            },
            onClick = { vm.next(onFinish) },
        )
    }
}

@Composable
private fun StepTitle(title: String, subtitle: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Ink,
    )
    Spacer(Modifier.height(4.dp))
    Text(
        text = subtitle,
        fontSize = 14.sp,
        color = AppColors.Muted,
    )
    Spacer(Modifier.height(28.dp))
}

/** Bước 1 · Số người (giữ nguyên giao diện theo Figma). */
@Composable
private fun ServingsStep(servings: Int, onChange: (Int) -> Unit) {
    val planForFamily = servings > 1

    StepTitle("Bạn lên kế hoạch cho ai?", "Bạn có thể thay đổi bất cứ lúc nào.")

    Counter(
        value = "$servings",
        unit = "người",
        onMinus = { onChange(servings - 1) },
        onPlus = { onChange(servings + 1) },
        minusLabel = "Giảm",
        plusLabel = "Tăng",
    )

    Spacer(Modifier.height(20.dp))

    // 2 Lựa chọn
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Option 1: Chỉ mình tôi
        val selectedSolo = !planForFamily
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(AppColors.Surface)
                .border(
                    width = if (selectedSolo) 2.dp else 1.dp,
                    color = if (selectedSolo) AppColors.Green else AppColors.Line,
                    shape = RoundedCornerShape(16.dp),
                )
                .clickable { onChange(1) }
                .padding(16.dp),
        ) {
            Column {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    tint = if (selectedSolo) AppColors.Green else AppColors.Muted,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Chỉ mình tôi",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = AppColors.Ink,
                )
            }
        }

        // Option 2: Gia đình tôi
        val selectedFamily = planForFamily
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(if (selectedFamily) AppColors.GreenSoft.copy(alpha = 0.4f) else AppColors.Surface)
                .border(
                    width = if (selectedFamily) 2.dp else 1.dp,
                    color = if (selectedFamily) AppColors.Green else AppColors.Line,
                    shape = RoundedCornerShape(16.dp),
                )
                .clickable { if (servings == 1) onChange(3) }
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = null,
                        tint = if (selectedFamily) AppColors.Green else AppColors.Muted,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Gia đình\ntôi",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = AppColors.Ink,
                    )
                }
                if (selectedFamily) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = AppColors.Green,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

/** Bước 2 · Mục tiêu ăn uống. */
@Composable
private fun GoalStep(selected: String, onSelect: (String) -> Unit) {
    StepTitle("Mục tiêu của bạn là gì?", "MealMate chọn món theo mục tiêu này.")

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SetupUiState.GOALS.forEach { goal ->
            val isSelected = goal == selected
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isSelected) AppColors.GreenSoft.copy(alpha = 0.4f) else AppColors.Surface)
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) AppColors.Green else AppColors.Line,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clickable { onSelect(goal) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = goal,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = AppColors.Ink,
                    modifier = Modifier.weight(1f),
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = AppColors.Green,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

/** Bước 3 · Ngân sách mỗi tuần và món cần tránh. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BudgetStep(budget: Long, onChange: (Long) -> Unit, avoid: Set<String>, onToggleAvoid: (String) -> Unit) {
    StepTitle("Ngân sách mỗi tuần", "Dùng để cảnh báo khi thực đơn vượt mức chi tiêu.")

    Counter(
        value = formatVnd(budget),
        unit = "mỗi tuần",
        onMinus = { onChange(-SetupUiState.BUDGET_STEP) },
        onPlus = { onChange(SetupUiState.BUDGET_STEP) },
        minusLabel = "Giảm ${formatVnd(SetupUiState.BUDGET_STEP)}",
        plusLabel = "Tăng ${formatVnd(SetupUiState.BUDGET_STEP)}",
    )

    Spacer(Modifier.height(28.dp))
    Text("Món cần tránh", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink)
    Spacer(Modifier.height(4.dp))
    Text("Thực đơn sẽ bỏ các món có nguyên liệu này.", fontSize = 13.sp, color = AppColors.Muted)
    Spacer(Modifier.height(12.dp))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SetupUiState.AVOID_OPTIONS.forEach { item ->
            val selected = item in avoid
            FilterChip(
                selected = selected,
                onClick = { onToggleAvoid(item) },
                label = { Text(item) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppColors.GreenSoft,
                    selectedLabelColor = AppColors.GreenDark,
                ),
            )
        }
    }
}

/** Ô tăng/giảm dùng cho số người và ngân sách. */
@Composable
private fun Counter(
    value: String,
    unit: String,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    minusLabel: String,
    plusLabel: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.GreenSoft.copy(alpha = 0.5f))
            .padding(vertical = 24.dp, horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = onMinus,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface),
            ) {
                Icon(Icons.Filled.Remove, contentDescription = minusLabel, tint = AppColors.Ink)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    fontSize = if (value.length > 4) 30.sp else 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.Ink,
                )
                Text(
                    text = unit,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Muted,
                )
            }

            IconButton(
                onClick = onPlus,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface),
            ) {
                Icon(Icons.Filled.Add, contentDescription = plusLabel, tint = AppColors.Ink)
            }
        }
    }
}
