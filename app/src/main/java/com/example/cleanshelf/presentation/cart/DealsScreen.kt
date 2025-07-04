package com.example.cleanshelf.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.bookMarks.BookMarksViewModel
import com.example.cleanshelf.presentation.homeScreen.HomeScreenViewmodel
import com.example.cleanshelf.presentation.homeScreen.components.ProductsCard
import com.example.cleanshelf.presentation.navigation.AppScreens

@Composable
fun DealsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val homeScreenViewModel: HomeScreenViewmodel = hiltViewModel()
    val homeScreenState = homeScreenViewModel.HomeScreenState.collectAsStateWithLifecycle().value

    val discountedProducts = homeScreenState.productByCategory.values
        .flatten()
        ?.map { it } // or it.something
        ?.filter { it.price.toDouble() != it.discountedPrice }





    LazyColumn(
        modifier = modifier
            .padding(top = 30.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Offers",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        item {
            if (discountedProducts != null) {
                if (discountedProducts.isNotEmpty()) {
                    DealsItem(products = discountedProducts, navController)
                } else {
                    Text(
                        text = "No deals available.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun DealsItem(products: List<ProductResponseItem>, navController: NavController) {
    val bookMarksViewModel: BookMarksViewModel = hiltViewModel()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp), //  Set a fixed height
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductsCard(
                product = product,
                onSaveClicked = { bookMarksViewModel.toggleButton(product) },
                onProductItemClicked = { productId ->
                    navController.navigate(AppScreens.DetailScreen.createRoute(productId))
                }
            )
        }
    }
}

