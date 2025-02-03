package com.example.cleanshelf.presentation.authentication.signIn

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class SignInViewModel()  : ViewModel() {


    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInState get() = _signInState.asStateFlow()
    val auth = AuthViewModel()

    fun uiEvents(events: SignInEvents,navController: NavController) {
        when (events) {
            is SignInEvents.EmailChanged -> {
                _signInState.update {
                    it.copy(email = events.email)
                }
            }

            SignInEvents.ForgotLabelClicked -> {
                navController.navigate(AppScreens.ForgetPassword.route)


            }

            is SignInEvents.PasswordChanged -> {
                _signInState.update { it.copy(password = events.password) }
            }

            SignInEvents.SignInButtonClicked -> {auth.login(_signInState.value.email,_signInState.value.password,navController)
                //authViewModel.login(_signInState.value.email,_signInState.value.password)
                }
            SignInEvents.SignUpLabelClicked -> {navController.navigate(AppScreens.SignUp.route)}
            SignInEvents.ViewPassword -> {
                _signInState.update {
                    it.copy(viewPassword = !it.viewPassword)
                }
            }
        }
    }
}