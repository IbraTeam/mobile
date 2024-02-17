package com.ibra.keytrackerapp.key_requests.domain.model

data class UserDto(
    val id : String = "",
    val name : String = "",
    val email : String = "",
    val role : UserRole = UserRole.User
)
