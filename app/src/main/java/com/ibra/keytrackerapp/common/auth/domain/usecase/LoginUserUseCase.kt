package com.ibra.keytrackerapp.common.auth.domain.usecase

import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend fun execute(user: UserLoginModel): Result<TokenResponse>{
        return authRepository.login(user)
    }
}