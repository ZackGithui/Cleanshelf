package com.example.cleanshelf.presentation.authentication.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignInViewModel() : ViewModel() {


    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInState get() = _signInState.asStateFlow()
    val auth = AuthViewModel()

    fun uiEvents(events: SignInEvents, navController: NavController) {
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

            SignInEvents.SignInButtonClicked -> {
                val current = _signInState.value
                val emailError = if (!current.email.contains("@")) "Valid email required" else null
                val passwordError = when {
                    current.password.isBlank() -> "Password is required"
                    current.password.length < 6 -> "Password must be at least six characters"
                    else -> null
                }
                if (emailError != null || passwordError != null) {
                    _signInState.value = _signInState.value.copy(
                        emailError = emailError, passwordError = passwordError
                    )
                } else {
                    viewModelScope.launch {
                        auth.login(
                            _signInState.value.email, _signInState.value.password,
                        )
                            .onSuccess {
                                uiEvents(SignInEvents.OnSuccess, navController)
                            }
                            .onFailure { err ->
                                SignInEvents.OnError(err.message ?: "Login failed")
                            }
                    }


                    //authViewModel.login(_signInState.value.email,_signInState.value.password)
                }
            }

            SignInEvents.SignUpLabelClicked -> {
                navController.navigate(AppScreens.SignUp.route)
            }

            SignInEvents.ViewPassword -> {
                _signInState.update {
                    it.copy(viewPassword = !it.viewPassword)
                }
            }

            is SignInEvents.OnError -> {
                _signInState.update { it.copy(passwordError = events.message) }
            }

            SignInEvents.OnSuccess -> {
                navController.navigate(AppScreens.CartScreen.route) {
                    popUpTo(AppScreens.SignIn.route) { inclusive = true }

                }
            }
        }
    }
}