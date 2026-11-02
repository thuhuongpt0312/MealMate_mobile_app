package vn.edu.uth.mealmate.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.LoadingView
import vn.edu.uth.mealmate.ui.components.MessageView
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.formatVnd

@Composable
fun FavoritesScreen(
    onBack: () -> Unit = {},
    onOpenMeal: (mealId: String) -> Unit = {},
    vm: FavoritesViewModel = viewModel(),
) {
    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 20.dp),
    ) {
        HeaderBar(
            title = "Yêu thích",
            subtitle = "Những món quen thuộc ở cùng một nơi",
            onBack = onBack,
        )

        Spacer(Modifier.height(10.dp))

        val meals = when (val state = uiState) {
            FavoritesUiState.Loading -> return@Column LoadingView()
            is FavoritesUiState.Error -> return@Column MessageView(
                title = "Không tải được món ăn",
                message = state.message,
                actionLabel = "Thử lại",
                onAction = vm::load,
            )
            is FavoritesUiState.Success -> state.meals
        }
        if (meals.isEmpty()) {
            MessageView(
                title = "Chưa có món yêu thích",
                message = "Bấm biểu tượng trái tim ở trang chi tiết món để lưu lại những món bạn thích.",
            )
            return@Column
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            items(meals, key = { it.id }) { meal ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(AppColors.Surface)
                        .border(1.dp, AppColors.Line, RoundedCornerShape(18.dp))
                        .clickable { onOpenMeal(meal.id) }
                        .padding(12.dp),
                ) {
                    Column {
                        // Food Image placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(AppColors.GreenSoft),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Restaurant,
                                contentDescription = null,
                                tint = AppColors.Green,
                                modifier = Modifier.size(36.dp),
                            )
                        }

                        Spacer(Modifier.height(10.dp))

                        Text(
                            text = meal.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Ink,
                            maxLines = 1,
                        )

                        Spacer(Modifier.height(2.dp))

                        Text(
                            text = "${meal.minutes} phút · ${formatVnd(meal.pricePerServing)}",
                            fontSize = 12.sp,
                            color = AppColors.Muted,
                        )
                    }
                }
            }
        }
    }
}
