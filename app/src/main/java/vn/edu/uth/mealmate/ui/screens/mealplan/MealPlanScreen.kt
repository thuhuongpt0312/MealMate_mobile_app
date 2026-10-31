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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.data.model.MealSlot
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.budgetPercent
import vn.edu.uth.mealmate.util.formatDateRange
import vn.edu.uth.mealmate.util.formatVnd
import vn.edu.uth.mealmate.util.indexOfToday
import vn.edu.uth.mealmate.util.longLabel
import vn.edu.uth.mealmate.util.parseApiDate
import vn.edu.uth.mealmate.util.shortLabel

@Composable
fun MealPlanScreen(
    onOpenMeal: (mealId: String, servings: Int) -> Unit = { _, _ -> },
    onShopping: () -> Unit = {},
    onCreatePlan: () -> Unit = {},
    vm: MealPlanViewModel = viewModel(),
) {
    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

        when (val state = uiState) {
            MealPlanUiState.Loading -> {
                PlanTitle(subtitle = "Đang tải thực đơn…")
                LoadingView()
            }
            MealPlanUiState.Empty -> {
                PlanTitle(subtitle = "Chưa có thực đơn")
                MessageView(
                    title = "Bạn chưa có thực đơn nào",
                    message = "Tạo thực đơn để MealMate lên món và danh sách mua sắm cho cả tuần.",
                    actionLabel = "Tạo thực đơn",
                    onAction = onCreatePlan,
                )
            }
            is MealPlanUiState.Error -> {
                PlanTitle(subtitle = "")
                MessageView(title = "Không tải được thực đơn", message = state.message, actionLabel = "Thử lại", onAction = vm::load)
            }
            is MealPlanUiState.Success -> PlanContent(state.plan, onOpenMeal, onShopping)
        }

        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun PlanTitle(subtitle: String) {
    Text(
        text = "Thực đơn tuần",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Ink,
    )
    if (subtitle.isNotEmpty()) {
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = AppColors.Muted,
        )
    }
}

@Composable
private fun PlanContent(
    plan: MealPlanDto,
    onOpenMeal: (String, Int) -> Unit,
    onShopping: () -> Unit,
) {
    val dates = remember(plan.id) { plan.days.map { parseApiDate(it.date) } }
    var selectedDayIndex by rememberSaveable(plan.id) { mutableIntStateOf(indexOfToday(dates)) }
    val budget = plan.budget
    val percent = budgetPercent(plan.totalCost, budget)
    val overBudget = budget != null && plan.totalCost > budget
    val accent = if (overBudget) AppColors.Orange else Color(0xFFA3E2BC)

    PlanTitle(subtitle = "${formatDateRange(plan.startDate, plan.endDate)} · ${plan.servings} người")

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
                    text = formatVnd(plan.totalCost),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                )
                if (budget != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (overBudget) "Vượt ngân sách ${formatVnd(budget)}" else "$percent% ngân sách ${formatVnd(budget)}",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
            }

            if (budget != null) {
                // Vòng tròn tỷ lệ so với ngân sách, đổi màu cam khi vượt
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(2.dp, accent, CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "$percent%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
        }
    }

    Spacer(Modifier.height(18.dp))

    // Thanh chọn ngày trong thực đơn
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(plan.days.size) { index ->
            val date = dates[index]
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
                        text = date?.dayOfWeek?.shortLabel() ?: "N${index + 1}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isSelected) Color.White.copy(alpha = 0.7f) else AppColors.Muted,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = date?.dayOfMonth?.toString() ?: "${index + 1}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else AppColors.Ink,
                    )
                }
            }
        }
    }

    Spacer(Modifier.height(20.dp))

    val day = plan.days.getOrNull(selectedDayIndex)
    Text(
        text = dates.getOrNull(selectedDayIndex)?.let { "Bữa ăn ${it.dayOfWeek.longLabel()}" } ?: "Bữa ăn trong ngày",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = AppColors.Ink,
    )

    Spacer(Modifier.height(12.dp))

    day?.meals.orEmpty().forEach { mealId ->
        val recipe = plan.recipes?.get(mealId)
        MealCardItem(
            slotName = recipe?.slot?.let { slot -> MealSlot.entries.find { it.name == slot }?.label }?.uppercase() ?: "MÓN ĂN",
            title = recipe?.name ?: mealId,
            subtitle = recipe?.let { "${it.minutes} phút · ${formatVnd(it.pricePerServing)}/phần" }.orEmpty(),
            onClick = { onOpenMeal(mealId, plan.servings) },
        )
        Spacer(Modifier.height(10.dp))
    }

    Spacer(Modifier.height(10.dp))
    PrimaryButton(label = "Xem danh sách mua sắm", onClick = onShopping)
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
