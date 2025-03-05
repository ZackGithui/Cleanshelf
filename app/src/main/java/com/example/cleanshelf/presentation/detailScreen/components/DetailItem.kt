package com.example.cleanshelf.presentation.detailScreen.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleanshelf.data.local.ProductEntity
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.authentication.components.CleanShelfButton
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.navigation.AppScreens

@Composable
fun DetailItem(
    modifier: Modifier = Modifier,
    productResponseItem: List<ProductResponseItem>,
    productEntity: ProductEntity,
    navController: NavController
) {
    val cartViewModel: CartViewModel = hiltViewModel()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(productResponseItem) { productResponseItem ->
            Holder(
                modifier = modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                image = productResponseItem.image,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = productResponseItem.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp)
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )

                    Text(
                        text = productResponseItem.unit,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 23.sp)
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Ksh. ${productResponseItem.price.toString()}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = productResponseItem.description,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp)
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.height(20.dp))


                CleanShelfButton(title = "Add to Cart", onClick = {
                    cartViewModel.addProductToCart(productEntity)
                    navController.navigate(AppScreens.CartScreen.route)
                }, modifier = Modifier.fillMaxWidth())

            }

        }

    }

}