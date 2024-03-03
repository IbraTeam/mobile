package com.ibra.keytrackerapp.common.keytrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PagePaginationDto(
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val pageSize: Int = 10,
    val totalElements: Int = 0
)
