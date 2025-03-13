package com.example.cleanshelf.presentation.bookMarks

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.bookMarks.component.BookMarkItem
import com.example.cleanshelf.presentation.navigation.AppScreens

@Composable
fun BookMarkScreen(modifier: Modifier = Modifier) {
    val bookMarksViewModel: BookMarksViewModel = hiltViewModel()
    val bookMarkState = bookMarksViewModel.bookMarkState.collectAsStateWithLifecycle().value

    // Ensure bookMarks is not null before using it
    val bookMarksList = bookMarkState.bookMarks ?: emptyList()
    LaunchedEffect(AppScreens.BookMarks.route) {
        bookMarksViewModel.getAllBookMarks()

    }

    LazyColumn(modifier = modifier.padding(top =25.dp)) {
        items(bookMarksList) { bookMark ->
            BookMarkItem(
                bookMark = bookMark,
                onCardClicked = { /* Handle click */ },
                onSaveClicked = {
                    val productResponseItem = ProductResponseItem(
                        id = bookMark.id,
                        name = bookMark.name,
                        category = bookMark.category,
                        price = bookMark.price,
                        unit = bookMark.unit.toString(),
                        description = "", // Add description if needed
                        image = bookMark.image
                    )
                    bookMarksViewModel.toggleButton(productResponseItem) // Pass BookMark object instead
                }
            )
        }
    }
}

