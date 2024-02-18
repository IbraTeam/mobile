package com.ibra.keytrackerapp.key_requests.domain.model

import com.ibra.keytrackerapp.key_requests.domain.enums.UserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id : String = "",
    val name : String = "",
    val email : String = "",
    val role : UserRole = UserRole.USER
)
