package vn.edu.uth.mealmate.ui.screens.history

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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
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
import java.time.LocalDate
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.formatDateRange
import vn.edu.uth.mealmate.util.formatVnd
import vn.edu.uth.mealmate.util.parseApiDate

@Composable
fun HistoryScreen(
    onOpenPlan: () -> Unit = {},
    onCreatePlan: () -> Unit = {},
    vm: HistoryViewModel = viewModel(),
) {
    val uiState by vm.uiState.collectAsState()
    val open: (MealPlanDto) -> Unit = { plan ->
        vm.openPlan(plan.id)
        onOpenPlan()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Lịch sử thực đơn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Text(
            text = "Dùng lại một tuần phù hợp với bạn",
            fontSize = 13.sp,
            color = AppColors.Muted,
        )

        Spacer(Modifier.height(16.dp))

        when (val state = uiState) {
            HistoryUiState.Loading -> LoadingView()
            is HistoryUiState.Error -> MessageView(
                title = "Không tải được lịch sử",
                message = state.message,
                actionLabel = "Thử lại",
                onAction = { vm.loadHistory() },
            )
            is HistoryUiState.Success -> if (state.plans.isEmpty()) {
                MessageView(
                    title = "Chưa có thực đơn nào",
                    message = "Các tuần bạn đã lên thực đơn sẽ được lưu ở đây để dùng lại.",
                    actionLabel = "Tạo thực đơn",
                    onAction = onCreatePlan,
                )
            } else {
                HistoryContent(state.plans, onOpen = open)
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun HistoryContent(plans: List<MealPlanDto>, onOpen: (MealPlanDto) -> Unit) {
    val latest = plans.first()
    val today = LocalDate.now()
    val isCurrent = parseApiDate(latest.startDate)?.let { !today.isBefore(it) } == true &&
        parseApiDate(latest.endDate)?.let { !today.isAfter(it) } == true

    run {
        // Featured Card: thực đơn gần nhất
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .clickable { onOpen(latest) }
                .padding(18.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AppColors.GreenSoft)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        Text(
                            text = if (isCurrent) "TUẦN NÀY" else "GẦN NHẤT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.GreenDark,
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Thực đơn ${latest.days.size} ngày",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Ink,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${formatDateRange(latest.startDate, latest.endDate)} · ${latest.servings} người",
                        fontSize = 12.sp,
                        color = AppColors.Muted,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "${formatVnd(latest.totalCost)} · ${latest.days.sumOf { it.meals.size }} bữa",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.GreenDark,
                    )
                }

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColors.GreenSoft),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Restaurant,
                        contentDescription = null,
                        tint = AppColors.Green,
                        modifier = Modifier.size(32.dp),
                    )
                }
            }
        }

        if (plans.size == 1) return

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Các tuần trước",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )

        Spacer(Modifier.height(12.dp))

        plans.drop(1).forEach { plan ->
            val title = "Thực đơn ${formatDateRange(plan.startDate, plan.endDate)}"
            val cost = "${formatVnd(plan.totalCost)} · ${plan.servings} người"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
                    .clickable { onOpen(plan) }
                    .padding(14.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AppColors.GreenSoft.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = AppColors.Green,
                            modifier = Modifier.size(20.dp),
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.Ink,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = cost,
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
            Spacer(Modifier.height(10.dp))
        }
    }
}
