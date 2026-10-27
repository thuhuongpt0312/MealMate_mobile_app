package com.example.myapplication.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReportsSection() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Báo Cáo Hiệu Quả Thuật Toán", style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "• Tỷ lệ chi phí tiết kiệm trung bình: 34.2%", style = MaterialTheme.typography.bodyLarge)
                Text(text = "• Top nguyên liệu có sẵn nhiều nhất: Gạo thơm, Trứng gà, Ức gà, Dầu ăn", style = MaterialTheme.typography.bodyMedium)
                Text(text = "• Món ăn được lên kế hoạch nhiều nhất: Cơm gà xào gừng (65%)", style = MaterialTheme.typography.bodyMedium)
                Text(text = "• Số cảnh báo chống lãng phí đã gửi: 142 lượt", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}