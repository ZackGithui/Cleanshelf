package com.example.cleanshelf.presentation.authentication.signUp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class SignUpViewModel : ViewModel() {
    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun uiEvents(events: SignUpEvents) {

        when (events) {
            SignUpEvents.SignInLabelClicked -> {}
            SignUpEvents.SignUpButtonClicked -> {}
            is SignUpEvents.UserEmailChanged -> {
                _signUpState.update {
                    it.copy(email = events.email)
                }
            }

            is SignUpEvents.UserNameChanged -> {
                _signUpState.update {
                    it.copy(username = events.name)
                }
            }

            is SignUpEvents.UserPasswordChanged -> {
               _signUpState.update {
                   it.copy(password = events.password)
               }
            }

            SignUpEvents.VisibilityToggled -> {
                _signUpState.update {
                    it.copy(
                        viewPassword = !_signUpState.value.viewPassword
                    )
                }
            }
        }

    }

}