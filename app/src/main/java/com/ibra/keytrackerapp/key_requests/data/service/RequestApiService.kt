package com.ibra.keytrackerapp.key_requests.data.service

import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// Получение списка всех заявок пользователя
interface RequestApiService  {
    @Headers("Content-Type: application/json")
    @GET("http://95.163.229.215:8081/api/request/user")
    suspend fun getUserRequests(@Header("Authorization") token: String, @Query("WeekStart") weekStart: String): UserRequests

    @DELETE("http://95.163.229.215:8081/api/request/{id}")
    suspend fun deleteRequest(@Header("Authorization") token: String, @Path("id") id: String)
}