package com.ibra.keytrackerapp.keytrack.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.enums.KeyCardType
import com.ibra.keytrackerapp.common.keytracker.data.KeyCardButtonData
import com.ibra.keytrackerapp.common.keytracker.data.KeyCardData
import com.ibra.keytrackerapp.common.ui.component.CardButton
import com.ibra.keytrackerapp.common.ui.component.ExitButton
import com.ibra.keytrackerapp.common.ui.theme.light12
import com.ibra.keytrackerapp.common.ui.theme.medium12
import com.ibra.keytrackerapp.common.ui.theme.semiBold16
import com.ibra.keytrackerapp.common.ui.theme.semiBold24
import com.ibra.keytrackerapp.keytrack.domain.models.KeyCardDto

@Composable
fun KeyTrackerScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: KeyTrackerViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(uiState.isExitButtonPressed) {
        if (uiState.isExitButtonPressed) {
            navController.navigateUp()
        }
    }

    Scaffold(
        modifier = modifier
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
                ExitButton(onClick = { viewModel.onExitButtonPressed() })
            }
            Text(text = stringResource(R.string.your_keys), style = semiBold16)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                state = lazyGridState
            ) {
                items(uiState.keyCardDtoList.size) { index ->
                    val keyCardDto = uiState.keyCardDtoList[index]
                    KeyCard(data = keyCardDto, firstOnClick = { }, secondOnClick = { })
                }
            }
        }

    }
}


@Composable
fun KeyCard(
    data: KeyCardDto,
    firstOnClick: () -> Unit,
    secondOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyCardData = KeyCardData(
        backgroundColor = getBackgroundColor(data.type),
        firstButton = getKeyCardData(data.type, false),
        secondButton = getKeyCardData(data.type, true),
        personIcon = getPersonIconFromType(data.type)
    )

    Column(
        modifier = modifier
            .height(200.dp)
            .width(156.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(keyCardData.backgroundColor),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.padding(start = 10.dp, top = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = getTextFromType(data.type),
                style = medium12,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            )
            RowWithIcon(text = data.auditory, painter = painterResource(R.drawable.destination))
            RowWithIcon(text = data.date, painter = painterResource(R.drawable.date_range))
            RowWithIcon(text = data.time, painter = painterResource(R.drawable.time))
            if (keyCardData.personIcon != null) {
                RowWithIcon(text = data.person ?: "", painter = keyCardData.personIcon)
            }
        }

        Column {
            if (keyCardData.firstButton != null) {
                CardButton(
                    onClick = firstOnClick, buttonData = keyCardData.firstButton
                )
            }
            if (keyCardData.secondButton != null) {
                CardButton(
                    onClick = secondOnClick, buttonData = keyCardData.secondButton
                )
            }
        }
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
fun getBackgroundColor(type: KeyCardType): Color {
    return when (type) {
        KeyCardType.WAIT -> colorResource(R.color.light_green)
        KeyCardType.SUGGEST_OFFER -> colorResource(R.color.light_pink)
        KeyCardType.HAVE_OFFER -> colorResource(R.color.light_gray)
        KeyCardType.ON_HAND -> colorResource(R.color.light_yellow)
    }
}

@Composable
fun getKeyCardData(type: KeyCardType, isFirstButton: Boolean): KeyCardButtonData? {
    return when {
        isFirstButton -> {
            KeyCardButtonData(
                icon = getFirstIconFromType(type),
                text = getFirstButtonText(type),
                color = getFirstButtonColor(type)
            )
        }

        type == KeyCardType.ON_HAND -> {
            KeyCardButtonData(
                icon = getSecondIconFromType(type)!!,
                text = getSecondButtonText(type)!!,
                color = getSecondButtonColor(type)!!
            )
        }

        else -> null
    }
}

@Composable
fun getFirstButtonText(type: KeyCardType): String {
    return when (type) {
        KeyCardType.HAVE_OFFER -> stringResource(R.string.reject)
        KeyCardType.SUGGEST_OFFER -> stringResource(R.string.cancel)
        KeyCardType.WAIT -> stringResource(R.string.accept)
        KeyCardType.ON_HAND -> stringResource(R.string.transfer)
    }
}

@Composable
fun getSecondButtonText(type: KeyCardType): String? {
    return when (type) {
        KeyCardType.HAVE_OFFER -> stringResource(R.string.accept)
        KeyCardType.ON_HAND -> stringResource(R.string.come_back)
        else -> null
    }
}


@Composable
fun getFirstButtonColor(type: KeyCardType): Color {
    return when (type) {
        KeyCardType.WAIT -> colorResource(id = R.color.green_bright)
        KeyCardType.ON_HAND -> colorResource(id = R.color.gray)
        else -> colorResource(id = R.color.red)
    }
}

@Composable
fun getSecondButtonColor(type: KeyCardType): Color? {
    return when (type) {
        KeyCardType.HAVE_OFFER -> colorResource(id = R.color.gray)
        KeyCardType.ON_HAND -> colorResource(id = R.color.yellow)
        else -> null
    }
}


@Composable
fun getFirstIconFromType(type: KeyCardType): Painter {
    return when (type) {
        KeyCardType.WAIT -> painterResource(id = R.drawable.key)
        KeyCardType.ON_HAND -> painterResource(id = R.drawable.horizontal_down_left_main)
        else -> painterResource(id = R.drawable.close_ring)
    }
}

@Composable
fun getSecondIconFromType(type: KeyCardType): Painter? {
    return when (type) {
        KeyCardType.ON_HAND -> painterResource(id = R.drawable.close_ring)
        KeyCardType.HAVE_OFFER -> painterResource(id = R.drawable.check_ring)
        else -> null
    }
}


@Composable
fun getPersonIconFromType(type: KeyCardType): Painter? {
    return when (type) {
        KeyCardType.SUGGEST_OFFER -> painterResource(id = R.drawable.export)
        KeyCardType.HAVE_OFFER -> painterResource(id = R.drawable.imports)
        else -> null
    }
}

@Composable
fun getTextFromType(type: KeyCardType): String {
    return stringResource(
        when (type) {
            KeyCardType.WAIT -> R.string.wait_for_dekanat
            KeyCardType.ON_HAND -> R.string.on_hands
            KeyCardType.SUGGEST_OFFER -> R.string.transer_key
            KeyCardType.HAVE_OFFER -> R.string.offer_key
        }
    )

}


@Preview
@Composable
fun PreviewKeyCard() {
    KeyCard(data = KeyCardDto("", KeyCardType.WAIT, "", "", ""),
        firstOnClick = { /*TODO*/ },
        secondOnClick = { /*TODO*/ })
}




