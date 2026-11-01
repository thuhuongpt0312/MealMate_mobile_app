package vn.edu.uth.mealmate.ui.screens.pantry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.uth.mealmate.ui.theme.AppColors
import vn.edu.uth.mealmate.util.parseAmount

/** Hộp thoại thêm nguyên liệu vào tủ bếp: tên, số lượng, đơn vị. */
@Composable
fun AddPantryItemDialog(
    saving: Boolean,
    error: String?,
    onAdd: (name: String, amount: Double?, unit: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var unit by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { if (!saving) onDismiss() },
        title = { Text("Thêm vào tủ bếp", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên nguyên liệu") },
                    placeholder = { Text("Ví dụ: Hành tím") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        label = { Text("Số lượng") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                    )
                    OutlinedTextField(
                        value = unit,
                        onValueChange = { unit = it },
                        label = { Text("Đơn vị") },
                        placeholder = { Text("g, quả…") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (error != null) {
                    Text(error, color = AppColors.Orange, fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onAdd(name, parseAmount(amount), unit) }, enabled = !saving) {
                Text(if (saving) "Đang lưu…" else "Thêm", color = AppColors.GreenDark, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !saving) { Text("Hủy", color = AppColors.Muted) }
        },
        containerColor = AppColors.Surface,
    )
}
