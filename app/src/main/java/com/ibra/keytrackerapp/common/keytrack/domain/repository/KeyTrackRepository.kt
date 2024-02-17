package com.ibra.keytrackerapp.common.keytrack.domain.repository

import com.ibra.keytrackerapp.common.keytrack.domain.model.KeysResponse
import retrofit2.Response

interface KeyTrackRepository {
    suspend fun getKeys(token: String): Response<KeysResponse?>
    suspend fun giveKey(token: String, userId: String, keyId: String): Response<Unit>
    suspend fun getKey(token: String, keyId: String): Response<String>
    suspend fun returnKey(token: String, keyId: String): Response<Unit>
    suspend fun rejectKey(token: String, keyId: String): Response<Unit>
    suspend fun acceptKey(token: String, keyId: String): Response<String>
    suspend fun cancelKey(token: String, keyId: String): Response<Unit>

}