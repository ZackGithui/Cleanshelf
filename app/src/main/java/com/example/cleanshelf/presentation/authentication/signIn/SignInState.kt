package com.example.cleanshelf.presentation.authentication.signIn

data class SignInState(
    val email: String = "",
    val password: String = "",
    var viewPassword: Boolean = false
)
