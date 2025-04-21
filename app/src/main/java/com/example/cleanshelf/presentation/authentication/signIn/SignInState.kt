package com.example.cleanshelf.presentation.authentication.signIn

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    var viewPassword: Boolean = false
)
