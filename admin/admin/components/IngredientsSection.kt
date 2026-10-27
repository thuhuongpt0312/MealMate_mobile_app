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
fun IngredientsSection() {
    val ingredients = AdminRepository.sampleIngredients

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(text = "Kho Nguyên Liệu & Giá Thị Trường", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(ingredients) { ing ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = ing.name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Hạn ngăn mát: ${ing.shelfLifeFridgeDays} ngày | Ngăn đông: ${ing.shelfLifeFreezerDays} ngày",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Text(
                        text = "${ing.unitPrice.toInt()} ₫ / ${ing.unit}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}