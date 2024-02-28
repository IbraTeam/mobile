package com.ibra.keytrackerapp.common.keytrack.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)
