package vn.edu.uth.mealmate.ui.screens.profile

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
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

private data class ImageCreditRow(val layer: String, val screen: String, val license: String)

private val credits = listOf(
    ImageCreditRow("img_s01_mamcom", "S01", "Miễn phí"),
    ImageCreditRow("img_s04_comgagung", "S04", "Miễn phí"),
    ImageCreditRow("img_s07_phoga", "S07", "Miễn phí"),
    ImageCreditRow("img_s07_bunthitnuong", "S07", "Miễn phí"),
    ImageCreditRow("img_s12_comgiadinh", "S12", "Miễn phí"),
    ImageCreditRow("img_s15_rauxanh", "S15", "Miễn phí"),
    ImageCreditRow("img_s16_avatar", "S16", "Tự tạo"),
)

@Composable
fun ImageCreditsScreen(onBack: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderBar(
            title = "Nguồn hình ảnh",
            subtitle = "Kiểm kê bản quyền hình ảnh MealMate",
            onBack = onBack,
        )

        Spacer(Modifier.height(14.dp))

        // Table
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(AppColors.Surface)
                .border(1.dp, AppColors.Line, RoundedCornerShape(18.dp))
                .padding(16.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                ) {
                    Text("Tên layer", modifier = Modifier.weight(1.8f), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AppColors.Muted)
                    Text("Màn hình", modifier = Modifier.weight(1f), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AppColors.Muted)
                    Text("Loại", modifier = Modifier.weight(1f), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AppColors.Muted)
                }

                HorizontalDivider(color = AppColors.Line)

                credits.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                    ) {
                        Text(row.layer, modifier = Modifier.weight(1.8f), fontSize = 12.sp, color = AppColors.Ink)
                        Text(row.screen, modifier = Modifier.weight(1f), fontSize = 12.sp, color = AppColors.Muted)
                        Text(row.license, modifier = Modifier.weight(1f), fontSize = 12.sp, color = AppColors.GreenDark, fontWeight = FontWeight.SemiBold)
                    }
                    HorizontalDivider(color = AppColors.Line.copy(alpha = 0.5f))
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // Alert vàng nhạt Đã rà soát
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFEF7E6))
                .border(1.dp, Color(0xFFF3D99F), RoundedCornerShape(16.dp))
                .padding(16.dp),
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color(0xFFB78103),
                    modifier = Modifier.size(20.dp),
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Đã rà soát",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B5E00),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Không sử dụng logo thương hiệu, ảnh người thật hoặc nguồn không rõ bản quyền.",
                        fontSize = 12.sp,
                        color = AppColors.Ink,
                        lineHeight = 16.sp,
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}
