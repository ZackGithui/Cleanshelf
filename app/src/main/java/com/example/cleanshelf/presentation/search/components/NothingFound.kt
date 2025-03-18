package com.example.cleanshelf.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cleanshelf.R

@Composable
fun NothingFound() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nothing))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        composition.let {
            lottieAnimatable.animate(
                composition = it,
                iteration = LottieConstants.IterateForever
            )
        }

    }

    LottieAnimation(
        composition = composition,
        progress = lottieAnimatable.progress,
        modifier = Modifier.size(200.dp)
    )


}


@Composable
fun Nothing(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NothingFound()

        Text(
            text = "Nothing Found...",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                .copy(color = MaterialTheme.colorScheme.onBackground)
        )

    }

}