package com.example.cleanshelf.presentation.homeScreen.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerScreen() {
    val shimmerColors = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f),
    )

    val animation = rememberInfiniteTransition()
    val animateTransition = animation.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = animateTransition.value, y = animateTransition.value)

    )
    ShimmerObject(brush = brush)


}


@Composable
fun ShimmerObject(brush: Brush) {
    Spacer(
        modifier = Modifier
            .size(height = 170.dp, width = 100.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(brush)
    )
}


@Preview
@Composable
private fun ShimmerScreenPrev() {
    ShimmerObject(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(0.7f),
                Color.LightGray.copy(0.2f),
                Color.LightGray.copy(0.7f)
            )
        )
    )


}