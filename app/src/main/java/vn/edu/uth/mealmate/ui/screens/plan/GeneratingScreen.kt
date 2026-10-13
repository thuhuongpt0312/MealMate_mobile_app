package vn.edu.uth.mealmate.ui.screens.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.edu.uth.mealmate.ui.theme.AppColors

/**
 * S06 · AI đang tạo. Phụ trách: B.
 * TODO: hiện 4 bước (Kiểm tra khẩu vị, Cân đối ngân sách, Sắp xếp bữa ăn, Tạo danh sách mua sắm)
 *       và gọi ShoppingListBuilder / logic tạo thực đơn thật thay vì delay giả lập.
 */
@Composable
fun GeneratingScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500)
        onDone()
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(color = AppColors.Green)
        Spacer(Modifier.height(24.dp))
        Text("Tuần mới đang dần hoàn thiện", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Figma: S06", color = AppColors.Muted)
    }
}
