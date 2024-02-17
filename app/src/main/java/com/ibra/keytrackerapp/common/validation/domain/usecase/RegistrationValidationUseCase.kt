package com.ibra.keytrackerapp.common.validation.domain.usecase

import com.ibra.keytrackerapp.common.validation.domain.model.ValidationResult
import com.ibra.keytrackerapp.common.validation.domain.util.ErrorMessageType
import javax.inject.Inject

class RegistrationValidationUseCase @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {
    fun validateName(name: String): ValidationResult {
        return validateNameOrSurname(name, true)
    }

    fun validateSurname(surname: String): ValidationResult {
        return validateNameOrSurname(surname, false)
    }


    fun validateEmail(email: String): ValidationResult {
        return validateEmailUseCase.execute(email)
    }

    fun validatePassword(password: String): ValidationResult {
        return validatePasswordUseCase.execute(password)
    }

    fun validateFirstPage(
        name: String,
        surname: String,
        email: String,
        nameErrorMessage: Int?,
        loginErrorMessage: Int?,
        emailErrorMessage: Int?,
    ): Boolean {
        if (name.isBlank() && surname.isBlank() && email.isBlank() && email.isBlank()) {
            return true
        }
        return nameErrorMessage == null
                && loginErrorMessage == null
                && emailErrorMessage == null
    }


    fun isButtonEnabledForPage(
        name: String,
        surname: String,
        email: String,
        password: String,
        isPageValid: Boolean
    ): Boolean {
        return name.isNotBlank()
                && surname.isNotBlank()
                && email.isNotBlank()
                && password.isNotBlank()
                && isPageValid
    }


    private fun validateNameOrSurname(value: String, isName: Boolean): ValidationResult {
        return if (value.length < 2) {
            ValidationResult(
                true,
                if (isName) ErrorMessageType.SHORT_NAME else ErrorMessageType.SHORT_SURNAME
            )
        } else if (!value.all { it.isLetter() }) {
            ValidationResult(
                true,
                if (isName) ErrorMessageType.INCORRECT_NAME else ErrorMessageType.INCORRECT_SURNAME
            )
        } else {
            ValidationResult(false)
        }
    }

}