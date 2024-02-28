package com.ibra.keytrackerapp.common.keytrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PagePaginationDto(
    val totalPages: Int = 0,
    val currentPage: Int = 1,
    val pageSize: Int = 2,
    val totalElements: Int = 0
)
