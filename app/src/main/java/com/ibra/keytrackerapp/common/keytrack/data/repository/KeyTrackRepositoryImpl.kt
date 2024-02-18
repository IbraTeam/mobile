package com.ibra.keytrackerapp.common.keytrack.data.repository

import com.ibra.keytrackerapp.common.keytrack.data.service.KeyTrackApiService
import com.ibra.keytrackerapp.common.keytrack.domain.model.KeysResponse
import com.ibra.keytrackerapp.common.keytrack.domain.repository.KeyTrackRepository
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class KeyTrackRepositoryImpl(private val keyTrackApiService: KeyTrackApiService) : KeyTrackRepository {
    override suspend fun getKeys(token: String): Response<KeysResponse?> {
        return try {
            val response = keyTrackApiService.getKeys("Bearer $token")
            if (response.isSuccessful) {
                Response.success(response.body())
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun giveKey(token: String, userId: String, keyId: String): Response<Unit> {
        return try {
            handleResponse(keyTrackApiService.giveKey("Bearer $token", userId, keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun getKey(token: String, keyId: String): Response<String> {
        return try {
            handleResponse(keyTrackApiService.getKey("Bearer $token", keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }



    override suspend fun returnKey(token: String, keyId: String): Response<Unit> {
        return try {
            handleResponse(keyTrackApiService.returnKey("Bearer $token", keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun rejectKey(token: String, keyId: String): Response<Unit> {
        return try {
            handleResponse(keyTrackApiService.rejectKey("Bearer $token", keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun acceptKey(token: String, keyId: String): Response<String> {
        return try {
            handleResponse(keyTrackApiService.acceptKey( "Bearer $token", keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    override suspend fun cancelKey(token: String, keyId: String): Response<Unit> {
        return try {
            handleResponse(keyTrackApiService.cancelKey("Bearer $token", keyId))
        } catch (e: Exception) {
            Response.error(500, e.message!!.toResponseBody())
        }
    }


    private fun <T> handleResponse(response: Response<T>): Response<T> {
        return if (response.isSuccessful) {
            Response.success(response.body())
        } else {
            Response.error(response.code(), response.errorBody()!!)
        }
    }


}