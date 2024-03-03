package com.ibra.keytrackerapp.common.keytrack.domain.repository

import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PeopleResponse
import retrofit2.Response

interface KeyTrackRepository {
    suspend fun getKeys(token: String): Response<List<KeyDto>>
    suspend fun giveKey(token: String, userId: String, keyId: String): Response<Unit>
    suspend fun getKey(token: String, keyId: String): Response<String>
    suspend fun returnKey(token: String, keyId: String): Response<Unit>
    suspend fun rejectKey(token: String, keyId: String): Response<Unit>
    suspend fun acceptKey(token: String, keyId: String): Response<String>
    suspend fun cancelKey(token: String, keyId: String): Response<Unit>
    suspend fun getPeople(token: String, page: Int, name: String?): Response<PeopleResponse>

}