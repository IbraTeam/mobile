package com.ibra.keytrackerapp.sign_in_sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class SignInSignUpViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel(){


    private val _uiState = MutableStateFlow(LaunchUIState())
    val uiState: StateFlow<LaunchUIState> = _uiState.asStateFlow()

    init {
        checkTokenValid()
        updateUserProfile()
        _uiState.update { currentState ->
            currentState.copy(isScreenUpdated = true)
        }
    }

    private fun updateUserProfile() {
        viewModelScope.launch(Dispatchers.Default) {
            try{
                withTimeout(2000) {
                    if (!_uiState.value.isTokenExpired) {
                        val result = profileUseCase.getProfile(_uiState.value.token)
                        val profile = result.body()
                        if (result.isSuccessful && profile != null) {
                            profileUseCase.setProfileToLocalStorage(profile)
                        } else {
                            profileUseCase.deleteProfileFromLocalStorage()
                        }

                    }
                }
            } catch (e: Exception){
                profileUseCase.deleteProfileFromLocalStorage()
                tokenUseCase.deleteTokenFromLocalStorage()
            }

        }
    }

    private fun checkTokenValid() {
        viewModelScope.launch(Dispatchers.Default) {
            withTimeout(2000) {
                val token = tokenUseCase.getTokenFromLocalStorage()
                val isTokenExpired = tokenUseCase.isTokenExpired(token)
                _uiState.update { currentState ->
                    currentState.copy(
                        token = if (isTokenExpired) "" else token,
                        isTokenExpired = isTokenExpired
                    )
                }
                if (isTokenExpired) {
                    tokenUseCase.deleteTokenFromLocalStorage()
                }
            }

        }

    }
}

data class LaunchUIState(
    val token: String = "",
    val isTokenExpired: Boolean = true,
    val isScreenUpdated: Boolean = false
)