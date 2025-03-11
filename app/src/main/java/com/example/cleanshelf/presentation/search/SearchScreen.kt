package com.example.cleanshelf.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanshelf.presentation.navigation.AppScreens
import com.example.cleanshelf.presentation.search.components.SearchItem

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchState = searchViewModel.searchState.collectAsStateWithLifecycle().value
    var text by remember {
        mutableStateOf(searchState.query)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp)

    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            value = text,
            onValueChange = {
                text = it
                searchViewModel.onEvents(SearchEvents.QueryChanged(it)) // Notify ViewModel of changes
            },
            placeholder = { Text("Search...") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        text = ""
                        searchViewModel.onEvents(SearchEvents.QueryChanged("")) // Clear query
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear Search")
                    }
                }
            }
        )



        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            if (searchState.result.isNotEmpty())
                items(searchState.result) { product ->
                    SearchItem(
                        productResponseItem = product,
                        cardClicked = { productId ->
                            navController.navigate(
                                AppScreens.DetailScreen.createRoute(productId)
                            )
                        })

                }
            else if (text.isNotEmpty() && searchState.result.isEmpty()) {
                item {
                    Row (modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically){
                        Text(
                            text = "No results found ...",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 16.dp),
                            textAlign = TextAlign.Center


                        )

                    }

                }


            }


        }
    }

}