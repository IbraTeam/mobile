package com.ibra.keytrackerapp.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.keytrack.data.KeyCardButtonData
import com.ibra.keytrackerapp.common.ui.theme.semiBold12

@Composable
fun ExitButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.exit),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(50.dp)
                .width(50.dp)
        )
        Text(
            text = stringResource(R.string.exit),
            modifier = Modifier,
            color = colorResource(R.color.accent_red)
        )
    }

}


@Composable
fun CardButton(
    onClick: () -> Unit, buttonData: KeyCardButtonData, modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        .height(28.dp)
        .fillMaxWidth()
        .clickable { onClick() }
        .background(buttonData.color, RoundedCornerShape(10.dp))
    ) {
        Icon(
            painter = buttonData.icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(24.dp)
                .padding(start = 4.dp)
                .height(24.dp)
        )
        Text(
            text = buttonData.text,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = semiBold12
        )
    }
}




