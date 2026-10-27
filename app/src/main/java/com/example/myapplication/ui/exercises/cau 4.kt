package com.example.myapplication.ui.exercises

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

private val Blue = Color(0xFF2196F3)
private val LightBlue = Color(0xFFBBDEFB)

@Composable
fun Cau4App() {
    var screen by remember { mutableStateOf("Welcome") }

    BackHandler(enabled = screen != "Welcome") {
        screen = "List"
    }

    when (screen) {
        "Welcome" -> WelcomeScreen {
            screen = "List"
        }

        "List" -> ComponentsList(
            onSelect = { screen = it }
        )

        "Text" -> DetailScreen("Text", onBack = {
            screen = "List"
        }) {
            TextDetail()
        }

        "Images" -> DetailScreen("Images", onBack = {
            screen = "List"
        }) {
            ImagesDetail()
        }

        "TextField" -> DetailScreen("TextField", onBack = {
            screen = "List"
        }) {
            TextFieldDetail()
        }

        "PasswordField" -> DetailScreen(
            "PasswordField",
            onBack = { screen = "List" }
        ) {
            PasswordDetail()
        }

        "Column" -> DetailScreen("Column", onBack = {
            screen = "List"
        }) {
            ColumnDetail()
        }

        "Row" -> DetailScreen("Row Layout", onBack = {
            screen = "List"
        }) {
            RowDetail()
        }
    }
}

@Composable
private fun WelcomeScreen(onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "⬡",
                fontSize = 100.sp,
                color = Blue
            )

            Text(
                text = "Jetpack Compose",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jetpack Compose is a modern UI toolkit " +
                        "for building native Android applications.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Button(
            onClick = onStart,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text("I'm ready")
        }
    }
}

@Composable
private fun ComponentsList(onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "UI Components List",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Blue,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Display", fontWeight = FontWeight.Bold)

        ComponentItem("Text", "Displays text") {
            onSelect("Text")
        }

        ComponentItem("Image", "Displays an image") {
            onSelect("Images")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Input", fontWeight = FontWeight.Bold)

        ComponentItem("TextField", "Input field for text") {
            onSelect("TextField")
        }

        ComponentItem("PasswordField", "Input field for passwords") {
            onSelect("PasswordField")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Layout", fontWeight = FontWeight.Bold)

        ComponentItem("Column", "Arranges elements vertically") {
            onSelect("Column")
        }

        ComponentItem("Row", "Arranges elements horizontally") {
            onSelect("Row")
        }
    }
}

@Composable
private fun ComponentItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(LightBlue, RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(description, fontSize = 13.sp)
    }
}

@Composable
private fun DetailScreen(
    title: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                fontSize = 32.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { onBack() }
            )

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun TextDetail() {
    val sample = buildAnnotatedString {
        append("The ")

        pushStyle(
            SpanStyle(textDecoration = TextDecoration.LineThrough)
        )
        append("quick")
        pop()

        append(" ")

        pushStyle(
            SpanStyle(
                color = Color(0xFFB87920),
                fontSize = 28.sp
            )
        )
        append("Brown")
        pop()

        append("\nfox j u m p s ")

        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append("over")
        pop()

        append("\n")

        pushStyle(
            SpanStyle(textDecoration = TextDecoration.Underline)
        )
        append("the lazy")
        pop()

        append(" dog.")
    }

    Column {
        Text(
            text = sample,
            fontSize = 24.sp,
            lineHeight = 40.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Color.LightGray)
    }
}

@Composable
private fun ImagesDetail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.uth),
            contentDescription = "Tòa nhà UTH",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Trường Đại học Giao thông vận tải TP.HCM",
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.khuong_vien),
            contentDescription = "Khuôn viên trường UTH",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Khuôn viên trường",
            fontSize = 13.sp
        )
    }
}

@Composable
private fun TextFieldDetail() {
    var message by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Thông tin nhập") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (message.isEmpty()) {
                "Tự động cập nhật dữ liệu theo textfield"
            } else {
                "Nội dung: $message"
            },
            color = Color(0xFFB6854A),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun PasswordDetail() {
    var password by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Mật khẩu") },
            placeholder = { Text("Nhập mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Độ dài mật khẩu: ${password.length}")
    }
}

@Composable
private fun ColumnDetail() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        if (index % 2 == 0) LightBlue else Blue,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Item ${index + 1}",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun RowDetail() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        repeat(4) { rowIndex ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFF5F5F5),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { columnIndex ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp)
                            .background(
                                if (columnIndex == 1) {
                                    Color(0xFF5186E8)
                                } else {
                                    Color(0xFFA8C3F0)
                                },
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${rowIndex * 3 + columnIndex + 1}",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
