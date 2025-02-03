package com.example.cleanshelf.presentation.authentication.firebaseAuth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState: MutableLiveData<AuthState> = MutableLiveData<AuthState>()
    val authState = _authState


    init {
        checkStatus()
    }


    private fun checkStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else _authState.value = AuthState.Authenticated
    }

    fun login(email: String, password: String, navController: NavController) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value =
                AuthState.Error("Email or password cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authState.value = AuthState.Authenticated
                navController.navigate(AppScreens.HomeScreen.route) {
                    popUpTo(AppScreens.SignIn.route) {
                        inclusive = true
                    }
                }
            } else _authState.value =
                AuthState.Error(task.exception?.message ?: "Something went wrong")

        }
    }

    fun signUp(name: String, email: String, password: String, navController: NavController) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                _authState.value = AuthState.Authenticated

                navController.navigate(AppScreens.HomeScreen.route) {
                    popUpTo(AppScreens.SignUp.route) {
                        inclusive = true
                    }
                }
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name).build()

                user?.updateProfile(profileUpdate)?.addOnCompleteListener { profileTask ->
                    if (profileTask.isSuccessful) {
                        Log.d(TAG, "User name successfully added $user")
                    } else Log.d(TAG, "Error add the name of the user")

                }
            } else _authState.value =
                AuthState.Error(task.exception?.message ?: "Something went wrong")


        }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun resetPassword(email: String, navController: NavController) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate(AppScreens.SignIn.route)
            }


        }
    }


}


sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val error: String) : AuthState()


}