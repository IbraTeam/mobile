package com.ibra.keytrackerapp.common.profile.data.service

import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApiService {
    @GET("api/account/profile")
    suspend fun getProfileData(@Header("Authorization") token: String): Response<Profile>
}

