package vn.edu.uth.mealmate.ui.screens.nutrition

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun NutritionScreen(onBack: () -> Unit = {}) {
    val dayBars = listOf(
        "T2" to 0.7f,
        "T3" to 0.85f,
        "T4" to 0.65f,
        "T5" to 0.9f,
        "T6" to 0.8f,
        "T7" to 0.5f,
        "CN" to 0.45f,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Dinh dưỡng",
            subtitle = "Tổng quan tuần 11–17/03",
            onBack = onBack,
        )

        Spacer(Modifier.height(14.dp))

        // Card xanh đậm
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(AppColors.GreenDark)
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Circular 82%
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(3.dp, Color(0xFFA3E2BC), CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "82%",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        )
                        Text(
                            text = "Đạt mục tiêu",
                            fontSize = 8.sp,
                            color = Color.White.copy(alpha = 0.7f),
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = "CÂN BẰNG TUẦN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFA3E2BC),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Bạn đang duy trì nhịp rất tốt",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "5/7 ngày đạt mục tiêu dinh dưỡng.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f),
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Biểu đồ Năng lượng mỗi ngày
        Text(
            text = "Năng lượng mỗi ngày",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )

        Spacer(Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .padding(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                dayBars.forEach { (day, fraction) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height((120 * fraction).dp)
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(AppColors.GreenSoft),
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = day,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.Muted,
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
