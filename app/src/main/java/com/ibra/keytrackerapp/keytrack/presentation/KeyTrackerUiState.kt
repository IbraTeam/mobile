package com.ibra.keytrackerapp.keytrack.presentation

import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PagePaginationDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PersonDto

data class KeyTrackerUiState(
    val keys: List<KeyDto> = emptyList(),
    val isLogout: Boolean = false,
    val isSheetVisible: Boolean = false,
    val people: List<PersonDto> = emptyList(),
    val personName: String = "",
    val transferKeyId: String = "",
    val pagination: PagePaginationDto = PagePaginationDto()
)
