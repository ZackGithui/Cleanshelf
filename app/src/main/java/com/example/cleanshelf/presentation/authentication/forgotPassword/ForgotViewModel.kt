package com.example.cleanshelf.presentation.authentication.forgotPassword

import androidx.lifecycle.ViewModel
import com.example.cleanshelf.presentation.authentication.firebaseAuth.AuthViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ForgotViewModel  : ViewModel() {
    private val _forgotState: MutableStateFlow<ForgotState> = MutableStateFlow(ForgotState())
    val forgotState = _forgotState.asStateFlow()


    fun forgotUiEvents(uiEvents: ForgotEvents) {
        when (uiEvents) {
            is ForgotEvents.EmailChanged -> {
                _forgotState.update {
                    it.copy(
                        email = uiEvents.email
                    )
                }
            }

            ForgotEvents.ForgotButtonClicked -> {


            }
        }
    }
}