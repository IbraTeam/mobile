package com.ibra.keytrackerapp.common.keytracker.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class KeyCardData(
    val backgroundColor: Color,
    val firstButton: KeyCardButtonData?,
    val secondButton: KeyCardButtonData?,
    val personIcon: Painter?
)

data class KeyCardButtonData(
    val icon: Painter, val text: String, val color: Color
)