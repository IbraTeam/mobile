package com.ibra.keytrackerapp.keytrack.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.enums.TransferStatus
import com.ibra.keytrackerapp.common.keytrack.data.KeyCardButtonData
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.ui.component.ExitButton
import com.ibra.keytrackerapp.common.ui.theme.light12
import com.ibra.keytrackerapp.common.ui.theme.semiBold16
import com.ibra.keytrackerapp.common.ui.theme.semiBold24
import com.ibra.keytrackerapp.key_requests.presentation.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeyTrackerScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: KeyTrackerViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()
    val sheetState = rememberModalBottomSheetState()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.layoutInfo.visibleItemsInfo){
        if (lazyListState.isScrollInProgress){
            viewModel.onPersonListScrolledToEnd(lazyListState)
        }
    }

    LaunchedEffect(uiState.isLogout) {
        if (uiState.isLogout) {
            navController.navigate(Screen.SignInSignUpScreen.name)
        }
    }

    LaunchedEffect(uiState.isSheetVisible) {
        if (uiState.isSheetVisible) {
            sheetState.show()
        } else {
            sheetState.hide()
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }, modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 38.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.key_track_title),
                    style = semiBold24,
                    textAlign = TextAlign.Start
                )
                ExitButton(onClick = {
                    navController.navigate(Screen.SignInSignUpScreen.name)
                    viewModel.onExitButtonPressed()
                })
            }
            Text(text = stringResource(R.string.your_keys), style = semiBold16)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                state = lazyGridState
            ) {
                items(uiState.keys.size) { index ->
                    val keyDto = uiState.keys[index]
                    KeyCard(data = keyDto, firstOnClick = {
                        viewModel.onFirstButtonPressed(keyDto)
                        if (keyDto.transferStatus == TransferStatus.ON_HANDS) {
                            viewModel.onSheetExpanded()
                        }
                    }, secondOnClick = { viewModel.onSecondButtonPressed(keyDto) })
                }
            }
        }

        if (uiState.isSheetVisible) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { viewModel.onSheetDismissed() },
                modifier = Modifier.fillMaxWidth(),
                containerColor = colorResource(id = R.color.light_blue)
            ) {
                MyTextField(uiState.personName, viewModel::onFieldChanged)
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    state = lazyListState
                ) {
                    items(uiState.people.size) { index ->
                        val person = uiState.people[index]
                        PersonCard(text = person.name, onClick = {
                            viewModel.onPersonSelected(person.id)
                            viewModel.onSheetDismissed()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun MyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size = 10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Go
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search_light),
                    contentDescription = null,
                    modifier = Modifier
                        .height(40.dp)
                        .width(34.dp),
                    tint = Color.White
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.input_name),
                    style = semiBold16,
                    color = Color.White
                )
            },
            keyboardActions = KeyboardActions(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                errorTextColor = Color.White,
                cursorColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledTextColor = Color.White,
                disabledBorderColor = Color.Transparent,
                disabledSupportingTextColor = Color.White,
                focusedSupportingTextColor = Color.White,
                unfocusedSupportingTextColor = Color.White,
            ),
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.White
        )
    }
}


@Composable
fun RowWithIcon(text: String, painter: Painter) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.width(30.dp), contentAlignment = Alignment.Center
        ) {
            Icon(painter = painter, contentDescription = null, modifier = Modifier.height(17.dp))
        }
        Text(text = text, style = light12)
    }
}

@Composable
fun getBackgroundColor(type: TransferStatus): Color {
    return when (type) {
        TransferStatus.IN_DEAN -> colorResource(R.color.light_green)
        TransferStatus.TRANSFERRING -> colorResource(R.color.light_pink)
        TransferStatus.OFFERING_TO_YOU -> colorResource(R.color.light_gray)
        TransferStatus.ON_HANDS -> colorResource(R.color.light_yellow)
    }
}

@Composable
fun getKeyCardData(type: TransferStatus, isFirstButton: Boolean): KeyCardButtonData? {
    return when {
        !isFirstButton && (type == TransferStatus.ON_HANDS || type == TransferStatus.OFFERING_TO_YOU) -> {
            KeyCardButtonData(
                icon = getSecondIconFromType(type)!!,
                text = getSecondButtonText(type)!!,
                color = getSecondButtonColor(type)!!
            )
        }
        isFirstButton -> {
            KeyCardButtonData(
                icon = getFirstIconFromType(type),
                text = getFirstButtonText(type),
                color = getFirstButtonColor(type)
            )
        }
        else -> null
    }
}

@Composable
fun getFirstButtonText(type: TransferStatus): String {
    return when (type) {
        TransferStatus.OFFERING_TO_YOU -> stringResource(R.string.reject)
        TransferStatus.TRANSFERRING -> stringResource(R.string.cancel)
        TransferStatus.IN_DEAN -> stringResource(R.string.accept)
        TransferStatus.ON_HANDS -> stringResource(R.string.transfer)
    }
}

@Composable
fun getSecondButtonText(type: TransferStatus): String? {
    return when (type) {
        TransferStatus.OFFERING_TO_YOU -> stringResource(R.string.accept)
        TransferStatus.ON_HANDS -> stringResource(R.string.come_back)
        else -> null
    }
}


@Composable
fun getFirstButtonColor(type: TransferStatus): Color {
    return when (type) {
        TransferStatus.IN_DEAN -> colorResource(id = R.color.green_bright)
        TransferStatus.ON_HANDS -> colorResource(id = R.color.gray)
        else -> colorResource(id = R.color.red)
    }
}

@Composable
fun getSecondButtonColor(type: TransferStatus): Color? {
    return when (type) {
        TransferStatus.OFFERING_TO_YOU -> colorResource(id = R.color.gray)
        TransferStatus.ON_HANDS -> colorResource(id = R.color.yellow)
        else -> null
    }
}


@Composable
fun getFirstIconFromType(type: TransferStatus): Painter {
    return when (type) {
        TransferStatus.IN_DEAN -> painterResource(id = R.drawable.key)
        TransferStatus.ON_HANDS -> painterResource(id = R.drawable.horizontal_down_left_main)
        else -> painterResource(id = R.drawable.close_ring)
    }
}

@Composable
fun getSecondIconFromType(type: TransferStatus): Painter? {
    return when (type) {
        TransferStatus.ON_HANDS -> painterResource(id = R.drawable.close_ring)
        TransferStatus.OFFERING_TO_YOU -> painterResource(id = R.drawable.check_ring)
        else -> null
    }
}


@Composable
fun getPersonIconFromType(type: TransferStatus): Painter? {
    return when (type) {
        TransferStatus.TRANSFERRING -> painterResource(id = R.drawable.export)
        TransferStatus.OFFERING_TO_YOU -> painterResource(id = R.drawable.imports)
        else -> null
    }
}

@Composable
fun getTextFromType(type: TransferStatus): String {
    return stringResource(
        when (type) {
            TransferStatus.IN_DEAN -> R.string.wait_for_dekanat
            TransferStatus.ON_HANDS -> R.string.on_hands
            TransferStatus.TRANSFERRING -> R.string.transer_key
            TransferStatus.OFFERING_TO_YOU -> R.string.offer_key
        }
    )
}






