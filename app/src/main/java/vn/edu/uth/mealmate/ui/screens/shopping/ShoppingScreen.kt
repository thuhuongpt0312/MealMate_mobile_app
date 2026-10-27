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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.theme.AppColors

private data class ShoppingUiItem(val id: String, val name: String, val amountText: String, var bought: Boolean)

@Composable
fun ShoppingScreen(onPantry: () -> Unit = {}) {
    val vegItems = remember {
        mutableStateListOf(
            ShoppingUiItem("1", "Cà rốt", "4 củ", true),
            ShoppingUiItem("2", "Rau bó xôi", "2 túi", false),
            ShoppingUiItem("3", "Hành lá", "1 bó", false),
            ShoppingUiItem("4", "Bơ", "3 quả", false),
        )
    }

    val meatItems = remember {
        mutableStateListOf(
            ShoppingUiItem("5", "Ức gà", "1,2 kg", true),
            ShoppingUiItem("6", "Trứng", "12 quả", false),
        )
    }

    val totalBought = vegItems.count { it.bought } + meatItems.count { it.bought }
    val totalItems = vegItems.size + meatItems.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(16.dp))

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
                Text(
                    text = "Cho thực đơn 11–17/03",
                    fontSize = 13.sp,
                    color = AppColors.Muted,
                )
            }

            IconButton(
                onClick = onPantry,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.Line, CircleShape),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Thêm món", tint = AppColors.Ink)
            }
        }

        Spacer(Modifier.height(16.dp))

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
                        text = "510.000đ",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AppColors.Ink,
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

        Spacer(Modifier.height(24.dp))

        // Nhóm Rau củ
        Text(
            text = "Rau củ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(10.dp))

        vegItems.forEachIndexed { index, item ->
            ShoppingRow(
                name = "${item.name} · ${item.amountText}",
                isBought = item.bought,
                onToggle = { vegItems[index] = item.copy(bought = !item.bought) },
            )
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(20.dp))

        // Nhóm Thịt & đạm
        Text(
            text = "Thịt & đạm",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(10.dp))

        meatItems.forEachIndexed { index, item ->
            ShoppingRow(
                name = "${item.name} · ${item.amountText}",
                isBought = item.bought,
                onToggle = { meatItems[index] = item.copy(bought = !item.bought) },
            )
            Spacer(Modifier.height(8.dp))
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun ShoppingRow(
    name: String,
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
            )
        }
    }
}
