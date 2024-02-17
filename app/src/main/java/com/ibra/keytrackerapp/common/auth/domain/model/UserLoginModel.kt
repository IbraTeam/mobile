package com.ibra.keytrackerapp.common.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginModel(
    val email: String,
    val password: String
)
