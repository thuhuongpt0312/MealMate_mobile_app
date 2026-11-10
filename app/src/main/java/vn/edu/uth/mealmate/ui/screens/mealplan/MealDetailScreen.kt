package vn.edu.uth.mealmate.ui.screens.mealplan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.data.local.FavoritesStore
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.MealImages
import vn.edu.uth.mealmate.util.formatQuantity
import vn.edu.uth.mealmate.util.formatVnd

@Composable
fun MealDetailScreen(
    mealId: String,
    servings: Int = 1,
    onBack: () -> Unit = {},
    vm: MealDetailViewModel = viewModel(),
) {
    LaunchedEffect(mealId) { vm.load(mealId) }
    val uiState by vm.uiState.collectAsState()
    val favorites by FavoritesStore.ids.collectAsState()

    when (val state = uiState) {
        is MealDetailUiState.Success -> MealDetailContent(
            meal = state.meal,
            servings = servings,
            isFavorite = state.meal.id in favorites,
            onToggleFavorite = { FavoritesStore.toggle(state.meal.id) },
            onBack = onBack,
        )
        MealDetailUiState.Loading -> Column(Modifier.fillMaxSize().background(AppColors.Cream).padding(horizontal = 16.dp)) {
            HeaderBar(title = "Chi tiết món", onBack = onBack)
            LoadingView()
        }
        is MealDetailUiState.Error -> Column(Modifier.fillMaxSize().background(AppColors.Cream).padding(horizontal = 16.dp)) {
            HeaderBar(title = "Chi tiết món", onBack = onBack)
            MessageView(title = "Không tải được món ăn", message = state.message, actionLabel = "Thử lại", onAction = vm::retry)
        }
    }
}

@Composable
private fun MealDetailContent(
    meal: Meal,
    servings: Int,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream),
    ) {
        // Hero Image Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(AppColors.GreenDark),
        ) {
            Image(
                painter = painterResource(MealImages.forMeal(meal.id)),
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            // Dim overlay at top for button visibility
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Black.copy(alpha = 0.2f)),
            )

            HeaderBar(
                title = "",
                onBack = onBack,
                modifier = Modifier.padding(horizontal = 16.dp),
                rightContent = {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppColors.Surface.copy(alpha = 0.9f)),
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Bỏ yêu thích" else "Thêm vào yêu thích",
                            tint = if (isFavorite) AppColors.Orange else AppColors.Ink,
                        )
                    }
                },
            )
        }

        // Details Container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(AppColors.Surface)
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            // Tag
            Text(
                text = meal.slot.label.uppercase(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Green,
                letterSpacing = 0.5.sp,
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = meal.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "${meal.ingredients.size} nguyên liệu · khoảng ${formatVnd(meal.pricePerServing * servings)} cho $servings người",
                fontSize = 13.sp,
                color = AppColors.Muted,
                lineHeight = 18.sp,
            )

            Spacer(Modifier.height(20.dp))

            // 3 Stat Boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                StatPill(
                    icon = Icons.Outlined.AccessTime,
                    value = "${meal.minutes} phút",
                    label = "Thời gian",
                    modifier = Modifier.weight(1f),
                )
                StatPill(
                    icon = Icons.Outlined.Payments,
                    value = formatVnd(meal.pricePerServing),
                    label = "Mỗi phần",
                    modifier = Modifier.weight(1f),
                )
                StatPill(
                    icon = Icons.Outlined.LocalFireDepartment,
                    value = "${meal.calories}",
                    label = "Calo",
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(Modifier.height(24.dp))

            // Nguyên liệu
            Text(
                text = "Nguyên liệu · $servings phần",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )

            Spacer(Modifier.height(12.dp))

            meal.ingredients.forEach { ing ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = ing.name,
                        fontSize = 14.sp,
                        color = AppColors.Ink,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = formatQuantity(ing.amount * servings, ing.unit),
                        fontSize = 14.sp,
                        color = AppColors.Muted,
                    )
                }
                HorizontalDivider(color = AppColors.Line.copy(alpha = 0.6f))
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatPill(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(AppColors.GreenSoft.copy(alpha = 0.4f))
            .border(1.dp, AppColors.Line, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AppColors.Green,
                modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Ink,
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = AppColors.Muted,
            )
        }
    }
}
