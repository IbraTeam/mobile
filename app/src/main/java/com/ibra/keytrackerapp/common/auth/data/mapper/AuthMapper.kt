package com.ibra.keytrackerapp.common.auth.data.mapper

import com.ibra.keytrackerapp.common.auth.domain.model.UserLoginModel
import com.ibra.keytrackerapp.login.LoginUiState

fun LoginUiState.toUserLoginModel(): UserLoginModel {
    return UserLoginModel(
        email = this.email,
        password = this.password
    )
}