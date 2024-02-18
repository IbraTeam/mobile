package com.ibra.keytrackerapp.common.keytrack.domain.model

import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.common.enums.TransferStatus
import java.time.LocalDateTime

data class KeyDto(
    val id: String,
    val room: String,
    val dateTime: LocalDateTime,
    val pairNumber: PairNumber,
    val transferStatus: TransferStatus,
    val userName: String? = null

)
