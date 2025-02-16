package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImageHolder(
    modifier: Modifier = Modifier,
    image: String,
    onClick: () -> Unit
) {
    Card(modifier = modifier
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.background)) {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(image)
                .build(),
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )

    }
}

@Preview
@Composable
private fun ImageHolder2() {
    ImageHolder(
        image = "", onClick = {}, modifier = Modifier
            .height(150.dp)
            .width(100.dp)
    )


}