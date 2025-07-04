package com.example.cleanshelf.presentation.authentication.signUp

sealed class SignUpEvents {
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvents()
    data class UserEmailChanged(val email: String) : SignUpEvents()
    data class UserPasswordChanged(val password: String) : SignUpEvents()
    data object VisibilityToggled : SignUpEvents()
    data object  SignUpButtonClicked: SignUpEvents()
    data object SignInLabelClicked: SignUpEvents()
}