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

    fun isDataValid(
        name: String,
        surname: String,
        email: String,
        password: String,
        nameErrorMessage: Int?,
        surnameErrorMessage: Int?,
        emailErrorMessage: Int?,
        passwordErrorMessage: Int?
    ): Boolean {
        if (name.isBlank() && surname.isBlank() && email.isBlank() && password.isBlank()) {
            return true
        }
        return nameErrorMessage == null
                && surnameErrorMessage == null
                && emailErrorMessage == null
                && passwordErrorMessage == null
    }


    fun isButtonEnabled(
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