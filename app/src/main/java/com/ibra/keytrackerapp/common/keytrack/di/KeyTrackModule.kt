package com.ibra.keytrackerapp.common.keytrack.di

import com.ibra.keytrackerapp.common.keytrack.data.repository.KeyTrackRepositoryImpl
import com.ibra.keytrackerapp.common.keytrack.data.service.KeyTrackApiService
import com.ibra.keytrackerapp.common.keytrack.domain.repository.KeyTrackRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class KeyTrackModule{
    @Provides
    fun provideApiImpl(retrofit: Retrofit) : KeyTrackApiService {
        return retrofit.create(KeyTrackApiService::class.java)
    }

    @Provides
    fun provideKeyTrackRepository(keyTrackApiService: KeyTrackApiService): KeyTrackRepository {
        return KeyTrackRepositoryImpl(keyTrackApiService)
    }
}
