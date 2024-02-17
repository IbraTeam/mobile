package com.ibra.keytrackerapp.common.token.domain.usecase

import com.ibra.keytrackerapp.common.token.domain.storage.TokenStorage
import com.ibra.keytrackerapp.common.token.entity.JwtTokenHelper
import javax.inject.Inject

class TokenUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val jwtTokenHelper: JwtTokenHelper
) {
    suspend fun setTokenToLocalStorage(token: String){
        tokenStorage.saveToken(token)
    }

    suspend fun getTokenFromLocalStorage() : String {
        return tokenStorage.getToken()
    }

    suspend fun deleteTokenFromLocalStorage(){
        tokenStorage.deleteToken()
    }

    fun isTokenExpired(token: String): Boolean{
        return jwtTokenHelper.isExpired(token)
    }


}