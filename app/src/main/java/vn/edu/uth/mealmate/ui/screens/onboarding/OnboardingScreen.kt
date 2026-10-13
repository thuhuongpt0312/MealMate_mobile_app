package vn.edu.uth.mealmate.ui.screens.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors

private data class Slide(val label: String, val title: String, val body: String)

// TODO: đối chiếu nội dung slide 2, 3 với Figma và thay khối ảnh giữ chỗ bằng ảnh thật (res/drawable).
private val slides = listOf(
    Slide(
        "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        "Bữa ăn hợp với nhịp sống của bạn",
        "MealMate kết hợp ngân sách, khẩu vị và mục tiêu để mỗi tuần nhẹ nhàng hơn.",
    ),
    Slide(
        "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        "Lên kế hoạch thông minh, có AI hỗ trợ",
        "Gợi ý bữa ăn cân bằng, hợp khẩu vị của bạn.",
    ),
    Slide(
        "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        "Một thực đơn. Một danh sách mua sắm.",
        "Dùng nguyên liệu sẵn có trong tủ bếp và chỉ mua những gì bạn thực sự cần.",
    ),
)

/** S01 · Làm quen. Phụ trách: A. */
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var page by remember { mutableIntStateOf(0) }
    val isLast = page == slides.lastIndex
    val slide = slides[page]

    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 12.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckBox, contentDescription = null, tint = AppColors.Green)
            Spacer(Modifier.width(8.dp))
            Text("MealMate", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.weight(1f))
            if (!isLast) TextButton(onClick = onFinish) { Text("Bỏ qua") }
        }
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(AppColors.GreenSoft),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Filled.Restaurant, contentDescription = null, modifier = Modifier.size(64.dp), tint = AppColors.Green)
        }
        Spacer(Modifier.height(16.dp))
        Text(slide.label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AppColors.Green, letterSpacing = 1.sp)
        Spacer(Modifier.height(8.dp))
        Text(slide.title, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(8.dp))
        Text(slide.body, color = AppColors.Muted)
        Spacer(Modifier.height(16.dp))
        Row {
            slides.indices.forEach { i ->
                Box(
                    Modifier
                        .padding(end = 6.dp)
                        .height(6.dp)
                        .width(if (i == page) 24.dp else 6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (i == page) AppColors.Green else AppColors.Line),
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        PrimaryButton(
            label = if (isLast) "Bắt đầu" else "Tiếp tục",
            onClick = { if (isLast) onFinish() else page++ },
        )
    }
}
