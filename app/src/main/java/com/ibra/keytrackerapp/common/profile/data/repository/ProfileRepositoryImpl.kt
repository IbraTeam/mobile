package com.ibra.keytrackerapp.common.profile.data.repository

import com.ibra.keytrackerapp.common.profile.data.service.ProfileApiService
import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import com.ibra.keytrackerapp.common.profile.domain.repository.ProfileRepository
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ProfileRepositoryImpl(private val profileApiService: ProfileApiService) : ProfileRepository {
    override suspend fun getProfile(token: String): Response<Profile?> {
        return try {
            val response = profileApiService.getProfileData("Bearer $token")
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