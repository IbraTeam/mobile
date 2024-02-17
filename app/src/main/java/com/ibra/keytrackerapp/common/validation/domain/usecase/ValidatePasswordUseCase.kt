package com.ibra.keytrackerapp.common.validation.domain.usecase

import com.ibra.keytrackerapp.common.validation.domain.model.ValidationResult
import com.ibra.keytrackerapp.common.validation.domain.util.ErrorMessageType

class ValidatePasswordUseCase {
    fun execute(value: String): ValidationResult {
        return if (value.isBlank()){
            ValidationResult(true, ErrorMessageType.BLANK_PASSWORD)
        } else {
            ValidationResult(false)
        }
    }
}