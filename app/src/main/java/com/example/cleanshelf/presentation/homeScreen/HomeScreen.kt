package com.example.cleanshelf.presentation.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.homeScreen.components.ProductCard
import com.example.cleanshelf.presentation.homeScreen.components.TopPart

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewmodel: HomeScreenViewmodel = hiltViewModel()
) {
    val homeScreenState = homeScreenViewmodel.HomeScreenState.collectAsStateWithLifecycle().value

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        topBar = { TopPart() },
        bottomBar = {},
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
            ) {
                if (homeScreenState.pantryStaples.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.pantryStaples,
                            text = "Pantry Staples",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.bakery.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.bakery,
                            text = "Bakery",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.frozenFood.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.frozenFood,
                            text = "Frozen Food",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.cleaning.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.cleaning,
                            text = "Cleaning",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.freshProducts.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.freshProducts,
                            text = "Fresh Products",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.dairyProducts.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.dairyProducts,
                            text = "Dairy Products",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }
                if (homeScreenState.beverages.isNotEmpty()) {
                    item {
                        ProductCard(
                            products = homeScreenState.beverages,
                            text = "Beverages",
                            onLabelButtonClicked = { },
                            onGameClicked = {}
                        )
                    }

                }

            }


        }
    )

}