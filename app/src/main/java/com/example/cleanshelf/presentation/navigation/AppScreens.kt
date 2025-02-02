package com.example.cleanshelf.presentation.navigation

import androidx.navigation.Navigation

sealed class AppScreens(val route: String) {
    data object SignUp : AppScreens("SignUp")
    data object SignIn : AppScreens("SignIn")
    data object ForgetPassword : AppScreens("ForgotPassword")
    data object HomeScreen : AppScreens("HomeScreen")
}