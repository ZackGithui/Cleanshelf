package com.example.cleanshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.example.cleanshelf.presentation.authentication.forgotPassword.ForgotViewModel
import com.example.cleanshelf.presentation.authentication.signIn.SignInViewModel
import com.example.cleanshelf.presentation.authentication.signUp.SignUp
import com.example.cleanshelf.presentation.authentication.signUp.SignUpViewModel

import com.example.cleanshelf.ui.theme.CleanshelfTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanshelfTheme {
              SignUp(viewModel = viewModel<SignUpViewModel>(), signUpState = viewModel<SignUpViewModel>().signUpState.collectAsStateWithLifecycle(),)

            }
        }
    }
}

