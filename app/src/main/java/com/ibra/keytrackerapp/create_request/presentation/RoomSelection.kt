@file:OptIn(ExperimentalLayoutApi::class)

package com.ibra.keytrackerapp.create_request.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibra.keytrackerapp.R
import com.ibra.keytrackerapp.common.ui.theme.BlueColor
import com.ibra.keytrackerapp.common.ui.theme.LightBlueColor
import com.ibra.keytrackerapp.common.ui.theme.RoomBgColor
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey

// Выбор аудитории
@Composable
fun RoomSelection(
    viewModel: CreateRequestViewModel = hiltViewModel()
) {
    val vmValues by viewModel.uiState.collectAsState()

    Text(
        modifier = Modifier
            .padding(24.dp, 24.dp, 24.dp, 0.dp),
        text = "Выберите свободную аудиторию",
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    )

    FlowRow(
        modifier = Modifier
            .padding(16.dp, 10.dp, 16.dp, 0.dp)
            .fillMaxWidth()
    ) {
        vmValues.freeKeys.forEach { it ->
            RoomElement(key = it)
        }
    }
}

// Аудитория
@Composable
fun RoomElement(
    viewModel: CreateRequestViewModel = hiltViewModel(),
    key: FreeKey
) {
    val vmValues by viewModel.uiState.collectAsState()
    val isSelected = vmValues.selectedKey == key

    Row(
        modifier = Modifier
            .padding(8.dp, 6.dp)
            .width(80.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) LightBlueColor else RoomBgColor)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) BlueColor else Color.Transparent
            )
            .clickable {
                viewModel.onSelectKey(key)
            }
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                .align(Alignment.CenterVertically),
            imageVector = ImageVector.vectorResource(
                if (isSelected) R.drawable.selected_room else R.drawable.unselected_room
            ),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(8.dp, 0.dp, 0.dp, 0.dp),
            text = key.name,
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}