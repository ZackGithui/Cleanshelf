package com.example.cleanshelf.presentation.authentication.firebaseAuth

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value =
                AuthState.Error("Email or password cannot be empty")
            return
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authState.value = AuthState.Authenticated
            } else _authState.value =
                AuthState.Error(task.exception?.message ?: "Something went wrong")

        }
    }

    fun signUp(name: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                _authState.value = AuthState.Authenticated
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


}


sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val error: String) : AuthState()


}