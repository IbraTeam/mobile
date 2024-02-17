package com.example.moviecatalog.common.auth.domain.usecase

import com.ibra.keytrackerapp.common.auth.domain.model.LogoutResponse
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute() : Result<LogoutResponse>{
        return authRepository.logout()
    }
}