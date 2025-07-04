package com.example.cleanshelf.presentation.detailScreen

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.domain.cart.CartItem
import com.example.cleanshelf.presentation.cart.CartViewModel
import com.example.cleanshelf.presentation.detailScreen.components.DetailItem
import com.example.cleanshelf.ui.theme.CleanshelfTheme

@Composable
fun DetailScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()

    val state = detailViewModel.detailState.collectAsStateWithLifecycle().value

    LaunchedEffect(productId) {
        detailViewModel.getProductsByIds(productId)
    }

    val product = state.product

    product?.let {
        val cartItem = com.example.cleanshelf.data.remote.Dto.CartItem(
            id = it.id.toString(),
            name = it.name,
            price = it.price,
            quantity = 1, // Default quantity when adding
            image = it.image,
            description = it.description,
            productId = it.id,
            userId = it.id.toString()

        )

        DetailItem(
            navController = navController,
            productResponseItem = listOf(it),
            cartItem = cartItem,
        )
    }

}


@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailScreenPrev() {
    CleanshelfTheme {
        val products = ProductResponseItem(
            id = 51,
            name = "Tortilla Wraps",
            category = "Frozen Food",
            price = 520,
            unit = "360 g",
            description = "This soft, thin flatbread made from finely ground wholemeal wheat flour. Pair this up with fillings of your choice, for wraps, tacos... let your creativity lead!",
            image = "https://cdn.mafrservices.com/pim-content/KEN/media/product/42980/1718895604/42980_main.jpg?im=Resize=400",
            number = 2,
            discountedPrice = 45.6
        )
        val navController = rememberNavController()
        DetailScreen(productId = 0, navController = navController)

    }


}