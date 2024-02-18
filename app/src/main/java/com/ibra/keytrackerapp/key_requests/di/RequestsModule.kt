package com.ibra.keytrackerapp.key_requests.di

import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.key_requests.data.service.RequestApiService
import com.ibra.keytrackerapp.key_requests.data.service.RequestRepositoryImpl
import com.ibra.keytrackerapp.key_requests.domain.repository.RequestRepository
import com.ibra.keytrackerapp.key_requests.domain.use_case.KeyRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class RequestsModule {

    @Provides
    fun provideRequestApiImplementation(retrofit: List<Retrofit>): RequestApiService {
        return retrofit[1].create(RequestApiService::class.java)
    }

    @Provides
    fun provideRequestRepository(requestApiService: RequestApiService): RequestRepository {
        return RequestRepositoryImpl(requestApiService)
    }

    @Provides
    fun provideKeyRequestUseCase(requestRepository: RequestRepository, tokenUseCase: TokenUseCase) : KeyRequestUseCase {
        return KeyRequestUseCase(requestRepository, tokenUseCase)
    }
}