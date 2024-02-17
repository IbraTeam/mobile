package com.ibra.keytrackerapp.common.auth.domain.usecase

import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.common.auth.domain.repository.AuthRepository
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val authRepository: AuthRepository){
    suspend fun execute(user: UserRegisterModel): Result<TokenResponse>{
        return try{
            authRepository.register(user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}