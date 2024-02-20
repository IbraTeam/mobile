package com.ibra.keytrackerapp.login

import androidx.compose.foundation.Image
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
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
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
                onEmailChanged = { viewModel.onFieldChanged(FieldType.Email, it) },
                onPasswordChanged = { viewModel.onFieldChanged(FieldType.Password, it) },
            )
            LinkAndButtonSection(isEnabled = uiState.isButtonEnabled, onLinkClick = {
                navController.navigate(Screen.Registration.name) {
                    navController.popBackStack()
                }
            }, onButtonClick = {
                viewModel.onButtonPressed(navController)

                /*navController.navigate(Screen.RequestsScreen.name) {
                    navController.popBackStack()
                }*/
            })
        }
    }
}

@Composable
fun TitleTextSection() {
    Text(
        text = stringResource(R.string.enter_to_account), color = Color.Black, style = bold24,
        modifier = Modifier.padding(top = 90.dp, bottom = 45.dp)
    )
}


@Composable
private fun FieldsSection(
    uiState: LoginUiState,
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
                text = stringResource(R.string.wrong_login_or_password),
                color = colorResource(R.color.accent_red),
                style = medium18,
                textAlign = TextAlign.Start
            )
        }

    }

}


@Composable
fun LoginButton(onClick: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = { onClick() },
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(30.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.accent_red),
            contentColor = colorResource(R.color.white),
            disabledContentColor = colorResource(R.color.white),
            disabledContainerColor = colorResource(R.color.light_gray)
        )
    ) {
        Text(
            text = stringResource(R.string.enter), style = medium18
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
        LoginButton(
            onClick = onButtonClick, isEnabled = isEnabled
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.is_there_account),
                color = colorResource(R.color.bright_gray)
            )
            Text(text = stringResource(R.string.register),
                color = colorResource(R.color.accent_red),
                modifier = Modifier.clickable { onLinkClick() })
        }
    }


}