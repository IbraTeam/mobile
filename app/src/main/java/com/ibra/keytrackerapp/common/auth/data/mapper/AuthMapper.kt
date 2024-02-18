package com.ibra.keytrackerapp.common.auth.data.mapper

import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.common.auth.domain.model.UserRegisterModel
import com.ibra.keytrackerapp.login.LoginUiState
import com.ibra.keytrackerapp.register.RegisterUiState

fun LoginUiState.toUserLoginModel(): UserLoginModel {
    return UserLoginModel(
        email = this.email,
        password = this.password
    )
}

fun RegisterUiState.toUserRegisterModel(): UserRegisterModel {
    return UserRegisterModel(
        name = this.surname + " " + this.name,
        password = this.password,
        email = this.email
    )
}