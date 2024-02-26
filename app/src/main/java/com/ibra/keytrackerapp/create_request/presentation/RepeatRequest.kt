package com.ibra.keytrackerapp.create_request.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.RepeatBgColor
import com.ibra.keytrackerapp.common.ui.theme.RepeatPlaceholderColor

// Повтор заявки на несколько недель
@Composable
fun RepeatRequest(
    viewModel: CreateRequestViewModel = hiltViewModel()
) {
    val vmValues by viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .padding(24.dp, 16.dp, 24.dp, 0.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(0.dp, 0.dp, 10.dp, 0.dp),
            text = "Создать заявку на",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        )

        var weeksVal by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        OutlinedTextField(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(70.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(RepeatBgColor),
            value = weeksVal,
            shape = RoundedCornerShape(15.dp),

            onValueChange = {
                if (it.length <= 3)
                    weeksVal = it

                viewModel.onWeeksChanged(weeksVal)
            },

            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            maxLines = 1,

            placeholder = {
                Text(
                    text = "10",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        color = RepeatPlaceholderColor
                    )
                )
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),

            isError = vmValues.isError,

            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.field),
                unfocusedContainerColor = colorResource(R.color.field),
                disabledContainerColor = colorResource(R.color.field),
                errorContainerColor = colorResource(R.color.field),
                focusedBorderColor = colorResource(R.color.field),
                unfocusedBorderColor = colorResource(R.color.field),
                errorBorderColor = colorResource(R.color.light_accent_red),
                focusedTextColor = colorResource(R.color.field_text),
                unfocusedTextColor = colorResource(R.color.field_text),
                errorTextColor = colorResource(R.color.light_accent_red),
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(10.dp, 0.dp, 0.dp, 0.dp),
            text = "недель",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}