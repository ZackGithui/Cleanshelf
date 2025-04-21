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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfLabelButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfPasswordTextField
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField
import com.example.cleanshelf.saveOnboardingStatus
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel,
    navController: NavController,

) {
    val context = LocalContext.current
    val signState: SignInState = viewModel.signInState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {


    }
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
            onValueChange = { viewModel.uiEvents(SignInEvents.EmailChanged(it), navController) },
            placeholder = "Email",
            errorMessage = signState.emailError
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfPasswordTextField(
            value = signState.password,
            onValueChange = { viewModel.uiEvents(SignInEvents.PasswordChanged(it),navController) },
            placeholder = "Password",
            onTrailingIconClicked = { viewModel.uiEvents(SignInEvents.ViewPassword,navController)},
            isPasswordVisible = signState.viewPassword,
            errorMessage = signState.emailError

        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfLabelButton(
            modifier = modifier.fillMaxWidth(),
            unClickableText = "",
            clickableText = "Forgot password?",
            onClick = { viewModel.uiEvents(SignInEvents.ForgotLabelClicked,navController) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfButton(
            modifier = modifier.fillMaxWidth(),
            title = "Sign In",
            onClick = { viewModel.uiEvents(SignInEvents.SignInButtonClicked,navController)
            GlobalScope.launch{
                saveOnboardingStatus(context,completed = true)
            }}
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfLabelButton(modifier = modifier.fillMaxWidth(),
            unClickableText = "Don't have an account?",
            clickableText = "Sign Up",
            onClick = { viewModel.uiEvents(SignInEvents.SignUpLabelClicked,navController) })


    }

}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignInPrev() {
    val navController = rememberNavController()
    Login(
        viewModel = viewModel<SignInViewModel>(),
        navController = navController

    )

}