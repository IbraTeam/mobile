package com.ibra.keytrackerapp.common.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ibra.keytrackerapp.R


@Composable
fun MyPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: Int?,
    modifier: Modifier = Modifier
) {
    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
        ),
        label = {
            if (isError) {
                errorMessage?.let {
                    Text(
                        text = stringResource(id = it),
                        color = colorResource(R.color.light_accent_red)
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.password),
                    color = colorResource(R.color.field_text)
                )
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                painterResource(R.drawable.show_password)
            } else {
                painterResource(R.drawable.hide_password)
            }
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(
                    painter = iconImage,
                    contentDescription = null,
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp)
                )
            }
        },

        isError = isError,
        keyboardActions = KeyboardActions(),
        singleLine = true,
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

            ),
    )
}

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: Int?,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
        ),
        label = {
            if (isError) {
                errorMessage?.let {
                    Text(
                        text = stringResource(id = it),
                        color = colorResource(R.color.light_accent_red),
                    )
                }
            } else {
                Text(
                    text = label, color = colorResource(R.color.field_text)
                )
            }
        },
        isError = isError,
        keyboardActions = KeyboardActions(),
        singleLine = true,
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
        ),
    )
}