package com.ibra.keytrackerapp.key_requests

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.Pink
import com.ibra.keytrackerapp.common.ui.theme.PinkOutlineColor

// Экран заявок пользователя
@Composable
fun RequestsScreen()
{
    Column {
        Greeting(name = "Олег Алексеевич")
        CurrentWeek()
    }
}

// Приветствие
@Composable
fun Greeting(name: String)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(24.dp, 56.dp, 16.dp, 0.dp),
            text = stringResource(id = R.string.greeting) + name,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
        )

        Button(
            modifier = Modifier
                .padding(0.dp, 30.dp, 0.dp, 0.dp)
                .align(Alignment.TopEnd),
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )
        ) {
            Column{
                Image(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = RoundedCornerShape(15.dp))
                        .border(
                            width = 1.dp,
                            color = PinkOutlineColor
                        ),
                    painter = painterResource(id = R.drawable.exit),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 4.dp, 0.dp, 0.dp),
                    text = stringResource(id = R.string.exit),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Pink
                    )
                )
            }
        }
    }
}
