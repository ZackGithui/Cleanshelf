package com.example.cleanshelf.presentation.homeScreen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cleanshelf.presentation.bookMarks.BookMarksViewModel
import com.example.cleanshelf.presentation.homeScreen.components.CategoryTab
import com.example.cleanshelf.presentation.homeScreen.components.ErrorScreen
import com.example.cleanshelf.presentation.homeScreen.components.ProductsCard
import com.example.cleanshelf.presentation.homeScreen.components.ShimmerScreen
import com.example.cleanshelf.presentation.homeScreen.components.TopPart
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.example.cleanshelf.presentation.search.components.NothingFound
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeScreenViewmodel: HomeScreenViewmodel = hiltViewModel(),
    bookMarksViewModel: BookMarksViewModel = hiltViewModel()
) {
    val homeScreenState = homeScreenViewmodel.HomeScreenState.collectAsStateWithLifecycle().value
    val isFavourite = bookMarksViewModel.isFavourite.observeAsState().value

    val categories = homeScreenState.productByCategory.keys.toList()

    // Avoid errors when categories are still loading
    //if (categories.isEmpty()) {
       // NothingFound()
   // }

    val pagerState = rememberPagerState(pageCount = { categories.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (categories.isNotEmpty() && page < categories.size) {
                homeScreenViewmodel.onEvents(event = Events.CategoryChanged(categories[page]))
            }
        }
    }


    Column {
        TopPart(navController)

        CategoryTab(
            modifier = Modifier.fillMaxWidth(),
            categories = categories.map { it.replaceFirstChar(Char::titlecase) },
            pagerState = pagerState,
            onTabClicked = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
                homeScreenViewmodel.onEvents(Events.CategoryChanged(categories[index]))
            }
        )

        when {
            homeScreenState.isLoading -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(8) {
                        ShimmerScreen()
                    }
                }
            }

            homeScreenState.error?.isNotEmpty() == true -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp),
                    contentAlignment = Alignment.Center
                ) {

                    ErrorScreen()
                }
            }

            else -> {
                HorizontalPager(state = pagerState) { page ->
                    val currentCategory = categories[page].lowercase()
                    val productList = homeScreenState.productByCategory[currentCategory] ?: emptyList()

                    if (productList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 90.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            NothingFound()
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(productList) { data ->
                                ProductsCard(
                                    product = data,
                                    onSaveClicked = { bookMarksViewModel.toggleButton(data) },
                                    onProductItemClicked = { productId ->
                                        navController.navigate(AppScreens.DetailScreen.createRoute(productId))
                                    }
                                )
                            }
                        }
                    }
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
