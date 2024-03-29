package com.ibra.keytrackerapp.common.validation.domain.usecase

import com.ibra.keytrackerapp.common.validation.domain.model.ValidationResult
import com.ibra.keytrackerapp.common.validation.domain.util.ErrorMessageType

class ValidateEmailUseCase {
    private val emailRegex = Regex("^[\\dA-Za-z\\-_.]+@[\\dA-Za-z\\-]+\\.[\\dA-Za-z]+\$")
    fun execute(value: String): ValidationResult {
        return if (value.isBlank()){
            ValidationResult(true, ErrorMessageType.BLANK_EMAIL)
        } else if (!value.matches(emailRegex)){
            ValidationResult(true, ErrorMessageType.INCORRECT_EMAIL)
        } else {
            ValidationResult(false)
        }
    }
}