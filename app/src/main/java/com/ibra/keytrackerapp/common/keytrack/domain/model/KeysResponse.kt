package com.ibra.keytrackerapp.common.keytrack.domain.model


data class KeysResponse(
    val keys: List<KeyDto> = emptyList()
)