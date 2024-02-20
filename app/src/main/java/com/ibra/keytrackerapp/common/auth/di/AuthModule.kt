package com.ibra.keytrackerapp.common.auth.di

import com.ibra.keytrackerapp.common.auth.data.repository.AuthRepositoryImpl
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import com.ibra.keytrackerapp.common.auth.data.service.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideApiImplementation(retrofit: List<Retrofit>): AuthApiService {
        return retrofit[0].create(AuthApiService::class.java)
    }

    @Provides
    fun provideAuthRepository(authApiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }
}