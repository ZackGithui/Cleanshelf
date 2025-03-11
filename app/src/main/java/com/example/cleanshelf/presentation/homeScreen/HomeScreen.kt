package com.example.cleanshelf.presentation.homeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.homeScreen.components.CategoryTab
import com.example.cleanshelf.presentation.homeScreen.components.ProductsCard
import com.example.cleanshelf.presentation.homeScreen.components.TopPart
import com.example.cleanshelf.presentation.navigation.AppScreens
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewmodel: HomeScreenViewmodel = hiltViewModel()
) {
    val homeScreenState = homeScreenViewmodel.HomeScreenState.collectAsStateWithLifecycle().value

    val categories = listOf(
        "All",
        "Bakery",
        "Cleaning",
        "Frozen food",
        "Dairy products",
        "Fresh products",
        "Pantry staples"
    )
    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            homeScreenViewmodel.onEvents(event = Events.CategoryChanged(categories[page]))
        }
    }


    Column {
        TopPart()

        CategoryTab(
            modifier = Modifier.fillMaxWidth(),
            categories = categories,
            pagerState = pagerState,
            onTabClicked = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
                homeScreenViewmodel.onEvents(Events.CategoryChanged(categories[index]))


            }
        )


        val productList = when (homeScreenState.category.lowercase()) {
            "bakery" -> homeScreenState.bakery
            "cleaning" -> homeScreenState.cleaning
            "frozen food" -> homeScreenState.frozenFood
            "dairy products" -> homeScreenState.dairyProducts
            "fresh products" -> homeScreenState.freshProducts
            "pantry staples" -> homeScreenState.pantryStaples
            else -> homeScreenState.all
        }
        HorizontalPager(state = pagerState) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productList) { data ->
                    Log.d(
                        TAG,
                        "Displaying products for category: ${homeScreenState.category}, Data: $data"
                    )
                    ProductsCard(
                        product = data,
                        onSaveClicked = { /*TODO*/ },
                        onProductItemClicked = { productId ->
                            navController.navigate(
                                AppScreens.DetailScreen.createRoute(productId)
                            )

                        }
                    )
                }
            }

        }


    }

}


@Preview
@Composable
private fun HomeScreenPrev() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)

}
