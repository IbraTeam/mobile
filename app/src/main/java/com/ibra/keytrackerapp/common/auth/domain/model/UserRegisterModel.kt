package com.ibra.keytrackerapp.common.auth.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class UserRegisterModel(
    val name: String,
    val password: String,
    val email: String,
)
