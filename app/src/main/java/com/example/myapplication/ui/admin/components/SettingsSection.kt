package com.example.myapplication.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsSection() {
    var warningDays by remember { mutableStateOf(2f) }
    var autoDeduplicate by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Cài Đặt Hệ Thống & Thuật Toán", style = MaterialTheme.typography.titleLarge)

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Ngưỡng cảnh báo thực phẩm sắp hết hạn: ${warningDays.toInt()} ngày", style = MaterialTheme.typography.titleSmall)
                Slider(
                    value = warningDays,
                    onValueChange = { warningDays = it },
                    valueRange = 1f..7f,
                    steps = 5
                )
                Text(
                    text = "Gửi thông báo đẩy đến người dùng khi nguyên liệu trong tủ còn dưới ${warningDays.toInt()} ngày.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Tự động trừ nguyên liệu trong tủ", style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = "Tự động đối chiếu đơn vị và khấu trừ khỏi giỏ hàng đi chợ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Switch(checked = autoDeduplicate, onCheckedChange = { autoDeduplicate = it })
            }
        }
    }
}