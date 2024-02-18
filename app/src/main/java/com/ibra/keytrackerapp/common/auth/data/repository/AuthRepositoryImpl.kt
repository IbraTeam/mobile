package com.ibra.keytrackerapp.common.auth.data.repository

import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import com.ibra.keytrackerapp.common.auth.data.service.AuthApiService
import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class AuthRepositoryImpl(private val authApiService: AuthApiService) : AuthRepository {


    override suspend fun login(user: UserLoginModel): Response<TokenResponse> {
        return try {
            val response = authApiService.login(user)
            if (response.isSuccessful) {
                Response.success(response.body())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun register(user: UserRegisterModel): Response<TokenResponse> {
        return try {
            val response = authApiService.register(user)
            if (response.isSuccessful) {
                Response.success(response.body())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun logout(token: String) : Response<LogoutResponse>{
        return try {
            val response = authApiService.logout(token)
            if (response.isSuccessful) {
                Response.success(response.body())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }
}

