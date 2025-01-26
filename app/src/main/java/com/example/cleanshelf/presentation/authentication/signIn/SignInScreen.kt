package com.example.cleanshelf.presentation.authentication.signIn

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfLabelButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfPasswordTextField
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField

@Composable
fun Login(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    signInState: SignInState
) {
    val signState: SignInState = viewModel.signInState.collectAsStateWithLifecycle().value
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign In",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfTextField(
            value = signState.email,
            onValueChange = { viewModel.uiEvents(SignInEvents.EmailChanged(it)) },
            placeholder = "Email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfPasswordTextField(
            value = signState.password,
            onValueChange = { viewModel.uiEvents(SignInEvents.PasswordChanged(it)) },
            placeholder = "Password",
            onTrailingIconClicked = { viewModel.uiEvents(SignInEvents.ViewPassword)},
            isPasswordVisible = signState.viewPassword

        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfLabelButton(
            modifier = modifier.fillMaxWidth(),
            unClickableText = "",
            clickableText = "Forgot password?",
            onClick = { viewModel.uiEvents(SignInEvents.ForgotLabelClicked) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfButton(
            modifier = modifier.fillMaxWidth(),
            title = "Sign In",
            onClick = { viewModel.uiEvents(SignInEvents.SignInButtonClicked) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfLabelButton(modifier = modifier.fillMaxWidth(),
            unClickableText = "Don't have an account?",
            clickableText = "Sign Up",
            onClick = { viewModel.uiEvents(SignInEvents.SignUpLabelClicked) })


    }

}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignInPrev() {
    Login(
        viewModel = viewModel<SignInViewModel>(),
        signInState = viewModel<SignInViewModel>().signInState.collectAsStateWithLifecycle().value
    )

}