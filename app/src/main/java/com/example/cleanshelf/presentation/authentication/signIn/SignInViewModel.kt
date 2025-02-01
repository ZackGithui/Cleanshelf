package com.example.cleanshelf.presentation.authentication.signIn

import androidx.lifecycle.ViewModel
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class SignInViewModel  : ViewModel() {

    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInState get() = _signInState.asStateFlow()
    val auth = AuthViewModel()

    fun uiEvents(events: SignInEvents) {
        when (events) {
            is SignInEvents.EmailChanged -> {
                _signInState.update {
                    it.copy(email = events.email)
                }
            }

            SignInEvents.ForgotLabelClicked -> {

            }

            is SignInEvents.PasswordChanged -> {
                _signInState.update { it.copy(password = events.password) }
            }

            SignInEvents.SignInButtonClicked -> {auth.login(_signInState.value.email,_signInState.value.password)
                //authViewModel.login(_signInState.value.email,_signInState.value.password)
                }
            SignInEvents.SignUpLabelClicked -> {}
            SignInEvents.ViewPassword -> {
                _signInState.update {
                    it.copy(viewPassword = !it.viewPassword)
                }
            }
        }
    }
}