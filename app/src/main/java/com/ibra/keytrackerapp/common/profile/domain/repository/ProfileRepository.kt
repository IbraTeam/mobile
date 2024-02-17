package com.ibra.keytrackerapp.common.profile.domain.repository

import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import retrofit2.Response

interface ProfileRepository {
    suspend fun getProfile(token: String): Response<Profile?>
}