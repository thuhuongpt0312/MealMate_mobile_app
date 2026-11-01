package vn.edu.uth.mealmate.ui.screens.shopping

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.domain.groupForShopping
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.formatDateRange
import vn.edu.uth.mealmate.util.formatQuantity
import vn.edu.uth.mealmate.util.formatVnd

@Composable
fun ShoppingScreen(
    onPantry: () -> Unit = {},
    onCreatePlan: () -> Unit = {},
    vm: ShoppingViewModel = viewModel(),
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

        val plan = (uiState as? ShoppingUiState.Success)?.plan
        // Header with + button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "Danh sách mua sắm",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Ink,
                )
                if (plan != null) {
                    Text(
                        text = "Cho thực đơn ${formatDateRange(plan.startDate, plan.endDate)} · đã trừ đồ trong tủ bếp",
                        fontSize = 13.sp,
                        color = AppColors.Muted,
                    )
                }
            }

            IconButton(
                onClick = onPantry,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.Line, CircleShape),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Cập nhật tủ bếp", tint = AppColors.Ink)
            }
        }

        Spacer(Modifier.height(16.dp))

        when (val state = uiState) {
            ShoppingUiState.Loading -> LoadingView()
            ShoppingUiState.NoPlan -> MessageView(
                title = "Chưa có danh sách mua sắm",
                message = "Tạo thực đơn để MealMate tính sẵn những nguyên liệu cần mua.",
                actionLabel = "Tạo thực đơn",
                onAction = onCreatePlan,
            )
            is ShoppingUiState.Error -> MessageView(
                title = "Không tải được danh sách",
                message = state.message,
                actionLabel = "Thử lại",
                onAction = vm::load,
            )
            is ShoppingUiState.Success -> ShoppingContent(state, onToggle = vm::toggleBought, onDismissMessage = vm::clearMessage)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ShoppingContent(
    state: ShoppingUiState.Success,
    onToggle: (String) -> Unit,
    onDismissMessage: () -> Unit,
) {
    val items = state.list.items
    val totalBought = items.count { it.bought }
    val totalItems = items.size

    // Card Tổng dự kiến & tiến độ đã mua
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
            .padding(18.dp),
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
                    color = AppColors.Muted,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = formatVnd(state.list.total),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.Ink,
                )
                Text(
                    text = "Còn phải mua ${formatVnd(state.list.remaining)}",
                    fontSize = 12.sp,
                    color = AppColors.Muted,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Đã mua $totalBought/$totalItems món",
                    fontSize = 12.sp,
                    color = AppColors.Muted,
                )
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { if (totalItems > 0) totalBought.toFloat() / totalItems else 0f },
                    modifier = Modifier
                        .width(80.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AppColors.Green,
                    trackColor = AppColors.Line,
                )
            }
        }
    }

    if (state.message != null) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = state.message,
            fontSize = 13.sp,
            color = AppColors.Orange,
            modifier = Modifier.clickable { onDismissMessage() },
        )
    }

    if (items.isEmpty()) {
        MessageView(
            title = "Không cần mua thêm gì",
            message = "Tủ bếp của bạn đã đủ nguyên liệu cho thực đơn này.",
        )
        return
    }

    groupForShopping(items, { it.group }, { it.bought }).forEach { (group, groupItems) ->
        Spacer(Modifier.height(24.dp))
        Text(
            text = group.label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(10.dp))

        groupItems.forEach { item ->
            ShoppingRow(
                name = "${item.name} · ${formatQuantity(item.amount, item.unit)}",
                cost = formatVnd(item.cost),
                isBought = item.bought,
                onToggle = { onToggle(item.id) },
            )
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ShoppingRow(
    name: String,
    cost: String,
    isBought: Boolean,
    onToggle: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(14.dp))
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isBought) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = AppColors.Green,
                    modifier = Modifier.size(22.dp),
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = AppColors.Line,
                    modifier = Modifier.size(22.dp),
                )
            }

            Spacer(Modifier.width(14.dp))

            Text(
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isBought) AppColors.Muted else AppColors.Ink,
                textDecoration = if (isBought) TextDecoration.LineThrough else null,
                modifier = Modifier.weight(1f),
            )
            Text(text = cost, fontSize = 13.sp, color = AppColors.Muted)
        }
    }
}
