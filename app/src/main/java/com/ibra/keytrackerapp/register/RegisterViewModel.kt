package com.ibra.keytrackerapp.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.auth.data.enum.FieldType
import com.ibra.keytrackerapp.common.auth.data.mapper.toUserRegisterModel
import com.ibra.keytrackerapp.common.auth.domain.usecase.RegisterUserUseCase
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.model.TokenResponse
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.common.validation.domain.usecase.RegistrationValidationUseCase
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
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registrationValidationUseCase: RegistrationValidationUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val tokenUseCase: TokenUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()


    @OptIn(FlowPreview::class)
    fun onFieldChanged(type: FieldType, newValue: Any) {
        viewModelScope.launch {
            val valueFlow = flowOf(newValue as String)
                .debounce(300)
                .distinctUntilChanged()
            when (type) {
                FieldType.Name -> {
                    valueFlow.collect { newName ->
                        onNameChanged(newName)
                    }
                }
                FieldType.Surname -> {
                    valueFlow.collect { newSurname ->
                        onSurnameChanged(newSurname)
                    }
                }
                FieldType.Email -> {
                    valueFlow.collect { newEmail ->
                        onEmailChanged(newEmail)
                    }
                }

                FieldType.Password -> {
                    valueFlow.collect { newPassword ->
                        onPasswordChanged(newPassword)
                    }
                }

            }
            updateErrorAndButtonStateForField()
        }
    }



    fun onButtonPressed() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val user = _uiState.value.toUserRegisterModel()
                val response = registerUserUseCase.execute(user)
                handleRegistrationResponse(response)
            } catch (e: Exception) {
                handleException()
            }
        }
    }

    private suspend fun handleRegistrationResponse(response: Response<TokenResponse>) {
        if (response.isSuccessful) {
            val token = response.body()?.token
            token?.let { tokenUseCase.setTokenToLocalStorage(it) }
            token?.let { getProfileAndSave(it) }
            _uiState.update { currentState ->
                currentState.copy(
                    isButtonPressed = true
                )
            }
        } else {
            handleException()
        }
    }

    private suspend fun getProfileAndSave(token: String) {
        val result = profileUseCase.getProfile(token)
        if (result.isSuccessful) {
            val profile = result.body()
            profile?.let { profileUseCase.setProfileToLocalStorage(it) }
        }
    }


    private fun onNameChanged(newName: String) {
        val nameValidationResult = registrationValidationUseCase.validateName(newName)

        _uiState.update { currentState ->
            currentState.copy(
                name = newName,
                nameErrorMessage = nameValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }
    private fun onSurnameChanged(newSurname: String) {
        val surnameValidationResult = registrationValidationUseCase.validateSurname(newSurname)

        _uiState.update { currentState ->
            currentState.copy(
                surname = newSurname,
                surnameErrorMessage = surnameValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }

    private fun onEmailChanged(newEmail: String) {
        val loginValidationResult = registrationValidationUseCase.validateEmail(newEmail)

        _uiState.update { currentState ->
            currentState.copy(
                email = newEmail,
                emailErrorMessage = loginValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }


    private fun onPasswordChanged(newPassword: String) {
        val passwordValidationResult = registrationValidationUseCase.validatePassword(newPassword)
        _uiState.update { currentState ->
            currentState.copy(
                password = newPassword,
                passwordErrorMessage = passwordValidationResult.errorMessage,
                isMainErrorShown = false
            )
        }
    }



    private fun updateErrorAndButtonStateForField() {
        _uiState.update { currentState ->
            val isDataValid = registrationValidationUseCase.isDataValid(
                currentState.name, currentState.surname,
                currentState.email, currentState.password,
                currentState.nameErrorMessage, currentState.surnameErrorMessage,
                currentState.emailErrorMessage, currentState.passwordErrorMessage
            )
            currentState.copy(
                isError = !isDataValid,
                isButtonEnabled = registrationValidationUseCase.isButtonEnabled(
                    currentState.name,
                    currentState.surname,
                    currentState.email,
                    currentState.password,
                    isDataValid
                )
            )
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