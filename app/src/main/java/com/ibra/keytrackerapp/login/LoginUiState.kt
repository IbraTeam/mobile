package com.ibra.keytrackerapp.login

data class LoginUiState(
    val email: String = "",
    val emailErrorMessage: Int? = null,
    val password: String = "",
    val passwordErrorMessage: Int? = null,
    val isError: Boolean = false,
    val isButtonEnabled: Boolean = false,
    val isButtonPressed: Boolean = false,
    val isMainErrorShown: Boolean = false
)
