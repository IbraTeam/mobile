package com.ibra.keytrackerapp.create_request.di

import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.create_request.data.CreateRequestApiService
import com.ibra.keytrackerapp.create_request.data.CreateRequestRepositoryImpl
import com.ibra.keytrackerapp.create_request.domain.repository.CreateRequestRepository
import com.ibra.keytrackerapp.create_request.domain.use_case.CreateRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
class CreateRequestModule {

    @Provides
    fun provideCreateRequestApiImplementation(retrofit : List<Retrofit>) : CreateRequestApiService {
        return retrofit[1].create(CreateRequestApiService::class.java)
    }

    @Provides
    fun provideCreateRequestRepository(createRequestService: CreateRequestApiService): CreateRequestRepository {
        return CreateRequestRepositoryImpl(createRequestService)
    }

    @Provides
    fun provideCreateRequestUseCase(createRequestRepository: CreateRequestRepository, tokenUseCase: TokenUseCase): CreateRequestUseCase {
        return CreateRequestUseCase(createRequestRepository, tokenUseCase)
    }
}