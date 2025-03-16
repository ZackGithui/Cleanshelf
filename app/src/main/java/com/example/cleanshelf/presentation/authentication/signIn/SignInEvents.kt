package com.example.cleanshelf.presentation.authentication.signIn

sealed class  SignInEvents {
    data class EmailChanged(val email:String):SignInEvents()
    data class PasswordChanged(val password:String):SignInEvents()
    data object ViewPassword:SignInEvents()
    data object SignUpLabelClicked:SignInEvents()
    data object SignInButtonClicked:SignInEvents()
    data object ForgotLabelClicked:SignInEvents()

}