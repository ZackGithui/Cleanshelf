package com.example.cleanshelf.presentation.authentication.signUp

data class SignUpState(
    val confirmPassword: String = "",
    val email: String = "",
    val password: String = "",
    var viewPassword: Boolean = false,
    val confirmPasswordErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)
