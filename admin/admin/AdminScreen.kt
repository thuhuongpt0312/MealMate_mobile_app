package com.example.myapplication.ui.admin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.admin.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen() {
    var selectedTab by remember { mutableStateOf(AdminTab.DASHBOARD) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MealMate Admin • " + selectedTab.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B4332),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
                AdminTab.values().forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                        label = { Text(text = tab.title, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF1B4332),
                            selectedTextColor = Color(0xFF1B4332),
                            indicatorColor = Color(0xFFE8F5E9)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                AdminTab.DASHBOARD -> DashboardSection()
                AdminTab.MEALS -> MealsSection()
                AdminTab.INGREDIENTS -> IngredientsSection()
                AdminTab.USERS -> UsersSection()
                AdminTab.REPORTS -> ReportsSection()
                AdminTab.SETTINGS -> SettingsSection()
            }
        }
    }
}