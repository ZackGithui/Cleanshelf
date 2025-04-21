package com.example.cleanshelf.presentation.authentication.forgotPassword

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField
import com.example.cleanshelf.presentation.navigation.AppScreens

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ForgotViewModel,
    navController: NavController
) {
    val forgotState = viewModel.forgotState.collectAsStateWithLifecycle()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))

        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 25.sp),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfTextField(
            value = forgotState.value.email,
            onValueChange = { viewModel.forgotUiEvents(ForgotEvents.EmailChanged(it),navController) },
            placeholder = "Email",
            errorMessage = forgotState.value.errorMessage
        )
        Spacer(modifier = Modifier.height(20.dp))
        CleanShelfButton(
            modifier = modifier.fillMaxWidth(),
            title = "Reset Password",
            onClick = { viewModel.forgotUiEvents(ForgotEvents.ForgotButtonClicked,navController) }
        )


    }

}

@Preview
@Composable
private fun ForgotPassword1() {
    val navController = rememberNavController()
    ForgotPasswordScreen(viewModel = viewModel<ForgotViewModel>(), navController = navController)

}