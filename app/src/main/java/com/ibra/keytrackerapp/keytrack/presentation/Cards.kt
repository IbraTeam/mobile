package com.ibra.keytrackerapp.keytrack.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.keytracker.data.KeyCardData
import com.ibra.keytrackerapp.common.ui.component.CardButton
import com.ibra.keytrackerapp.common.ui.theme.medium12
import com.ibra.keytrackerapp.common.ui.theme.medium18
import com.ibra.keytrackerapp.keytrack.domain.models.KeyCardDto


@Composable
fun PersonCard(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ){
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.user_fill),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                tint = Color.White
            )
            Text(
                text = text,
                style = medium18,
                color = Color.White
            )
        }
        Divider(
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        )
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
        firstButton = getKeyCardData(data.type, true),
        secondButton = getKeyCardData(data.type, false),
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
