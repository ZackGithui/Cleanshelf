package com.example.cleanshelf.presentation.checkOut

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens


@Composable
fun CheckOutScreen(viewModel: CheckOutViewModel = hiltViewModel()) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle()
    var phoneNumber by remember { mutableStateOf("") }
    var amount =cartState.value.totalPrice
    val paymentResponse by viewModel.paymentResponse.collectAsState()

    LaunchedEffect(AppScreens.CheckOutScreen.route) {
        cartViewModel.getCartProducts()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp)
        )



        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.makePayment( phoneNumber,amount)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pay Now")
        }

        Spacer(modifier = Modifier.height(20.dp))

        paymentResponse?.let {
            //Text(text = "Response: ${it.customerMessage}")
        }
    }
}
