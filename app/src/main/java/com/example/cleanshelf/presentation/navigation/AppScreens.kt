package com.example.cleanshelf.presentation.navigation

sealed class AppScreens(val route: String) {
    data object SignUp : AppScreens("SignUp")
    data object SignIn : AppScreens("SignIn")
    data object ForgetPassword : AppScreens("ForgotPassword")
    data object HomeScreen : AppScreens("HomeScreen")
    data object DetailScreen : AppScreens("DetailScreen/{id}") {
        fun createRoute(id: Int) = "DetailScreen/$id"
    }
    data object CartScreen : AppScreens("CartScreen")


}