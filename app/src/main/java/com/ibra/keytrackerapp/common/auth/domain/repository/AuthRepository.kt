package com.ibra.keytrackerapp.common.auth.domain.repository

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(user: UserLoginModel): Response<TokenResponse>
    suspend fun register(user: UserRegisterModel): Response<TokenResponse>
    suspend fun logout(): Response<LogoutResponse>
}