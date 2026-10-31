package vn.edu.uth.mealmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.theme.AppColors

/** Trạng thái đang tải dùng chung cho các màn lấy dữ liệu từ API. */
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().padding(vertical = 48.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = AppColors.Green)
    }
}

/** Trạng thái trống hoặc lỗi: tiêu đề, mô tả và một nút hành động (Thử lại, Tạo thực đơn…). */
@Composable
fun MessageView(
    title: String,
    message: String? = null,
    actionLabel: String? = null,
    onAction: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = AppColors.Ink, textAlign = TextAlign.Center)
        if (!message.isNullOrBlank()) {
            Spacer(Modifier.height(6.dp))
            Text(message, fontSize = 13.sp, color = AppColors.Muted, textAlign = TextAlign.Center, lineHeight = 18.sp)
        }
        if (actionLabel != null) {
            Spacer(Modifier.height(20.dp))
            PrimaryButton(label = actionLabel, onClick = onAction, showArrow = false)
        }
    }
}
