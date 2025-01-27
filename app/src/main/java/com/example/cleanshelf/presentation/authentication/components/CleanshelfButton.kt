package com.example.cleanshelf.presentation.authentication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CleanShelfButton(
    modifier: Modifier = Modifier,
    title: String = "",
    contentPaddingValues: PaddingValues = PaddingValues(
        start = 24.dp,
        top = 12.dp,
        bottom = 12.dp,
        end = 24.dp
    ),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ),
    onClick: () -> Unit,

    ) {

    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = colors,
        contentPadding = contentPaddingValues,
        onClick = onClick
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
        )

    }

}

@Composable
fun CleanShelfLabelButton(
    modifier: Modifier = Modifier,
    unClickableText: String = "",
    clickableText: String = "",
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = unClickableText,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
        TextButton(
            onClick = onClick,


            ) {
            Text(
                text = clickableText,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp)
            )
        }
    }

}


