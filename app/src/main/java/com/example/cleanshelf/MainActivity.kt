package com.example.cleanshelf

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotViewModel
import com.example.cleanshelf.presentation.authentication.signIn.Login
import com.example.cleanshelf.presentation.authentication.signIn.SignInViewModel
import com.example.cleanshelf.presentation.authentication.signUp.SignUp
import com.example.cleanshelf.presentation.authentication.signUp.SignUpViewModel
import com.example.cleanshelf.presentation.bookMarks.BookMarkScreen
import com.example.cleanshelf.presentation.cart.CartScreen
import com.example.cleanshelf.presentation.detailScreen.DetailScreen
import com.example.cleanshelf.presentation.homeScreen.HomeScreen
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.example.cleanshelf.presentation.navigation.BottomNavigation
import com.example.cleanshelf.presentation.search.SearchScreen
import com.example.cleanshelf.ui.theme.CleanshelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fetchFirebaseToken()
        installSplashScreen()
        setContent {
            CleanshelfTheme {
                val navController = rememberNavController()
                val currentRoute = navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry).value?.destination?.route

                val hideBottomBarRoutes = listOf(
                    AppScreens.SignIn.route,
                    AppScreens.SignUp.route,
                    AppScreens.ForgetPassword.route
                )
                Scaffold(
                    bottomBar = { if( currentRoute !in hideBottomBarRoutes)
                        BottomNavigation(navController = navController) },

                    ) { it ->
                    App(navController = navController, modifier = Modifier.padding(it))
                }


            }
        }
    }
}

@Composable
fun App(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current
    val onBoardingStatusFlow = remember { readOnboardingStatus(context) }
    val onBoardingCompleted by onBoardingStatusFlow.collectAsState(initial = false)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        NavHost(
            navController = navController,
            startDestination = if (onBoardingCompleted) AppScreens.HomeScreen.route else AppScreens.SignIn.route
        ) {
            composable(AppScreens.SignIn.route) {
                Login(
                    viewModel = viewModel<SignInViewModel>(),
                    navController = navController
                )


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
            composable(AppScreens.HomeScreen.route) {
                HomeScreen(navController = navController)


            }
            composable(
                route = AppScreens.DetailScreen.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("id")
                DetailScreen(
                    productId = productId ?: 1,
                    navController = navController

                )
            }
            composable(AppScreens.CartScreen.route) {
                CartScreen()
            }
            composable(AppScreens.SearchScreen.route) {
                SearchScreen(navController = navController)
            }
            composable(AppScreens.BookMarks.route) {
                BookMarkScreen()
            }
        }
    }
}
