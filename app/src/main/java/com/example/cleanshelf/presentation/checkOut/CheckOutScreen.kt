package com.example.cleanshelf.presentation.checkOut

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.authentication.components.CleanShelfTextField
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens


@Composable
fun CheckOutScreen(viewModel: CheckOutViewModel = hiltViewModel()) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle()
    var phoneNumber by remember { mutableStateOf("") }
    val amount = cartState.value.totalPrice
    val paymentResponse by viewModel.paymentResponse.collectAsState()

    // Fetch cart products when the screen loads
    LaunchedEffect(AppScreens.CheckOutScreen.route) {
        cartViewModel.getCartProducts()
    }

    // Clear cart when payment is successful
    LaunchedEffect(paymentResponse) {
        if (paymentResponse?.responseCode == 0) {
            cartState.value.products?.forEach { product ->
                cartViewModel.removeProductFromCart(product)
            }
        }
    }

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
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = "254712345678",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            )

            CleanShelfButton(
                onClick = {
                    viewModel.makePayment(phoneNumber, amount)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                title = "Pay Now"
            )
            Spacer(modifier = Modifier.height(40.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        paymentResponse?.let {
            Text(
                text = "Response: ${it.customerMessage}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

