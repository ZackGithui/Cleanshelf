package com.example.cleanshelf.presentation.homeScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cleanshelf.data.remote.Dto.ProductResponseItem
import com.example.cleanshelf.ui.theme.CleanshelfTheme

@Composable
fun ProductsCard(
    product: ProductResponseItem,
    onSaveClicked: () -> Unit,
    onProductItemClicked: (Int) -> Unit,


) {

    Column(
        modifier = Modifier
            .width(130.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { onSaveClicked() },
                colors = IconButtonDefaults.iconButtonColors(
                    //containerColor = MaterialTheme.colorScheme.secondary, // Background color
                    contentColor = MaterialTheme.colorScheme.onSurface, // Icon color
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant, // Disabled state
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant // Disabled icon color
                )
               ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favourite",
                    modifier = Modifier.size(25.dp)
                )

            }
        }
        Spacer(modifier = Modifier.height(3.dp))


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            ImageHolder(
                image = product.image, onClick = { onProductItemClicked(product.id) },
                modifier = Modifier
                    .height(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = product.name, style = TextStyle(color = MaterialTheme.colorScheme.onSurface ))

        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ksh. ${product.price}", style = TextStyle(color = MaterialTheme.colorScheme.onSurface ) )
            Text(text = product.unit, style = TextStyle(color = MaterialTheme.colorScheme.onSurface ))


        }


    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable

private fun ProductsPrev() {

    val productItem = ProductResponseItem(
        id = 51,
        name = "Tortilla Wraps",
        category = "Frozen Food",
        price = 520,
        unit = "360 g",
        description = "This soft, thin flatbread made from finely ground wholemeal wheat flour. Pair this up with fillings of your choice, for wraps, tacos... let your creativity lead!",
        image = "https://cdn.mafrservices.com/pim-content/KEN/media/product/42980/1718895604/42980_main.jpg?im=Resize=400"
    )
    CleanshelfTheme {
        ProductsCard(
            product = productItem, onSaveClicked = { /*TODO*/ },
            onProductItemClicked = {},
        )

    }
}
