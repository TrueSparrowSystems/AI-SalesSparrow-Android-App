package com.truesparrow.sales.common_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomText(
    text: String,
    customTextStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    modifier: Modifier? = null
) {
    if (modifier != null) {
        Text(
            text = text,
            style = customTextStyle,
            color = color,
            modifier = modifier
        )
    } else {
        Text(
            text = text,
            style = customTextStyle,
            color = color
        )
    }
}