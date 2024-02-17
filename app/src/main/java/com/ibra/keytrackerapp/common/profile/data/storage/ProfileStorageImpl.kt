package com.ibra.keytrackerapp.common.profile.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ibra.keytrackerapp.common.profile.ProfilePreferences
import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import com.ibra.keytrackerapp.common.profile.domain.storage.ProfileStorage
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class ProfileStorageImpl @Inject constructor(
    context: Context
) : ProfileStorage {

    private var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        ProfilePreferences.PROFILE_STORAGE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override suspend fun saveProfile(profile: Profile) {
        sharedPreferences.edit().apply {
            putString(ProfilePreferences.PROFILE_ID_KEY, profile.id)
            putString(ProfilePreferences.PROFILE_EMAIL_KEY, profile.email)
            putString(ProfilePreferences.PROFILE_NAME_KEY, profile.name)
            putString(ProfilePreferences.PROFILE_SURNAME_KEY, profile.surname)
            apply()
        }
    }

    override suspend fun getProfile(): Response<Profile> {
        val id = sharedPreferences.getString(ProfilePreferences.PROFILE_ID_KEY, null)
        val name = sharedPreferences.getString(ProfilePreferences.PROFILE_NAME_KEY, null)
        val surname = sharedPreferences.getString(ProfilePreferences.PROFILE_SURNAME_KEY, null)
        val email = sharedPreferences.getString(ProfilePreferences.PROFILE_EMAIL_KEY, null)

        return if ( id != null && surname != null && email != null && name != null ) {
            Response.success(
                Profile(
                    id = id,
                    email = email,
                    name = name,
                    surname = surname
                )
            )
        } else {
            Response.error(404, "Profile not found".toResponseBody(null))
        }
    }

    override suspend fun deleteProfile() {
        sharedPreferences.edit().apply {
            remove(ProfilePreferences.PROFILE_ID_KEY)
            remove(ProfilePreferences.PROFILE_EMAIL_KEY)
            remove(ProfilePreferences.PROFILE_NAME_KEY)
            remove(ProfilePreferences.PROFILE_SURNAME_KEY)
            apply()
        }
    }


}