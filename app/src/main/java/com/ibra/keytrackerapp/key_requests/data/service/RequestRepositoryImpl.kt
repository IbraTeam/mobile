package com.ibra.keytrackerapp.key_requests.data.service

import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import com.ibra.keytrackerapp.key_requests.domain.repository.RequestRepository

class RequestRepositoryImpl(
    private val requestApiService: RequestApiService
) : RequestRepository
{
    // Получение списка всех заявок пользователя
    override suspend fun getUserRequests(token: String, weekStart: String): UserRequests {
        return requestApiService.getUserRequests(token, weekStart)
    }

    // Удаление заявки
    override suspend fun deleteRequest(token: String, id: String) {
        requestApiService.deleteRequest(token, id)
    }
}