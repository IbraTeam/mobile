package com.ibra.keytrackerapp.common.token.domain.model

import com.ibra.keytrackerapp.key_requests.domain.enums.UserRole
import kotlinx.serialization.Serializable


@Serializable
data class TokenResponse(
    val token: String,
    val role: UserRole
)
