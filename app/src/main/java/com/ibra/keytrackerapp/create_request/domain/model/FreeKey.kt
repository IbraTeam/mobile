package com.ibra.keytrackerapp.create_request.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FreeKey (
    val name: String,
    val keyId: String
)