package com.example.cleanshelf.presentation.authentication.signUp


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
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfLabelButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfPasswordTextField
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField


@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    signUpState: State<SignUpState>,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Create an Account",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfTextField(
            value = signUpState.value.username,
            onValueChange = { viewModel.uiEvents(SignUpEvents.UserNameChanged(it), navController) },
            placeholder = "Username",

            )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfTextField(
            value = signUpState.value.email,
            onValueChange = {
                viewModel.uiEvents(
                    SignUpEvents.UserEmailChanged(it),
                    navController
                )
            },
            placeholder = "Email"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfPasswordTextField(
            value = signUpState.value.password,
            onValueChange = {
                viewModel.uiEvents(
                    SignUpEvents.UserPasswordChanged(it),
                    navController
                )
            },
            onTrailingIconClicked = {
                viewModel.uiEvents(
                    SignUpEvents.VisibilityToggled,
                    navController
                )
            },
            placeholder = "Password",
            isPasswordVisible = signUpState.value.viewPassword
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfLabelButton(
            modifier = Modifier.fillMaxWidth(),
            unClickableText = "Already have an account?",
            clickableText = "LogIn",
            onClick = { viewModel.uiEvents(SignUpEvents.SignInLabelClicked, navController) }
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfButton(
            modifier = modifier
                .fillMaxWidth(),
            title = "Sign Up",
            onClick = { viewModel.uiEvents(SignUpEvents.SignUpButtonClicked, navController) }

        )


    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPrev() {
    val navController = rememberNavController()
    SignUp(
        viewModel = viewModel<SignUpViewModel>(),
        signUpState = viewModel<SignUpViewModel>().signUpState.collectAsStateWithLifecycle(),
        modifier = Modifier,
        navController = navController
    )


}

@Preview(
    showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFFFFFFFF
)
@Composable
private fun SignUpScreenPrev2() {
    val navController = rememberNavController()
    SignUp(
        viewModel = viewModel<SignUpViewModel>(),
        signUpState = viewModel<SignUpViewModel>().signUpState.collectAsStateWithLifecycle(),
        modifier = Modifier,
        navController = navController
    )


}