package com.ibra.keytrackerapp.common.keytrack.data.service

import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PeopleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface KeyTrackApiService {
    @GET("/api/audience-key")
    suspend fun getKeys(
        @Header("Authorization") token: String
    ): Response<List<KeyDto>>

    @PATCH("/api/audience-key/give/{userId}")
    suspend fun giveKey(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body keyId: String
    ): Response<Unit>

    @PATCH("/api/audience-key/get/{keyId}")
    suspend fun getKey(
        @Header("Authorization") token: String,
        @Path("keyId") keyId: String
    ): Response<String>

    @PATCH("/api/audience-key/return/{keyId}")
    suspend fun returnKey(
        @Header("Authorization") token: String,
        @Path("keyId") keyId: String
    ): Response<Unit>

    @POST("/api/audience-key/reject/{keyId}")
    suspend fun rejectKey(
        @Header("Authorization") token: String,
        @Path("keyId") keyId: String
    ): Response<Unit>

    @POST("/api/audience-key/accept/{keyId}")
    suspend fun acceptKey(
        @Header("Authorization") token: String,
        @Path("keyId") keyId: String
    ): Response<String>

    @POST("/api/audience-key/cancel/{keyId}")
    suspend fun cancelKey(
        @Header("Authorization") token: String,
        @Path("keyId") keyId: String
    ): Response<Unit>

    @GET("/api/account/users")
    suspend fun getPersons(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("name") name: String?
    ): Response<PeopleResponse>
}