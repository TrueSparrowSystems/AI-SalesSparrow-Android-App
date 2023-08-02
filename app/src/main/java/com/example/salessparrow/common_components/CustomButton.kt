package com.example.salessparrow.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    buttonText: String,
    buttonTextStyle: TextStyle? = TextStyle.Default,
    onClick: () -> Unit,
    isLoadingProgressBar: Boolean? = false,
    imageId: Int? = null,
    imageContentDescription: String? = null,
    imageModifier: Modifier? = Modifier,
    modifier: Modifier = Modifier,
    buttonShape: Shape?,
    buttonTextModifier: Modifier? = Modifier,
) {
    if (buttonShape != null) {
        Button(
            onClick = onClick,
            modifier = modifier,
            shape = buttonShape,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (isLoadingProgressBar == true) {
                    CircularProgressIndicator();
                } else {


                    if (imageId != null) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = imageContentDescription,
                            modifier = imageModifier!!
                        )
                    }
                    Text(
                        text = buttonText,
                        style = buttonTextStyle!!,
                        modifier = buttonTextModifier!!
                    )
                }
            }
        }
    }
}
