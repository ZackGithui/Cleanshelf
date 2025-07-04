package com.example.cleanshelf.presentation.cart.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.data.local.ProductEntity
import com.example.cleanshelf.data.remote.Dto.CartItem
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.homeScreen.components.ImageHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    // Initialize only once from cartItem
    var quantity by remember(cartItem.id) { mutableIntStateOf(cartItem.quantity) }

    Box(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                ImageHolder(
                    image = cartItem.image ?: "",
                    onClick = {},
                    modifier = Modifier.width(100.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier = Modifier.width(100.dp)) {
                    Text(
                        text = cartItem.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Price: ${cartItem.price}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Qty: $quantity",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    IconButton(onClick = {
                        if (quantity > 1) {
                            quantity--
                            cartViewModel.addToCart(cartItem.copy(quantity = quantity))
                        }
                    }) {
                        Text(text = "-")
                    }

                    Text(text = quantity.toString())

                    IconButton(onClick = {
                        quantity++
                        cartViewModel.addToCart(cartItem.copy(quantity = quantity))
                    }) {
                        Text(text = "+")
                    }

                    Button(onClick = {
                        cartViewModel.removeItemFromCart(cartItem)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.height(30.dp)
                        )
                    }
                }

        }
    }
}
