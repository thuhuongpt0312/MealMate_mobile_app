package vn.edu.uth.mealmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vn.edu.uth.mealmate.ui.theme.AppColors

/**
 * Màn hình tạm. Các nút "actions" chỉ để thử luồng điều hướng.
 * Khi dựng giao diện thật theo Figma thì thay PlaceholderScreen bằng UI thật.
 */
@Composable
fun PlaceholderScreen(
    figmaId: String,
    title: String,
    actions: List<Pair<String, () -> Unit>> = emptyList(),
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Figma: $figmaId", color = AppColors.Muted)
        Text("TODO: dựng giao diện theo Figma", color = AppColors.Muted)
        Spacer(Modifier.height(16.dp))
        actions.forEach { (label, onClick) ->
            OutlinedButton(onClick = onClick) { Text(label) }
        }
    }
}
