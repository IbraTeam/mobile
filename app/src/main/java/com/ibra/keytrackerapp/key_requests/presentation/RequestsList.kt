package com.ibra.keytrackerapp.key_requests.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Список заявок пользователя
@Composable
fun RequestsList() {
    RequestsLabel()


}

// Надпись "Ваши заявки"
@Composable
fun RequestsLabel()
{
    Text(
        modifier = Modifier
            .padding(24.dp, 10.dp, 0.dp, 0.dp),
        text = "Ваши заявки",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}