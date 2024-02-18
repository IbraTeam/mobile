package com.ibra.keytrackerapp.key_requests.data.service

import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import com.ibra.keytrackerapp.key_requests.domain.repository.RequestRepository

class RequestRepositoryImpl(
    private val requestApiService: RequestApiService
) : RequestRepository
{
    // Получение списка всех заявок пользователя
    override suspend fun getUserRequests(token: String): UserRequests {
        return requestApiService.getUserRequests("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsZWhhQGxlaGEubGVoYSIsInJvbGVzIjpbIlJPTEVfVEVBQ0hFUiJdLCJleHAiOjE3MDgyNjExMDQsInVzZXJJZCI6IjFiMGY3ZDQ4LWEwNGItNDk2Yi04YjNhLThjYzNmYTBlMTUyNCIsImlhdCI6MTcwODI1NzUwNCwianRpIjoiZTcxZjEyNmMtYzlmNC00NDEyLThmZWQtOGRkMDc0MmVhNzA0In0.30ONIvT_65IK5BNPm_z6dt5ur0pOGN8_Zzb0TH6gsUo")
    }
}