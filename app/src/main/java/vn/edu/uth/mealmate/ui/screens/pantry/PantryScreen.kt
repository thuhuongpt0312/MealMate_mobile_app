package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.theme.AppColors

private data class PantryItemDisplay(val name: String, val amount: String, val badgeColor: Color)

private val pantrySampleList = listOf(
    PantryItemDisplay("Gạo thơm", "750 g", Color(0xFFDCC8B4)),
    PantryItemDisplay("Trứng", "8 quả", Color(0xFFF7E4A8)),
    PantryItemDisplay("Ức gà", "450 g", Color(0xFFE8B2A6)),
    PantryItemDisplay("Rau bó xôi", "1 túi", Color(0xFFAFD6BE)),
    PantryItemDisplay("Cà rốt", "5 củ", Color(0xFFF2BA8C)),
    PantryItemDisplay("Nước tương", "½ chai", Color(0xFFD8D2C5)),
)

@Composable
fun PantryScreen(onBack: () -> Unit = {}) {
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
        )

        Spacer(Modifier.height(12.dp))

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
                        text = "6 nguyên liệu có thể dùng cho thực đơn tuần này.",
                        fontSize = 13.sp,
                        color = AppColors.Muted,
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Danh sách nguyên liệu trong tủ bếp
        pantrySampleList.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.Surface)
                    .border(1.dp, AppColors.Line, RoundedCornerShape(16.dp))
                    .padding(14.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(item.badgeColor),
                    )

                    Spacer(Modifier.width(14.dp))

                    Text(
                        text = "${item.name} · ${item.amount}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Ink,
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }

        Spacer(Modifier.height(24.dp))
    }
}
