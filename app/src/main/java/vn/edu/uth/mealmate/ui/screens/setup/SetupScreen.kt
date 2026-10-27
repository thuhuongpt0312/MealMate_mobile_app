package vn.edu.uth.mealmate.ui.screens.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.components.HeaderBar
import vn.edu.uth.mealmate.ui.components.PrimaryButton
import vn.edu.uth.mealmate.ui.theme.AppColors

@Composable
fun SetupScreen(onFinish: () -> Unit = {}) {
    var step by remember { mutableIntStateOf(1) }
    var servings by remember { mutableIntStateOf(3) }
    var planForFamily by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Cream)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        HeaderBar(
            title = "Vài điều về bạn",
            subtitle = "Bước $step/3",
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Bạn lên kế hoạch cho ai?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.Ink,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Bạn có thể thay đổi bất cứ lúc nào.",
            fontSize = 14.sp,
            color = AppColors.Muted,
        )

        Spacer(Modifier.height(28.dp))

        // Stepper container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(AppColors.GreenSoft.copy(alpha = 0.5f))
                .padding(vertical = 24.dp, horizontal = 20.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(
                    onClick = { if (servings > 1) servings-- },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(AppColors.Surface),
                ) {
                    Icon(Icons.Filled.Remove, contentDescription = "Giảm", tint = AppColors.Ink)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$servings",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AppColors.Ink,
                    )
                    Text(
                        text = "người",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppColors.Muted,
                    )
                }

                IconButton(
                    onClick = { if (servings < 10) servings++ },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(AppColors.Surface),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Tăng", tint = AppColors.Ink)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // 2 Lựa chọn
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Option 1: Chỉ mình tôi
            val selectedSolo = !planForFamily
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.Surface)
                    .border(
                        width = if (selectedSolo) 2.dp else 1.dp,
                        color = if (selectedSolo) AppColors.Green else AppColors.Line,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clickable {
                        planForFamily = false
                        servings = 1
                    }
                    .padding(16.dp),
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = if (selectedSolo) AppColors.Green else AppColors.Muted,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Chỉ mình tôi",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = AppColors.Ink,
                    )
                }
            }

            // Option 2: Gia đình tôi
            val selectedFamily = planForFamily
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (selectedFamily) AppColors.GreenSoft.copy(alpha = 0.4f) else AppColors.Surface)
                    .border(
                        width = if (selectedFamily) 2.dp else 1.dp,
                        color = if (selectedFamily) AppColors.Green else AppColors.Line,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .clickable {
                        planForFamily = true
                        if (servings == 1) servings = 3
                    }
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Icon(
                            imageVector = Icons.Filled.People,
                            contentDescription = null,
                            tint = if (selectedFamily) AppColors.Green else AppColors.Muted,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Gia đình\ntôi",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = AppColors.Ink,
                        )
                    }
                    if (selectedFamily) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = AppColors.Green,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        PrimaryButton(
            label = "Tiếp tục",
            onClick = {
                if (step < 3) step++ else onFinish()
            },
        )
    }
}
