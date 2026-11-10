package com.example.myapplication.ui.admin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class AdminTab(
    val title: String,
    val icon: ImageVector
) {
    DASHBOARD(title = "Tổng quan", icon = Icons.Default.Dashboard),
    MEALS(title = "Thực đơn", icon = Icons.Default.RestaurantMenu),
    INGREDIENTS(title = "Nguyên liệu", icon = Icons.Default.Inventory2),
    USERS(title = "Người dùng", icon = Icons.Default.People),
    REPORTS(title = "Thống kê", icon = Icons.Default.BarChart),
    SETTINGS(title = "Cài đặt", icon = Icons.Default.Settings)
}