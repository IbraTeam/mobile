package com.ibra.keytrackerapp.create_request.di

import com.ibra.keytrackerapp.create_request.domain.CreateRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CreateRequestModule {

    @Provides
    fun provideCreateRequestUseCase(): CreateRequestUseCase{
        return CreateRequestUseCase()
    }
}