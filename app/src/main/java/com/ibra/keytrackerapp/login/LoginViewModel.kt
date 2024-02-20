package com.ibra.keytrackerapp.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.common.auth.data.enum.FieldType
import com.ibra.keytrackerapp.common.auth.data.mapper.toUserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.usecase.LoginUserUseCase
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.common.validation.domain.usecase.LoginValidationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginValidationUseCase: LoginValidationUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val tokenUseCase: TokenUseCase,
    private val profileUseCase: ProfileUseCase
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()



    @OptIn(FlowPreview::class)
    fun onFieldChanged(type: FieldType, newValue: Any) {
        viewModelScope.launch {
            val valueFlow = flowOf(newValue as String)
                .debounce(300)
                .distinctUntilChanged()
            when (type) {
                FieldType.Email -> {
                    valueFlow.collect { newLogin ->
                        onEmailChanged(newLogin)
                        updateErrorAndButtonStateForField()
                    }
                }

                FieldType.Password -> {
                    valueFlow.collect { newPassword ->
                        onPasswordChanged(newPassword)
                        updateErrorAndButtonStateForField()
                    }
                }
                else -> {}
            }
        }
    }

    private fun updateErrorAndButtonStateForField() {
        _uiState.update { currentState ->
            val isDataValid = loginValidationUseCase.isDataValid(
                currentState.email, currentState.password,
                currentState.emailErrorMessage, currentState.passwordErrorMessage
            )
            currentState.copy(
                isError = !isDataValid,
                isButtonEnabled = loginValidationUseCase.isButtonEnabled(
                    currentState.email,
                    currentState.password,
                    isDataValid
                )
            )
        }
    }


    private fun onEmailChanged(newEmail: String) {
        val loginValidationResult = loginValidationUseCase.validateEmail(newEmail)

        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail,
                emailErrorMessage = loginValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }

    private fun onPasswordChanged(newPassword: String) {
        val passwordValidationResult = loginValidationUseCase.validatePassword(newPassword)
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                passwordErrorMessage = passwordValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }

    fun onButtonPressed(navHostController: NavHostController) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val user = _uiState.value.toUserLoginModel()
                val response = loginUserUseCase.execute(user)
                val token = response.body()?.token
                token?.let { tokenUseCase.setTokenToLocalStorage(it) }
                if (token != null) {
                    val result = profileUseCase.getProfile(token)
                    val profile = result.body()
                    if (result.isSuccessful && profile != null) {
                        profileUseCase.setProfileToLocalStorage(profile)
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            isButtonPressed = true
                        )
                    }
                } else {
                    handleException()
                }
            } catch (e: Exception) {
                handleException()
            }

            navHostController.navigate(Screen.RequestsScreen.name) {
                navHostController.popBackStack()
            }
        }


    }


    private fun handleException() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(
                    isError = true, isButtonEnabled = false, isMainErrorShown = true
                )
            }
        }
    }

}