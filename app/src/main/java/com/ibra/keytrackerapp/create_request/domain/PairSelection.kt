@file:OptIn(ExperimentalFoundationApi::class)

package com.ibra.keytrackerapp.create_request.domain

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.common.ui.theme.BlueColor
import com.ibra.keytrackerapp.common.ui.theme.PairSelectionTextColor
import com.ibra.keytrackerapp.common.ui.theme.PairTimeColor
import com.ibra.keytrackerapp.common.ui.theme.VeryLightGray
import com.ibra.keytrackerapp.create_request.CreateRequestViewModel

// Выбор пары
@Composable
fun PairSelection(
    viewModel: CreateRequestViewModel = hiltViewModel()
)
{
    val vmValues by viewModel.uiState.collectAsState()

    Text(
        modifier = Modifier
            .padding(24.dp, 36.dp, 24.dp, 0.dp),
        text = "Выберите пару",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    )

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(24.dp, 16.dp, 24.dp, 0.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(VeryLightGray),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            PairTime(isStartTime = true)
            PairDropDownButton()
            PairTime(isStartTime = false)
        }

        if (vmValues.isPairSelecting)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(90.dp, 96.dp, 0.dp, 0.dp)
            ){
                PairsList()
            }
    }
}

// Время пары
@Composable
fun PairTime(
    viewModel: CreateRequestViewModel = hiltViewModel(),
    isStartTime: Boolean
) {
    val vmValues by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(0.dp, 24.dp)
    ) {
        Text(
            modifier = Modifier
                .width(90.dp),
            text = if (isStartTime) "От" else "До",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = PairSelectionTextColor
            )
        )

        val startTime = viewModel.getPairStartTime(vmValues.selectedPair.ordinal)
        val endTime = viewModel.getPairEndTime(vmValues.selectedPair.ordinal)

        Text(
            modifier = Modifier
                .padding(0.dp, 4.dp, 0.dp, 0.dp),
            text = if (isStartTime) startTime else endTime,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PairTimeColor
            )
        )
    }
}

// Кнопка выпадающего списка пар
@Composable
fun PairDropDownButton(
    viewModel: CreateRequestViewModel = hiltViewModel()
) {
    val vmValues by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Max)
            .fillMaxHeight()
            .padding(0.dp, 16.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    viewModel.onDropDownMenuClick()
                }
        ) {
            val pairNum = vmValues.selectedPair.ordinal + 1

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "$pairNum пара",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = PairSelectionTextColor
                )
            )

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(4.dp, 2.dp, 0.dp, 0.dp)
                    .size(10.dp, 5.dp),
                imageVector = ImageVector.vectorResource(
                    if (vmValues.isPairSelecting) R.drawable.arrow_up else R.drawable.down_arrow
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(PairSelectionTextColor)
        )
    }
}

// Список пар
@Composable
fun PairsList() {
    val pairsList = listOf(
        PairNumber.First,
        PairNumber.Second,
        PairNumber.Third,
        PairNumber.Fourth,
        PairNumber.Fifth,
        PairNumber.Sixth,
        PairNumber.Seventh
    )

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null
    ) {
        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(BlueColor)
                .height(152.dp)
                .width(200.dp)
        ) {
            items(
                items = pairsList,
                itemContent = {
                        pair -> PairListElement(pair = pair)
                    Divider(
                        modifier = Modifier
                            .background(Color.White)
                            .height(1.dp)
                    )
                })
        }
    }
}

// Элемент выпадающего списка пар
@Composable
fun PairListElement(
    viewModel: CreateRequestViewModel = hiltViewModel(),
    pair: PairNumber
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .clickable {
                viewModel.onPairSelected(pair)
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp, 4.dp, 0.dp, 0.dp),
            text = "${pair.ordinal + 1} пара",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(0.dp, 4.dp, 8.dp, 0.dp),
            text = "${viewModel.getPairStartTime(pair.ordinal)} - ${viewModel.getPairEndTime(pair.ordinal)}",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.White
            )
        )
    }
}