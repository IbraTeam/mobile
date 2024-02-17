package com.ibra.keytrackerapp.common.profile.di

import android.content.Context
import com.ibra.keytrackerapp.common.profile.data.repository.ProfileRepositoryImpl
import com.ibra.keytrackerapp.common.profile.data.service.ProfileApiService
import com.ibra.keytrackerapp.common.profile.data.storage.ProfileStorageImpl
import com.ibra.keytrackerapp.common.profile.domain.repository.ProfileRepository
import com.ibra.keytrackerapp.common.profile.domain.storage.ProfileStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    @Singleton
    fun provideProfileStorage(@ApplicationContext context: Context): ProfileStorage {
        return ProfileStorageImpl(context)
    }

    @Provides
    fun provideApiImplementation(retrofit: Retrofit): ProfileApiService {
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    fun provideProfileRepository(profileApiService: ProfileApiService): ProfileRepository {
        return ProfileRepositoryImpl(profileApiService)
    }
}