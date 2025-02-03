package com.example.cleanshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotViewModel
import com.example.cleanshelf.presentation.authentication.signIn.Login
import com.example.cleanshelf.presentation.authentication.signIn.SignInViewModel
import com.example.cleanshelf.presentation.authentication.signUp.SignUp
import com.example.cleanshelf.presentation.authentication.signUp.SignUpViewModel
import com.example.cleanshelf.presentation.homeScreen.HomeScreen
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.example.cleanshelf.ui.theme.CleanshelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanshelfTheme {
                App()

            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.SignIn.route) {
        composable(AppScreens.SignIn.route) {
            Login(viewModel = viewModel<SignInViewModel>(), navController = navController)

        }
        composable(AppScreens.SignUp.route) {
            SignUp(
                viewModel = viewModel<SignUpViewModel>(),
                signUpState = viewModel<SignUpViewModel>().signUpState.collectAsStateWithLifecycle(),
                navController = navController
            )
        }
        composable(AppScreens.ForgetPassword.route) {
            ForgotPasswordScreen(viewModel = viewModel<ForgotViewModel>(), navController = navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen()

        }
    }

}
