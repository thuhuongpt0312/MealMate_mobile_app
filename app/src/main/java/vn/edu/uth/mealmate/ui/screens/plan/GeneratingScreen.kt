package vn.edu.uth.mealmate.ui.screens.plan

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.CreatePlanRequest
import vn.edu.uth.mealmate.ui.components.MealMateBrandBadge
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun GeneratingScreen(onDone: () -> Unit) {
    var completedStep by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        // Gọi API tạo thực đơn trong lúc chạy animation
        runCatching {
            ApiClient.api.createPlan(CreatePlanRequest(servings = 3))
        }

        delay(600)
        completedStep = 1
        delay(700)
        completedStep = 2
        delay(700)
        completedStep = 3
        delay(600)
        completedStep = 4
        delay(400)
        onDone()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "angle",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            MealMateBrandBadge()
        }

        Spacer(Modifier.height(40.dp))

        // Vòng tròn minh họa AI sinh thực đơn với hiệu ứng quay
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .background(AppColors.GreenSoft),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .rotate(angle),
                contentAlignment = Alignment.TopCenter,
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(AppColors.Orange),
                )
            }
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = AppColors.Green,
                modifier = Modifier.size(44.dp),
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Tuần mới đang dần hoàn\nthiện",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
            lineHeight = 30.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "AI đang cân bằng khẩu vị, ngân sách và\nnguyên liệu sẵn có.",
            fontSize = 13.sp,
            color = AppColors.Muted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 18.sp,
        )

        Spacer(Modifier.height(36.dp))

        // Checklist tiến độ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(20.dp))
                .padding(20.dp),
        ) {
            Column {
                CheckStepItem("Kiểm tra khẩu vị", isDone = completedStep >= 1)
                Spacer(Modifier.height(14.dp))
                CheckStepItem("Cân đối ngân sách", isDone = completedStep >= 2)
                Spacer(Modifier.height(14.dp))
                CheckStepItem("Sắp xếp bữa ăn", isDone = completedStep >= 3)
                Spacer(Modifier.height(14.dp))
                CheckStepItem("Tạo danh sách mua sắm", isDone = completedStep >= 4)
            }
        }
    }
}

@Composable
private fun CheckStepItem(label: String, isDone: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isDone) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = AppColors.Green,
                modifier = Modifier.size(20.dp),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(AppColors.Line),
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isDone) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isDone) AppColors.Ink else AppColors.Muted,
        )
    }
}
