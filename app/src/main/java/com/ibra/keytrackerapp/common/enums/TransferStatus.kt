package com.ibra.keytrackerapp.common.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TransferStatus {
    IN_DEAN,
    ON_HANDS,
    TRANSFERRING,
    OFFERING_TO_YOU
}