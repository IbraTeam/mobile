package com.ibra.keytrackerapp.common.auth.data.service

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/account/login")
    suspend fun login(@Body body: UserLoginModel): TokenResponse

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegisterModel): TokenResponse

    @POST("api/account/logout")
    suspend fun logout() : LogoutResponse
}