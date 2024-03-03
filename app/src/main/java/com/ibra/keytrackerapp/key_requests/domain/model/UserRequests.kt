package com.ibra.keytrackerapp.key_requests.domain.model

import kotlinx.serialization.Serializable

// Тело ответа запроса получения всех заявок пользователя
@Serializable
data class UserRequests (
    val requests : List<KeyRequestDto>,
    val weekStart : String,
    val weekEnd : String
)