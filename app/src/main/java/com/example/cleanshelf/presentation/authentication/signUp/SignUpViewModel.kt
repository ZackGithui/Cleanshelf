package com.example.cleanshelf.presentation.authentication.signUp

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.example.cleanshelf.saveOnboardingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch




class SignUpViewModel : ViewModel() {

    private val _signUpState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()
    private val auth = AuthViewModel()



    fun uiEvents(events: SignUpEvents, navController: NavController) {


        when (events) {
            SignUpEvents.SignInLabelClicked -> {
                navController.navigate(AppScreens.SignIn.route)
            }

            SignUpEvents.SignUpButtonClicked -> {

                auth.signUp(
                    _signUpState.value.username,
                    _signUpState.value.email,
                    _signUpState.value.password,
                    navController
                )

                //authViewModel.signUp(_signUpState.value.username,_signUpState.value.email,_signUpState.value.password)
            }

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