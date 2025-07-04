package com.example.cleanshelf.presentation.checkOut

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.cart.OrderViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    viewModel: CheckOutViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavController,
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle().value
    val checkOutState = viewModel._checkOutState.collectAsStateWithLifecycle().value
    val paymentResponse by viewModel.paymentResponse.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            navController.navigate(AppScreens.SignIn.route) {
                popUpTo(AppScreens.CheckOutScreen.route) { inclusive = true }
            }
        } else {
            val token = currentUser.getIdToken(true).await().token
            if (token.isNullOrBlank()) {

                navController.navigate(AppScreens.SignIn.route) {
                    popUpTo(AppScreens.CheckOutScreen.route) { inclusive = true }
                }
            } else {
                //Token is valid, proceed to load cart
                cartViewModel.getCartFromServer()
            }
        }
    }

    // Calculate total price from cart products
    val totalAmount = cartState.totalPrice

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "CheckOut",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        ) {
            CleanShelfTextField(
                value = checkOutState.phoneNumber,
                onValueChange = { viewModel.onEvents(CheckOutEvents.phone(it)) },
                placeholder = "254712345678",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)

            )

            CleanShelfButton(
                onClick = {
                    viewModel.makePayment(checkOutState.phoneNumber, totalAmount)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                title = "Pay Now"
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total $totalAmount",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        // After payment response success:
        LaunchedEffect(paymentResponse) {
            if (paymentResponse?.responseCode == 0) {
                // Payment successful, now place order
                orderViewModel.placeOrder { result ->
                    result.onSuccess { message ->
                        Log.d("order", message.toString())
                        Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show()
                    } // Call place order API here
                }
            }

            // After order placement success:

        }
    }
}
