package com.example.cleanshelf.presentation.authentication.forgotPassword

data class ForgotState(
    val email: String = "",
    val isEmailSent:Boolean = false,
    val errorMessage:String ?= null
)
