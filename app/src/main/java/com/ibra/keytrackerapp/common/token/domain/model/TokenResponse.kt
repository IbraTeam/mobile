package com.ibra.keytrackerapp.common.token.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    val token: String
)
