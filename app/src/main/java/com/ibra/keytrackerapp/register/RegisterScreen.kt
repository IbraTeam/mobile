package com.ibra.keytrackerapp.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.auth.data.enum.FieldType
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.ui.component.MyPasswordTextField
import com.ibra.keytrackerapp.common.ui.component.MyTextField
import com.ibra.keytrackerapp.common.ui.theme.bold24
import com.ibra.keytrackerapp.common.ui.theme.medium18


@Composable
fun RegisterScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isButtonPressed) {
        if (uiState.isButtonPressed) {
            navController.navigate(Screen.KeyTracker.name)
        }
    }

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.corner_circles),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TitleTextSection()
            FieldsSection(
                uiState = uiState,
                onNameChanged = { viewModel.onFieldChanged(FieldType.Name, it) },
                onSurnameChanged = { viewModel.onFieldChanged(FieldType.Surname, it) },
                onEmailChanged = { viewModel.onFieldChanged(FieldType.Email, it) },
                onPasswordChanged = { viewModel.onFieldChanged(FieldType.Password, it) },
            )
            LinkAndButtonSection(isEnabled = uiState.isButtonEnabled, onLinkClick = {
                navController.navigate(Screen.Login.name) {
                    navController.popBackStack()
                }
            }, onButtonClick = { viewModel.onButtonPressed() })
        }
    }
}

@Composable
fun TitleTextSection() {
    Text(
        text = stringResource(R.string.create_your_account),
        color = Color.Black,
        style = bold24,
        modifier = Modifier.padding(top = 100.dp, bottom = 35.dp)
    )
}


@Composable
private fun FieldsSection(
    uiState: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyTextField(
            value = uiState.name,
            onValueChange = onNameChanged,
            isError = uiState.isError,
            errorMessage = uiState.nameErrorMessage,
            label = stringResource(R.string.name)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            value = uiState.surname,
            onValueChange = onSurnameChanged,
            isError = uiState.isError,
            errorMessage = uiState.surnameErrorMessage,
            label = stringResource(R.string.surname)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyTextField(
            value = uiState.email,
            onValueChange = onEmailChanged,
            isError = uiState.isError,
            errorMessage = uiState.emailErrorMessage,
            label = stringResource(R.string.email)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MyPasswordTextField(
            value = uiState.password,
            onValueChange = onPasswordChanged,
            isError = uiState.isError,
            errorMessage = uiState.passwordErrorMessage
        )

        if (uiState.isMainErrorShown) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.wrong_used_data),
                color = colorResource(R.color.accent_red),
                style = medium18,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun RegisterButton(onClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = { onClick() },
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.purple),
                        colorResource(R.color.light_purple),
                    )
                ), shape = RoundedCornerShape(30.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = colorResource(R.color.white),
            disabledContentColor = colorResource(R.color.white),
            disabledContainerColor = colorResource(R.color.light_gray)
        )
    ) {
        Text(
            text = stringResource(R.string.regist), style = medium18
        )
    }
}


@Composable
private fun LinkAndButtonSection(
    isEnabled: Boolean,
    onLinkClick: () -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 90.dp, top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        RegisterButton(
            onClick = onButtonClick, isEnabled = isEnabled
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.an_account_already_exists),
                color = colorResource(R.color.bright_gray)
            )
            Text(text = stringResource(R.string.enter_pls),
                color = colorResource(R.color.purple),
                modifier = Modifier.clickable { onLinkClick() })
        }
    }


}