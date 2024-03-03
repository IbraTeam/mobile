package com.ibra.keytrackerapp.common.auth.data.service

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: UserLoginModel): Response<TokenResponse>

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel):  Response<TokenResponse>

    @Headers("Content-Type: application/json")
    @POST("api/account/logout")
    suspend fun logout(@Header("Authorization") token: String) :  Response<LogoutResponse>
}