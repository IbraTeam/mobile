package com.ibra.keytrackerapp.common.validation.domain.usecase

import com.ibra.keytrackerapp.common.validation.domain.model.ValidationResult
import com.ibra.keytrackerapp.common.validation.domain.util.ErrorMessageType

class ValidatePasswordUseCase {
    fun execute(value: String): ValidationResult {
        return if (value.isBlank()) {
            ValidationResult(true, ErrorMessageType.BLANK_PASSWORD)
        } else if (value.length < 6 || value.length > 20) {
            ValidationResult(true, ErrorMessageType.WRONG_LENGTH_PASSWORD)
        } else if (value.contains(" ")) {
            ValidationResult(true, ErrorMessageType.WHITESPACE_PASSWORD)
        }  else if (!value.contains(Regex(".*\\d.*"))) {
            ValidationResult(true, ErrorMessageType.NO_DIGIT_PASSWORD)
        } else if (!value.contains(Regex(".*\\d.*"))) {
            ValidationResult(true, ErrorMessageType.NO_DIGIT_PASSWORD)
        } else
            ValidationResult(false, null)
    }
}