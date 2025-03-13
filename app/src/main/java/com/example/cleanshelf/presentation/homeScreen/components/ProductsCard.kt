package com.example.cleanshelf.presentation.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.bookMarks.BookMarksViewModel
import com.example.cleanshelf.ui.theme.CleanshelfTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsCard(
    product: ProductResponseItem,
    onSaveClicked: (ProductResponseItem) -> Unit,
    onProductItemClicked: (Int) -> Unit
) {
    val bookMarksViewModel: BookMarksViewModel = hiltViewModel()
    val isFavouriteSet = bookMarksViewModel.isFavourite.observeAsState(emptySet()).value // Observe bookmarked IDs

    // Ensure isBookMarked() is called only once per product ID
    LaunchedEffect(product.id) {
        bookMarksViewModel.isBookMarked(product.id)
    }

    val isBookmarked = isFavouriteSet.contains(product.id) // Check if this product is bookmarked

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .shadow(
                elevation = 8.dp,
                spotColor = Color.Gray.copy(0.9f),
                shape = RoundedCornerShape(12.dp)
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onProductItemClicked(product.id) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { onSaveClicked(product) },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Favourite",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageHolder(
                image = product.image, onClick = { onProductItemClicked(product.id) },
                modifier = Modifier.height(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Ksh. ${product.price}",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = product.unit,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}