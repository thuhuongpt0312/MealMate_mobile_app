package vn.edu.uth.mealmate.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.MealImages

private data class Slide(
    val label: String,
    val title: String,
    val body: String,
    val imageRes: Int,
)

private val slides = listOf(
    Slide(
        label = "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        title = "Bữa ăn hợp với nhịp sống của bạn",
        body = "MealMate kết hợp ngân sách, khẩu vị và mục tiêu để mỗi tuần nhẹ nhàng hơn.",
        imageRes = MealImages.MamCom,
    ),
    Slide(
        label = "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        title = "Lên kế hoạch thông minh, có AI hỗ trợ",
        body = "Gợi ý bữa ăn cân bằng, hợp khẩu vị của bạn.",
        imageRes = MealImages.ComGiaDinh,
    ),
    Slide(
        label = "LÊN THỰC ĐƠN THẬT DỄ DÀNG",
        title = "Một thực đơn. Một danh sách mua sắm.",
        body = "Dùng nguyên liệu sẵn có trong tủ bếp và chỉ mua những gì bạn thực sự cần.",
        imageRes = MealImages.RauXanh,
    ),
)

/** S01 · Làm quen. */
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var page by remember { mutableIntStateOf(0) }
    val isLast = page == slides.lastIndex
    val slide = slides[page]

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp, vertical = 12.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckBox, contentDescription = null, tint = AppColors.Green)
            Spacer(Modifier.width(8.dp))
            Text("MealMate", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppColors.Ink)
            Spacer(Modifier.weight(1f))
            if (!isLast) TextButton(onClick = onFinish) { Text("Bỏ qua", color = AppColors.Muted) }
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
            Image(
                painter = painterResource(slide.imageRes),
                contentDescription = slide.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(slide.label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AppColors.Green, letterSpacing = 1.sp)
        Spacer(Modifier.height(8.dp))
        Text(slide.title, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = AppColors.Ink, lineHeight = 34.sp)
        Spacer(Modifier.height(8.dp))
        Text(slide.body, color = AppColors.Muted, fontSize = 14.sp, lineHeight = 20.sp)
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
