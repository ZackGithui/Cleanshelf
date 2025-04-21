package com.example.cleanshelf.presentation.authentication.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotViewModel : ViewModel() {
    private val _forgotState: MutableStateFlow<ForgotState> = MutableStateFlow(ForgotState())
    val forgotState = _forgotState.asStateFlow()
    val auth = AuthViewModel()


    fun forgotUiEvents(uiEvents: ForgotEvents, navController: NavController) {
        when (uiEvents) {
            is ForgotEvents.EmailChanged -> {
                _forgotState.update {
                    it.copy(
                        email = uiEvents.email
                    )
                }
            }

            ForgotEvents.ForgotButtonClicked -> {

                val current = _forgotState.value
                val emailError = when {
                    current.email.isBlank() -> "Email cannot be blank"
                    !current.email.contains("@") -> "Enter a valid email"
                    else -> null
                }
                if (emailError != null) {
                    _forgotState.value = _forgotState.value.copy(
                        errorMessage = emailError
                    )
                } else {

                    auth.resetPassword(_forgotState.value.email, navController)
                }


            }
        }
    }
}