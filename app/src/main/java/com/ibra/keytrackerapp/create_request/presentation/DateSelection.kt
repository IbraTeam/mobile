@file:OptIn(ExperimentalMaterial3Api::class)

package com.ibra.keytrackerapp.create_request.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.BlueColor
import com.ibra.keytrackerapp.common.ui.theme.DarkerGrayColor
import com.ibra.keytrackerapp.common.ui.theme.VeryLightGray
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.format.DateTimeFormatter

// Выбор даты, на которую создаётся заявка
@Composable
fun DateSelection() {
    Text(
        modifier = Modifier
            .padding(24.dp, 24.dp, 24.dp, 0.dp),
        text = stringResource(id = R.string.select_a_date),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    )

    DatesRow()
}

// Список дней
@Composable
fun DatesRow(
    viewModel: CreateRequestViewModel = hiltViewModel()
)
{
    val vmValues by viewModel.uiState.collectAsState()
    val calendarState = rememberSheetState()

    // Календарь
    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date(
            selectedDate = vmValues.selectedDate,
            onSelectDate = {
                    date -> viewModel.selectDate(date)
            }
        ),
    )

    Row(
        modifier = Modifier
            .padding(24.dp, 16.dp, 24.dp, 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (date in vmValues.datesRow) {
            val isSelected = vmValues.selectedDate == date && !calendarState.visible

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(if (isSelected) BlueColor else VeryLightGray)
                    .clickable {
                        viewModel.selectDate(date)
                    },
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val dateTimeFormatter = DateTimeFormatter.ofPattern("dd")
                val day = date.format(dateTimeFormatter)

                DateElement(
                    day = day,
                    dayOfWeek = viewModel.getDayOfWeekName(date.dayOfWeek.value),
                    isSelected = isSelected
                )
            }

           Spacer(modifier = Modifier.weight(0.2f))
        }

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(if (calendarState.visible) BlueColor else VeryLightGray)
                .clickable {
                    calendarState.show()
                },
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            AnotherDate(calendarState.visible)
        }
    }
}

// Дата
@Composable
fun DateElement(
    day: String,
    dayOfWeek: String,
    isSelected: Boolean
) {
    Column {
        Text(
            modifier = Modifier
                .padding(0.dp, 40.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            text = day,
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) Color.White else DarkerGrayColor
            )
        )

        Text(
            modifier = Modifier
                .padding(0.dp, 2.dp, 0.dp, 40.dp)
                .align(Alignment.CenterHorizontally),
            text = dayOfWeek,
            style = TextStyle(
                fontSize = 16.sp,
                color = if (isSelected) Color.White else DarkerGrayColor,
                letterSpacing = 0.3.sp
            )
        )
    }
}

// Другой день
@Composable
fun AnotherDate(isSelected: Boolean) {
    Column {
        Text(
            modifier = Modifier
                .padding(0.dp, 40.dp, 0.dp, 0.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.another),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = if(isSelected) Color.White else DarkerGrayColor
            )
        )

        Text(
            modifier = Modifier
                .padding(0.dp, 2.dp, 0.dp, 40.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.day),
            style = TextStyle(
                fontSize = 16.sp,
                color = if (isSelected) Color.White else DarkerGrayColor,
                letterSpacing = 0.3.sp
            )
        )
    }
}