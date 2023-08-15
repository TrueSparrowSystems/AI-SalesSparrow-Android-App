package com.truesparrowsystemspvtltd.salessparrow.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextButton(
    buttonText: String,
    buttonAction: () -> Unit,
    imageId: Int? = null,
    imageModifier: Modifier? = Modifier,
    isButtonEnabled: Boolean = true,
    buttonTextStyle: TextStyle? = TextStyle.Default,
) {
    TextButton(onClick = buttonAction, enabled = isButtonEnabled) {
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
        )
    }
}

