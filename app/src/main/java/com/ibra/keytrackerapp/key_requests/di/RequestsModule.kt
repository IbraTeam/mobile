package com.ibra.keytrackerapp.key_requests.di

import com.ibra.keytrackerapp.key_requests.domain.use_case.KeyRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RequestsModule {

    @Provides
    fun provideKeyRequestUseCase() : KeyRequestUseCase {
        return KeyRequestUseCase()
    }
}