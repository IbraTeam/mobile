package com.ibra.keytrackerapp.key_requests.domain.model

import com.ibra.keytrackerapp.key_requests.domain.enums.PairNumber
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestStatus
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestType
import kotlinx.serialization.Serializable

@Serializable
data class KeyRequestDto (
    val id : String = "",
    val name : String = "",
    val pairName : String = "",
    val status : RequestStatus = RequestStatus.Pending,
    val dateTime : String = "",
    val dayNumb : Int = 0,
    val repeated : Boolean = false,
    val typeBooking : RequestType = RequestType.Booking,
    val pairNumber : PairNumber = PairNumber.First,
    val keyId : String = "",
    val user : UserDto = UserDto()
)