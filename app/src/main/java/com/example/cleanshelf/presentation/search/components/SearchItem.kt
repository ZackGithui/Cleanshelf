package com.example.cleanshelf.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.presentation.homeScreen.components.ImageHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    productResponseItem: ProductResponseItem,
    cardClicked: (Int) -> Unit
) {



        Card(
            onClick = { cardClicked(productResponseItem.id) },
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
            Row (modifier = Modifier.padding(5.dp)){
            ImageHolder(
                image = productResponseItem.image,
                onClick = {},
                modifier = Modifier
                    .width(120.dp)

            )
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Row {
                    Text(
                        text = productResponseItem.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row{
                        Text(
                            text = productResponseItem.category,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                   Row{ Text(
                        text = productResponseItem.price.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                        color = MaterialTheme.colorScheme.onSurface
                   )

                   }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Text(
                            text = productResponseItem.unit,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 22.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

            }


        }
    }

}

