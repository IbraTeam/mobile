package com.ibra.keytrackerapp.common.token.di

import android.content.Context
import com.ibra.keytrackerapp.common.token.entity.JwtTokenHelper
import com.ibra.keytrackerapp.common.token.data.storage.TokenStorageImpl
import com.ibra.keytrackerapp.common.token.domain.storage.TokenStorage
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {

    @Provides
    @Singleton
    fun provideTokenStorage(@ApplicationContext context: Context): TokenStorage {
        return TokenStorageImpl(context)
    }

    @Provides
    @Singleton
    fun provideJwtTokenHelper(): JwtTokenHelper {
        return JwtTokenHelper()
    }

    @Provides
    fun provideTokenUseCase(tokenStorage: TokenStorage, jwtTokenHelper: JwtTokenHelper): TokenUseCase {
        return TokenUseCase(tokenStorage = tokenStorage, jwtTokenHelper = jwtTokenHelper)
    }
}