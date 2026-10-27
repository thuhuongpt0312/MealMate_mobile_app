package com.example.myapplication.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.repository.AdminRepository

@Composable
fun MealsSection() {
    val meals = AdminRepository.sampleMeals

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Quản Lý Món Ăn (${meals.size})",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1917)
                    )
                    Text(
                        text = "Công thức & định lượng nguyên liệu chuẩn",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Button(
                    onClick = { /* Thêm món mới */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Thêm món", fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(meals) { meal ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Ảnh món ăn tròn bo góc
                    AsyncImage(
                        model = meal.imageUrl,
                        contentDescription = meal.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    // Thông tin món ăn
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = meal.category,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = meal.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1917)
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color.Gray)
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(text = "${meal.prepTime}p", fontSize = 11.sp, color = Color.Gray)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, modifier = Modifier.size(12.dp), tint = Color(0xFFEF4444))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(text = "${meal.calories} kcal", fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }

                    // Giá tiền
                    Text(
                        text = "${meal.price.toInt()} ₫",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}