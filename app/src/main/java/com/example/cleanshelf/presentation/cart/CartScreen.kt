package com.example.cleanshelf.presentation.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.cart.components.CartItem
import com.example.cleanshelf.presentation.navigation.AppScreens

@Composable
fun CartScreen(modifier: Modifier = Modifier) {

    val cartViewModel : CartViewModel = hiltViewModel()
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle().value
    LaunchedEffect(AppScreens.CartScreen.route) {
        cartViewModel.getCartProducts()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp, bottom = 40.dp)
    ) {

        items(cartState.products ?: emptyList()) { product->
            CartItem(productEntity = product)


        }

        item {
            CleanShelfButton(modifier = modifier.fillMaxWidth(), title = "CheckOut", onClick = {})
        }

    }






}