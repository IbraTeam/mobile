package com.ibra.keytrackerapp.key_requests.data.service

import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

// Получение списка всех заявок пользователя
interface RequestApiService  {
    @Headers("Content-Type: application/json")
    @GET("http://95.163.229.215:8081/api/request/user")
    suspend fun getUserRequests(@Header("Authorization") token: String): UserRequests
}