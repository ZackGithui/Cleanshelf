package com.example.cleanshelf.presentation.authentication.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CleanShelfTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    placeholder: String = "",
    placeholderColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
    errorMessage: String? = null,
    color: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary
    )
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        isError = errorMessage != null,
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
        shape = MaterialTheme.shapes.large,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = placeholderColor,
            )
        },
        supportingText = {
            errorMessage?.let { msg ->
                Text(
                    text = msg,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = MaterialTheme.colorScheme.error
                )

            }
        },
        colors = color
    )


}


@Composable
fun CleanShelfPasswordTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean = false,
    placeholder: String = "",
    errorMessage: String?= null,
    onTrailingIconClicked: () -> Unit,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary
    ),
    //textColor: Color

) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = errorMessage != null,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.onBackground,
            )
        },
        supportingText = {
                         errorMessage?.let { msg->
                             Text(text = msg,
                                 color= MaterialTheme.colorScheme.error,
                                 style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp))
                         }
        },

        trailingIcon = {
            IconButton(onClick = onTrailingIconClicked) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                )

            }
        },
        shape = MaterialTheme.shapes.large,
        colors = colors,


        )


}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TextFiledPrev() {
    CleanShelfTextField(value = "", onValueChange = {})

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PassTextFieldPrev() {
    CleanShelfPasswordTextField(onValueChange = {}, onTrailingIconClicked = { /*TODO*/ })

}