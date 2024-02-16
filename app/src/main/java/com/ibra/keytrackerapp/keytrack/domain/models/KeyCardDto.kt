package com.ibra.keytrackerapp.keytrack.domain.models

import com.ibra.keytrackerapp.common.enums.KeyCardType

data class KeyCardDto(
    val id: String,
    val type: KeyCardType,
    val auditory: String,
    val date: String,
    val time: String,
    val person: String? = null
)
