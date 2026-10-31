package vn.edu.uth.mealmate.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import vn.edu.uth.mealmate.data.session.SessionManager
import vn.edu.uth.mealmate.ui.screens.auth.RegisterScreen
import vn.edu.uth.mealmate.ui.screens.favorites.FavoritesScreen
import vn.edu.uth.mealmate.ui.screens.history.HistoryScreen
import vn.edu.uth.mealmate.ui.screens.home.HomeScreen
import vn.edu.uth.mealmate.ui.screens.mealplan.MealDetailScreen
import vn.edu.uth.mealmate.ui.screens.mealplan.MealPlanScreen
import vn.edu.uth.mealmate.ui.screens.notifications.NotificationSettingsScreen
import vn.edu.uth.mealmate.ui.screens.notifications.NotificationsScreen
import vn.edu.uth.mealmate.ui.screens.nutrition.NutritionScreen
import vn.edu.uth.mealmate.ui.screens.onboarding.OnboardingScreen
import vn.edu.uth.mealmate.ui.screens.pantry.PantryScreen
import vn.edu.uth.mealmate.ui.screens.plan.CreatePlanScreen
import vn.edu.uth.mealmate.ui.screens.plan.GeneratingScreen
import vn.edu.uth.mealmate.ui.screens.profile.ImageCreditsScreen
import vn.edu.uth.mealmate.ui.screens.profile.ProfileScreen
import vn.edu.uth.mealmate.ui.screens.setup.SetupScreen
import vn.edu.uth.mealmate.ui.screens.shopping.ShoppingScreen
import vn.edu.uth.mealmate.ui.screens.stats.CostStatsScreen

private data class Tab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab(Routes.HOME, "Trang chủ", Icons.Outlined.Home),
    Tab(Routes.MEAL_PLAN, "Thực đơn", Icons.Outlined.CalendarMonth),
    Tab(Routes.SHOPPING, "Mua sắm", Icons.Outlined.ShoppingBag),
    Tab(Routes.HISTORY, "Lịch sử", Icons.Outlined.History),
    Tab(Routes.PROFILE, "Cá nhân", Icons.Outlined.Person),
)

private fun NavHostController.goToTab(route: String) {
    navigate(route) {
        popUpTo(Routes.HOME) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun MealMateApp() {
    val nav = rememberNavController()
    val current = nav.currentBackStackEntryAsState().value?.destination?.route
    val showBottomBar = tabs.any { it.route == current }

    // Đã đăng nhập từ lần trước thì vào thẳng Trang chủ
    val startDestination = remember { if (SessionManager.isLoggedIn) Routes.HOME else Routes.ONBOARDING }

    // Đăng xuất hoặc token hết hạn (401) → quay về màn đăng nhập, xóa toàn bộ back stack
    val token by SessionManager.token.collectAsState()
    var wasLoggedIn by remember { mutableStateOf(token != null) }
    LaunchedEffect(token) {
        if (token == null && wasLoggedIn) {
            nav.navigate(Routes.REGISTER) { popUpTo(nav.graph.id) { inclusive = true } }
        }
        wasLoggedIn = token != null
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = current == tab.route,
                            onClick = { nav.goToTab(tab.route) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                        )
                    }
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = startDestination,
            modifier = Modifier.padding(padding),
        ) {
            // --- Bắt đầu ---
            composable(Routes.ONBOARDING) {
                OnboardingScreen(onFinish = {
                    nav.navigate(Routes.REGISTER) { popUpTo(Routes.ONBOARDING) { inclusive = true } }
                })
            }
            composable(Routes.REGISTER) {
                RegisterScreen(onRegister = { nav.navigate(Routes.SETUP) })
            }
            composable(Routes.SETUP) {
                SetupScreen(onFinish = {
                    nav.navigate(Routes.HOME) { popUpTo(Routes.REGISTER) { inclusive = true } }
                })
            }

            // --- Trải nghiệm chính ---
            composable(Routes.HOME) {
                HomeScreen(
                    onCreatePlan = { nav.navigate(Routes.CREATE_PLAN) },
                    onNotifications = { nav.navigate(Routes.NOTIFICATIONS) },
                    onPantry = { nav.navigate(Routes.PANTRY) },
                    onNutrition = { nav.navigate(Routes.NUTRITION) },
                    onFavorites = { nav.navigate(Routes.FAVORITES) },
                    onCostStats = { nav.navigate(Routes.COST_STATS) },
                )
            }
            composable(Routes.CREATE_PLAN) {
                CreatePlanScreen(
                    onBack = { nav.popBackStack() },
                    onCreate = { days, servings -> nav.navigate(Routes.generating(days, servings)) },
                )
            }
            composable(
                route = Routes.GENERATING,
                arguments = listOf(
                    navArgument("days") { type = NavType.IntType; defaultValue = 7 },
                    navArgument("servings") { type = NavType.IntType; defaultValue = 0 },
                ),
            ) { entry ->
                GeneratingScreen(
                    days = entry.arguments?.getInt("days") ?: 7,
                    servings = entry.arguments?.getInt("servings")?.takeIf { it > 0 },
                    onDone = { nav.navigate(Routes.MEAL_PLAN) { popUpTo(Routes.HOME) } },
                    onBack = { nav.popBackStack() },
                )
            }

            // --- Bữa ăn ---
            composable(Routes.MEAL_PLAN) {
                MealPlanScreen(
                    onOpenMeal = { nav.navigate(Routes.mealDetail("com-ga-gung")) },
                    onShopping = { nav.goToTab(Routes.SHOPPING) },
                )
            }
            composable(
                route = Routes.MEAL_DETAIL,
                arguments = listOf(navArgument("mealId") { type = NavType.StringType }),
            ) { entry ->
                MealDetailScreen(
                    mealId = entry.arguments?.getString("mealId").orEmpty(),
                    onBack = { nav.popBackStack() },
                )
            }

            // --- Tiện ích, thư viện ---
            composable(Routes.SHOPPING) { ShoppingScreen(onPantry = { nav.navigate(Routes.PANTRY) }) }
            composable(Routes.PANTRY) { PantryScreen(onBack = { nav.popBackStack() }) }
            composable(Routes.NUTRITION) { NutritionScreen(onBack = { nav.popBackStack() }) }
            composable(Routes.FAVORITES) { FavoritesScreen(onBack = { nav.popBackStack() }) }
            composable(Routes.HISTORY) {
                HistoryScreen(onOpenPlan = { nav.goToTab(Routes.MEAL_PLAN) })
            }

            // --- Thông báo ---
            composable(Routes.NOTIFICATIONS) {
                NotificationsScreen(
                    onBack = { nav.popBackStack() },
                    onSettings = { nav.navigate(Routes.NOTIFICATION_SETTINGS) },
                )
            }
            composable(Routes.NOTIFICATION_SETTINGS) {
                NotificationSettingsScreen(onBack = { nav.popBackStack() })
            }

            // --- Thống kê, tài khoản ---
            composable(Routes.COST_STATS) { CostStatsScreen(onBack = { nav.popBackStack() }) }
            composable(Routes.PROFILE) {
                ProfileScreen(
                    onNotificationSettings = { nav.navigate(Routes.NOTIFICATION_SETTINGS) },
                    onCostStats = { nav.navigate(Routes.COST_STATS) },
                    onImageCredits = { nav.navigate(Routes.IMAGE_CREDITS) },
                )
            }
            composable(Routes.IMAGE_CREDITS) { ImageCreditsScreen(onBack = { nav.popBackStack() }) }
        }
    }
}
