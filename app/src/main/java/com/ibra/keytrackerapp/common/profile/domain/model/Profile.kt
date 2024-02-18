package com.ibra.keytrackerapp.common.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)
