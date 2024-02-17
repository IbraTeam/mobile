package com.ibra.keytrackerapp.key_requests.domain.model

data class KeyRequestDto (
    val id : String = "",
    val name : String = "",
    val pairName : String = "",
    val status : RequestStatus = RequestStatus.Pending,
    val dateTime : String = "",
    val dayNumb : Int = 0,
    val repeated : Boolean = false,
    val typeBooking : RequestType = RequestType.Booking,
    val pairNumber : Int = 0,
    val keyId : String = "",
    val user : UserDto = UserDto()
)