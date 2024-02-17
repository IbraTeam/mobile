package com.ibra.keytrackerapp.key_requests

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.BlueColor
import com.ibra.keytrackerapp.common.ui.theme.GrayColor
import com.ibra.keytrackerapp.common.ui.theme.LightBlueColor
import com.ibra.keytrackerapp.common.ui.theme.Pink
import com.ibra.keytrackerapp.common.ui.theme.PinkOutlineColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

// Текущая неделя
@Composable
fun CurrentWeek(
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val vmValues = viewModel.uiState.collectAsState().value

    Row(
       modifier = Modifier
           .padding(24.dp, 16.dp, 16.dp, 0.dp)
           .fillMaxWidth()
    ) {
        for (date in vmValues.selectedWeek){
            DayOfWeek(date)

            if (date.plusDays(1) != vmValues.selectedDate && date != vmValues.selectedDate)
                Spacer(modifier = Modifier.weight(1f))
            else
                Spacer(modifier = Modifier.weight(0.2f))
        }

        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(shape = RoundedCornerShape(10.dp))
                .clickable {
                           //TODO
                },
            imageVector = ImageVector.vectorResource(R.drawable.options),
            contentDescription = null
        )

    }
}

// День недели
@Composable
fun DayOfWeek(
    date: LocalDate,
    viewModel: RequestsViewModel = hiltViewModel()
) {
    val vmValues = viewModel.uiState.collectAsState().value
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd")
    val day = date.format(dateTimeFormatter)
    val isSelected = vmValues.selectedDate == date

    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16.dp))
            .background(
                color = if (date == vmValues.selectedDate) LightBlueColor else Color.Transparent
            )
            .clickable(
                enabled = !isSelected,
                onClick = {
                    //TODO
                }
            )
    )
    {
        val horizontalPadding = if (isSelected) 18.dp else 0.dp

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontalPadding, 16.dp, horizontalPadding, 0.dp),
            text = day.toString(),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (vmValues.selectedDate == date) BlueColor else Color.Black
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = viewModel.GetDayOfWeekName(date.dayOfWeek.value),
            style = TextStyle(
                fontSize = 16.sp,
                color = if (vmValues.selectedDate == date) BlueColor else GrayColor
            )
        )

        if (isSelected)
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp),
                painter = painterResource(id = R.drawable.dot),
                contentDescription = null
            )
    }
}