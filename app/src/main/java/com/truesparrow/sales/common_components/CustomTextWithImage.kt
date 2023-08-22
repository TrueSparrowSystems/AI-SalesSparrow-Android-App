package com.truesparrow.sales.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextWithImage(
    imageId: Int,
    imageContentDescription: String,
    text: String,
    textStyle: TextStyle,
    maxLines: Int = 1,
    imageModifier: Modifier? = Modifier,
    onClick: () -> Unit? = {},
    textModifier: Modifier? = Modifier
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(
            onClick = { onClick()!! },
            interactionSource = MutableInteractionSource(),
            indication = null
        )
    ) {

        Image(
            painter = painterResource(id = imageId),
            contentDescription = imageContentDescription,
            modifier = imageModifier!!
        )
        Text(
            text,
            style = textStyle,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = textModifier!!
        )
    }

}