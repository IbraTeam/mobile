package com.ibra.keytrackerapp.create_request.data

import com.ibra.keytrackerapp.create_request.domain.model.CreateRequestDto
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface CreateRequestApiService {
    @Headers("Content-Type: application/json")
    @POST("api/request/create")
    suspend fun createRequest(@Header("Authorization") token: String, @Body request : CreateRequestDto)

    @Headers("Content-Type: application/json")
    @GET("api/request/free")
    suspend fun getFreeKeys(
        @Header("Authorization")
        token: String,

        @Query("BookingTime")
        bookingTime : String,

        @Query("PairNumber")
        pairNumber : Int,

        @Query("RepeatedCount")
        repeatedCount : Int
        ) : List<FreeKey>
}