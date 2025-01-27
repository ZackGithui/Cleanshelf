package com.example.cleanshelf.presentation.authentication.forgotPassword

sealed class ForgotEvents {
    data class EmailChanged(val email: String) : ForgotEvents()
    data object ForgotButtonClicked : ForgotEvents()
}