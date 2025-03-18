package com.example.cleanshelf.presentation.homeScreen.components

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cleanshelf.R
import com.example.cleanshelf.presentation.homeScreen.HomeScreenViewmodel

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    val homeScreenViewmodel: HomeScreenViewmodel = hiltViewModel()
    val homeScreenState = homeScreenViewmodel.HomeScreenState.collectAsStateWithLifecycle().value

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LottieAnimation()
        Text(
            text =homeScreenState.error ?: "Unexpected error occurred",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                .copy(color = MaterialTheme.colorScheme.onBackground),

            )
    }


}

@Composable
fun LottieAnimation() {
    val composition by rememberLottieComposition( LottieCompositionSpec.RawRes(R.raw.animation))
    //val progress by animateLottieCompositionAsState(composition = composition)
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect (composition){
        composition.let { lottieAnimatable.animate(composition = it, iteration = LottieConstants.IterateForever) }

    }

    com.airbnb.lottie.compose.LottieAnimation(
        composition = composition,
        progress =lottieAnimatable.progress ,
        modifier = Modifier.size(200.dp)
    )

}