package com.ibra.keytrackerapp.common.auth.domain.repository

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse

interface AuthRepository {
    suspend fun login(user: UserLoginModel): Result<TokenResponse>
    suspend fun register(user: UserRegisterModel): Result<TokenResponse>
    suspend fun logout(): Result<LogoutResponse>
}