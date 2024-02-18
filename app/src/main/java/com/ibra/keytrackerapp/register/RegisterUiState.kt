package com.ibra.keytrackerapp.register

data class RegisterUiState(
    val name: String = "",
    val nameErrorMessage: Int? = null,
    val surname: String = "",
    val surnameErrorMessage: Int? = null,
    val email: String = "",
    val emailErrorMessage: Int? = null,
    val password: String = "",
    val passwordErrorMessage: Int? = null,
    val isError: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isButtonPressed: Boolean = false,
    val isMainErrorShown: Boolean = false
)
