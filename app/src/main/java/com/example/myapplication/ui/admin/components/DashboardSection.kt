package com.example.myapplication.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.data.repository.AdminRepository

@Composable
fun DashboardSection() {
    val meals = AdminRepository.sampleMeals

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. HERO BANNER MÀU XANH LÁ RỪNG
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF1B4332), Color(0xFF2D6A4F))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "BẢNG ĐIỀU KHIỂN HỆ THỐNG",
                                color = Color(0xFF86EFAC),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "MealMate Admin Portal",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "Tối ưu hóa giỏ hàng & tận dụng nguyên liệu tủ lạnh",
                            color = Color(0xFFD1FAE5),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // 2. LƯỚI 4 THẺ THỐNG KÊ KPI CHÍNH
        item {
            Text(
                text = "Chỉ Số Hoạt Động Tuần Này",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1917)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Thẻ 1: Tiết kiệm tiền
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Savings,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Đã tiết kiệm", fontSize = 11.sp, color = Color(0xFF2E7D32))
                            Text(text = "1.495.000 ₫", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                            Text(text = "Trung bình 34.2%", fontSize = 10.sp, color = Color(0xFF388E3C))
                        }
                    }

                    // Thẻ 2: Thực phẩm tránh lãng phí
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = null,
                                tint = Color(0xFF00796B),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Tránh lãng phí", fontSize = 11.sp, color = Color(0xFF00796B))
                            Text(text = "48.5 kg", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF004D40))
                            Text(text = "Nấu trước khi hỏng", fontSize = 10.sp, color = Color(0xFF00897B))
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Thẻ 3: Món ăn phục vụ
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.RestaurantMenu,
                                contentDescription = null,
                                tint = Color(0xFFF57F17),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Món ăn chuẩn", fontSize = 11.sp, color = Color(0xFFF57F17))
                            Text(text = "${meals.size} món", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                            Text(text = "Đã có định lượng", fontSize = 10.sp, color = Color(0xFFFB8C00))
                        }
                    }

                    // Thẻ 4: Cảnh báo tủ lạnh
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFD32F2F),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Cảnh báo tủ lạnh", fontSize = 11.sp, color = Color(0xFFD32F2F))
                            Text(text = "2 người dùng", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                            Text(text = "Hết hạn trong 48h", fontSize = 10.sp, color = Color(0xFFE53935))
                        }
                    }
                }
            }
        }

        // 3. DANH SÁCH MÓN ĂN TIÊU BIỂU CÓ HÌNH ẢNH ĐẸP
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Món Ăn Nổi Bật Trong Tuần",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1917)
                )
                Text(
                    text = "${meals.size} món",
                    fontSize = 12.sp,
                    color = Color(0xFF2D6A4F),
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(meals) { meal ->
                    Card(
                        modifier = Modifier
                            .width(220.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column {
                            // Hình ảnh món ăn tải từ Coil
                            AsyncImage(
                                model = meal.imageUrl,
                                contentDescription = meal.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = meal.category.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2D6A4F)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = meal.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = Color(0xFF1C1917)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${meal.prepTime} phút • ${meal.calories} kcal",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "${meal.price.toInt()} ₫",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}