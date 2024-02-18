package com.ibra.keytrackerapp.common.auth.domain.usecase

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute() : Response<LogoutResponse> {
        return authRepository.logout()
    }

}