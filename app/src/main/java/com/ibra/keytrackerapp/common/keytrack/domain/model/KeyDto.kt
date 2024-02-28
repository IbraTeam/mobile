package com.ibra.keytrackerapp.common.keytrack.domain.model

import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.common.enums.TransferStatus
import kotlinx.serialization.Serializable

@Serializable
data class KeyDto(
    val keyId: String,
    val room: String? = "",
    val dateTime: String? = "",
    val pairNumber: PairNumber? = PairNumber.First,
    val transferStatus: TransferStatus = TransferStatus.IN_DEAN,
    val userName: String? = null
)
