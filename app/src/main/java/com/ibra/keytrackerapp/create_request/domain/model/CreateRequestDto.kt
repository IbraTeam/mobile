package com.ibra.keytrackerapp.create_request.domain.model

import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestType
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestDto(
    val dateTime : String,
    val RepeatCount : Int,
    val typeBooking : RequestType,
    val pairNumber : PairNumber,
    val keyId : String,
    val pairName : String?
)