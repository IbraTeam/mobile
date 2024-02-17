package com.ibra.keytrackerapp.common.profile.domain.usecase

import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import com.ibra.keytrackerapp.common.profile.domain.repository.ProfileRepository
import com.ibra.keytrackerapp.common.profile.domain.storage.ProfileStorage
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val profileStorage: ProfileStorage

) {
    suspend fun getProfile(token: String) : Response<Profile?> {
        return try{
            val result = profileRepository.getProfile(token)

            if(result.isSuccessful){
                val profile = result.body()
                Response.success(profile)
            }else{
                Response.error(result.code(), result.errorBody()!!)
            }
        }catch (e: Exception){
            Response.error(500, e.message!!.toResponseBody())
        }
    }

    suspend fun getProfileFromLocalStorage(): Response<Profile> {
        return profileStorage.getProfile()
    }

    suspend fun deleteProfileFromLocalStorage(){
        profileStorage.deleteProfile()
    }

    suspend fun setProfileToLocalStorage(profile: Profile) {
        profileStorage.saveProfile(profile)
    }
}