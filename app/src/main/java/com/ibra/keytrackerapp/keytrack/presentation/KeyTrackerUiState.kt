package com.ibra.keytrackerapp.keytrack.presentation

import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PersonDto

data class KeyTrackerUiState(
    val keyDtoList: List<KeyDto> = emptyList(),
    val isLogout: Boolean = false,
    val isSheetVisible: Boolean = false,
    val personList: List<PersonDto> = emptyList(),
    val personName: String = "",
    val transferKeyId: String = "",
)
