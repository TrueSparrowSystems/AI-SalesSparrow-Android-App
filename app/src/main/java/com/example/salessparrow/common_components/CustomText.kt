package com.example.salessparrow.common_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomText(
    text: String,
    customTextStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        style = customTextStyle,
        color = color
    )
}