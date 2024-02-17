package com.ibra.keytrackerapp.common.auth.data.repository

import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import com.ibra.keytrackerapp.common.auth.data.service.AuthApiService
import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse

class AuthRepositoryImpl(private val authApiService: AuthApiService) : AuthRepository {


    override suspend fun login(user: UserLoginModel): Result<TokenResponse> {
        return try {
            val response = authApiService.login(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: UserRegisterModel): Result<TokenResponse> {
        return try {
            val response = authApiService.register(user)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() : Result<LogoutResponse>{
        return try {
            val response = authApiService.logout()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

