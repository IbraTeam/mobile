package com.ibra.keytrackerapp.common.profile.domain.storage

import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import retrofit2.Response

interface ProfileStorage {
    suspend fun saveProfile(profile: Profile)
    suspend fun getProfile(): Response<Profile>
    suspend fun deleteProfile()
}