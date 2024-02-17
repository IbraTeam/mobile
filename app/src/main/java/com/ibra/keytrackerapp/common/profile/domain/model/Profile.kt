package com.ibra.keytrackerapp.common.profile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val email: String,
    val name: String,
    val surname: String
)
