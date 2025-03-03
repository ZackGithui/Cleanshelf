package com.example.cleanshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotViewModel
import com.example.cleanshelf.presentation.authentication.signIn.Login
import com.example.cleanshelf.presentation.authentication.signIn.SignInViewModel
import com.example.cleanshelf.presentation.authentication.signUp.SignUp
import com.example.cleanshelf.presentation.authentication.signUp.SignUpViewModel
import com.example.cleanshelf.presentation.detailScreen.DetailScreen
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

                val navController = rememberNavController()
                App(navController)


            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route) {
        composable(AppScreens.HomeScreen.route) {
           HomeScreen(navController = navController)

        }
        composable(AppScreens.SignUp.route) {
            SignUp(
                viewModel = viewModel<SignUpViewModel>(),
                signUpState = viewModel<SignUpViewModel>().signUpState.collectAsStateWithLifecycle(),
                navController = navController
            )
        }
        composable(AppScreens.ForgetPassword.route) {
            ForgotPasswordScreen(
                viewModel = viewModel<ForgotViewModel>(),
                navController = navController
            )
        }
       /* composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController)

        }*/
        composable(
            route = AppScreens.DetailScreen.route,
            arguments = listOf(navArgument("id"){type = NavType.IntType})
        ) {backStackEntry->
            val productId= backStackEntry.arguments?.getInt("id")
            DetailScreen(
                productId = productId ?: 1,

            )
        }
    }

}
