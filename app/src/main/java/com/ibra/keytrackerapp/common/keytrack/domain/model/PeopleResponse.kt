package com.ibra.keytrackerapp.common.keytrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val users: List<PersonDto> = emptyList(),
    val page: PagePaginationDto = PagePaginationDto()
)
