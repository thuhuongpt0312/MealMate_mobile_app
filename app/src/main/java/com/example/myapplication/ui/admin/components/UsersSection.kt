package com.example.myapplication.ui.admin.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.repository.AdminRepository

@Composable
fun UsersSection() {
    val users = AdminRepository.sampleUsers

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(text = "Giám Sát Tủ Lạnh Người Dùng", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(users) { user ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = user.name, style = MaterialTheme.typography.titleMedium)
                        if (user.hasExpiringItems) {
                            Badge(containerColor = MaterialTheme.colorScheme.error) {
                                Text(text = "Có đồ sắp hỏng", color = MaterialTheme.colorScheme.onError)
                            }
                        }
                    }
                    Text(text = user.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Trong tủ: ${user.pantryItemsCount} nguyên liệu | Đã tiết kiệm: ${user.savedMoney.toInt()} ₫",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}