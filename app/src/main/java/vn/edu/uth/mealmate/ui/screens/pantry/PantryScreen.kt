package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import vn.edu.uth.mealmate.data.remote.PantryItemDto
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.formatQuantity
import kotlin.math.abs

/** Màu ô vuông đầu dòng, chọn theo tên để mỗi nguyên liệu luôn giữ một màu. */
private val badgeColors = listOf(
    Color(0xFFDCC8B4),
    Color(0xFFF7E4A8),
    Color(0xFFE8B2A6),
    Color(0xFFAFD6BE),
    Color(0xFFF2BA8C),
    Color(0xFFD8D2C5),
)

private fun badgeColorOf(name: String) = badgeColors[abs(name.trim().lowercase().hashCode()) % badgeColors.size]

@Composable
fun PantryScreen(
    onBack: () -> Unit = {},
    vm: PantryViewModel = viewModel(),
) {
    val uiState by vm.uiState.collectAsState()
    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    val loaded = uiState as? PantryUiState.Success
    if (showAddDialog && loaded != null) {
        AddPantryItemDialog(
            saving = loaded.saving,
            error = loaded.message,
            onAdd = { name, amount, unit -> vm.addPantryItem(name, amount, unit) { showAddDialog = false } },
            onDismiss = {
                showAddDialog = false
                vm.clearMessage()
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Tủ bếp",
            subtitle = "MealMate lên món từ nguyên liệu bạn có",
            onBack = onBack,
            rightContent = {
                if (loaded != null) {
                    IconButton(
                        onClick = { showAddDialog = true },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppColors.Surface)
                            .border(1.dp, AppColors.Line, CircleShape),
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Thêm nguyên liệu", tint = AppColors.Ink)
                    }
                }
            },
        )

        Spacer(Modifier.height(12.dp))

        when (val state = uiState) {
            PantryUiState.Loading -> LoadingView()
            is PantryUiState.Error -> MessageView(
                title = "Không tải được tủ bếp",
                message = state.message,
                actionLabel = "Thử lại",
                onAction = vm::loadPantry,
            )
            is PantryUiState.Success -> PantryContent(
                state = if (showAddDialog) state.copy(message = null) else state,
                onDelete = vm::deletePantryItem,
                onDismissMessage = vm::clearMessage,
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun PantryContent(
    state: PantryUiState.Success,
    onDelete: (String) -> Unit,
    onDismissMessage: () -> Unit,
) {
    // Banner Tiết kiệm hơn với tủ bếp
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
                    text = "Tiết kiệm hơn với tủ bếp",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.GreenDark,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "${state.items.size} nguyên liệu sẽ được trừ khỏi danh sách mua sắm khi bạn tạo thực đơn mới.",
                    fontSize = 13.sp,
                    color = AppColors.Muted,
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

    Spacer(Modifier.height(20.dp))

    if (state.items.isEmpty()) {
        MessageView(title = "Tủ bếp đang trống", message = "Thêm nguyên liệu bạn đang có để tiết kiệm khi đi chợ.")
    }

    // Danh sách nguyên liệu trong tủ bếp
    state.items.forEach { item ->
        PantryRow(item, onDelete = { onDelete(item.id) })
        Spacer(Modifier.height(10.dp))
    }
}

@Composable
private fun PantryRow(item: PantryItemDto, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppColors.Surface)
            .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
            .padding(start = 14.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(badgeColorOf(item.name)),
            )

            Spacer(Modifier.width(14.dp))

            Text(
                text = "${item.name} · ${formatQuantity(item.amount, item.unit)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.Ink,
                modifier = Modifier.weight(1f),
            )

            IconButton(onClick = onDelete) {
                Icon(Icons.Outlined.DeleteOutline, contentDescription = "Xóa ${item.name}", tint = AppColors.Muted)
            }
        }
    }
}
