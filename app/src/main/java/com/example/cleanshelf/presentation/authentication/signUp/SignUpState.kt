package com.example.cleanshelf.presentation.authentication.signUp

data class SignUpState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    var viewPassword: Boolean = false,
    val usernameErrorMessage: String? = null,
    val emailErrorMessage: String? = null,
    val passwordErrorMessage: String? = null
)
