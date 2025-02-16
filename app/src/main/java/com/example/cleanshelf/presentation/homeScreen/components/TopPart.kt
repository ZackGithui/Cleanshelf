package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cleanshelf.R

@Composable
fun TopPart() {
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Cleanshelf",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp)
        )
        Image(
            painter = painterResource(id = R.drawable.cleanshelficon),

            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier.size(110.dp),
            contentScale = ContentScale.Crop,
           // colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)

        )


    }


}


@Preview
@Composable
private fun TopPart1() {
    TopPart()

}