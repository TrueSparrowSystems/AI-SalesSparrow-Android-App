package com.truesparrow.sales.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.util.NoRippleInteractionSource

@Composable
fun CustomTextButton(
    buttonText: String,
    buttonAction: () -> Unit,
    imageId: Int? = null,
    imageModifier: Modifier? = Modifier,
    isButtonEnabled: Boolean = true,
    buttonTextStyle: TextStyle? = TextStyle.Default,
    textModifier:  Modifier? = Modifier,
    isRightButtonModifier: Modifier? = Modifier
) {
    TextButton(
        onClick = buttonAction,
        enabled = isButtonEnabled,
        interactionSource = NoRippleInteractionSource(),
        modifier = isRightButtonModifier!!
    ) {
        if (imageId != null) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "55",
                modifier = imageModifier!!
            )
        }
        Spacer(modifier = Modifier.padding(start = 8.dp))
        CustomText(
            text = buttonText,
            customTextStyle = buttonTextStyle!!,
            modifier = textModifier!!
        )
    }
}

