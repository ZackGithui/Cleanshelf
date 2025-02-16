package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem

@Composable
fun ProductCard(
    products: List<ProductResponseItem>,
    text: String,
    onLabelButtonClicked: () -> Unit,
    onGameClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.onPrimary)

    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 23.sp)
            )
            TextButton(onClick = onLabelButtonClicked) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // LazyRow for product items
        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products) { product ->
                Column(
                    modifier = Modifier
                        .padding(2.dp)
                        .width(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    ImageHolder(
                        modifier = Modifier
                            .height(150.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        image = product.image ?: "",
                        onClick = { onGameClicked(product.id) }
                    )
                    Text(
                        text = product.name ?: "Unknown Title",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                }
            }
        }
    }

}