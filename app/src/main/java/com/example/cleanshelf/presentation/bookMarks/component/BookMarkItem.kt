package com.example.cleanshelf.presentation.bookMarks.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cleanshelf.data.local.BookMark
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.bookMarks.BookMarksViewModel
import com.example.cleanshelf.presentation.homeScreen.components.ImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkItem(
    modifier: Modifier = Modifier,
    bookMark: BookMark,
    onCardClicked: (Int) -> Unit,
    onSaveClicked: (BookMark) -> Unit // Pass the bookmark item for removal
) {
    val bookMarksViewModel: BookMarksViewModel = hiltViewModel()
    val isFavouriteSet = bookMarksViewModel.isFavourite.observeAsState(emptySet()).value

    // Avoid unnecessary recompositions
    val isBookmarked = isFavouriteSet.contains(bookMark.id)

    Card(
        onClick = { onCardClicked(bookMark.id) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.surface)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color.Gray.copy(alpha = 0.4f)
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            ImageHolder(
                image = bookMark.image,
                onClick = {},

            )
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    ) {
                    Text(
                        text = bookMark.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    IconButton(
                        onClick = { onSaveClicked(bookMark) }, // Remove bookmark
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }

                Text(
                    text = bookMark.category,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Ksh. ${bookMark.price}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = bookMark.unit.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}