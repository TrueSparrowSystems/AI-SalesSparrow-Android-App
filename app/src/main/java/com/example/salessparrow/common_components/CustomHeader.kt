package com.example.salessparrow.common_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R

@Composable
fun CustomHeader(
    isLeftButtonPresent: Boolean = false,
    leftIcon: Int? = null,
    leftButtonText: String,
    leftButtonAction: () -> Unit,
    isLeftTextButton: Boolean = false,
    leftButtonShape: Shape,
    isRightButtonPresent: Boolean = false,
    rightIcon: Int? = null,
    rightButtonText: String,
    rightButtonAction: () -> Unit,
    isRightTextButton: Boolean = false,
    rightButtonShape: Shape,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isLeftButtonPresent) Arrangement.SpaceBetween else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically

    ) {
        if (isLeftButtonPresent) {
            if (isLeftTextButton) {
                CustomTextButton(
                    buttonText = leftButtonText,
                    buttonAction = leftButtonAction,
                    imageId = leftIcon,
                )
            } else {
                CustomButton(
                    buttonText = leftButtonText,
                    buttonTextStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    onClick = leftButtonAction,
                    isLoadingProgressBar = false,
                    imageId = leftIcon,
                    imageContentDescription = "Button Icon",
                    imageModifier = Modifier.size(24.dp),
                    buttonShape = leftButtonShape,
                    buttonTextModifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

        if (isRightButtonPresent) {
            if (isRightTextButton) {
                CustomTextButton(
                    buttonText = rightButtonText,
                    buttonAction = rightButtonAction,
                    imageId = rightIcon,
                )

            } else {
                CustomButton(
                    buttonText = rightButtonText,
                    buttonTextStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    onClick = rightButtonAction,
                    isLoadingProgressBar = false,
                    imageId = rightIcon,
                    imageContentDescription = "Button Icon",
                    imageModifier = Modifier.size(24.dp),
                    buttonShape = rightButtonShape,
                    buttonTextModifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CustomHeaderPreview() {
    CustomHeader(
        isLeftButtonPresent = true,
        leftIcon = R.drawable.buildings,
        leftButtonText = "Accounts",
        leftButtonAction = { /* Add your action here */ },
        isLeftTextButton = true,
        leftButtonShape = CircleShape,
        isRightButtonPresent = true,
        rightIcon = null,
        rightButtonText = "Hello",
        rightButtonAction = { /* Add your action here */ },
        isRightTextButton = false,
        rightButtonShape = CircleShape
    )
}