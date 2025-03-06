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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.data.local.ProductEntity
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.homeScreen.components.ImageHolder

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    productEntity: ProductEntity,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartState = cartViewModel.cartState.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier
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

            Row(modifier = Modifier.fillMaxWidth(0.8f)) {
                Row {
                    ImageHolder(
                        image = productEntity.image, onClick = {}, modifier = Modifier
                            .width(100.dp)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))

                Row (modifier = Modifier.width(100.dp)

                ){
                    Column {
                        Text(
                            text = productEntity.name,
                            color = MaterialTheme.colorScheme.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = productEntity.price.toString(),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = productEntity.quantity.toString(),
                            color = MaterialTheme.colorScheme.onBackground
                        )


                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { productEntity.quantity - 1 }) {
                        Text(text = "-")

                    }
                    Text(text = productEntity.quantity.toString())
                    IconButton(onClick = { productEntity.quantity + 1 }) {
                        Text(text = "+")

                    }
                }
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(onClick = { cartViewModel.removeProductFromCart(productEntity) }) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "delete",
                        modifier = Modifier.height(40.dp)
                    )

                }
            }


        }

    }
    Spacer(modifier = Modifier.height(10.dp))
}