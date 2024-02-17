package com.ibra.keytrackerapp.common.validation.domain.usecase

import com.ibra.keytrackerapp.common.validation.domain.model.ValidationResult
import javax.inject.Inject

class LoginValidationUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {
    fun validateEmail(email: String) : ValidationResult {
        return validateEmailUseCase.execute(email)
    }

    fun validatePassword(password: String) : ValidationResult {
        return validatePasswordUseCase.execute(password)
    }

    fun isButtonEnabled(email: String, password: String, isPageValid: Boolean): Boolean {
        return email.isNotBlank() && password.isNotBlank() && isPageValid
    }
}