package com.example.cleanshelf.presentation.cart

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.cart.components.CartItem
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current

    val orderViewModel : OrderViewModel = hiltViewModel()
    val auth = FirebaseAuth.getInstance()
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        cartViewModel.getCartFromServer()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .padding(top = 20.dp)
        ) {
            val name = auth.currentUser?.email

            if (name != null) {
                Text(
                    text = "$name's cart",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Divider()

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 10.dp)
        ) {

            items(cartState.products ?: emptyList()) { cartItem ->
                Spacer(modifier = Modifier.height(5.dp))
                CartItem(cartItem = cartItem)  // Pass CartItem (not ProductEntity)
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Total Price: Ksh. ${cartState.totalPrice}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                CleanShelfButton(
                    modifier = modifier.fillMaxWidth(),
                    title = "CheckOut",
                    onClick = {

                                navController.navigate(AppScreens.CheckOutScreen.route)

                    }
                )
            }
        }
    }
}
