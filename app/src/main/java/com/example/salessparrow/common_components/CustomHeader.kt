package com.example.salessparrow.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomHeader(
    isLeftButtonPresent: Boolean = false,
    leftIcon: Int? = null,
    leftButtonText: String,
    leftButtonAction: () -> Unit,
    isLeftTextButton: Boolean = false,
    leftButtonShape: Shape,
    leftButtonTextStyle: TextStyle? = TextStyle.Default,
    leftIconModifier: Modifier? = Modifier,
    isLeftButtonEnabled: Boolean = true,

    isRightButtonPresent: Boolean = false,
    rightIcon: Int? = null,
    rightButtonText: String,
    rightButtonAction: () -> Unit,
    isRightTextButton: Boolean = false,
    rightButtonShape: Shape,
    rightButtonTextStyle: TextStyle? = TextStyle.Default,
    rightIconModifier: Modifier? = Modifier,
    isRightButtonEnabled: Boolean = true,
    rightButtonTestId: String? = null,
    shouldShowAvatarComponent: Boolean = false,
    userId: String? = null,
    userName: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF1F1F2))
            .padding(top = 10.dp),
        horizontalArrangement = if (isLeftButtonPresent) Arrangement.SpaceBetween else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically

    ) {
        if (isLeftButtonPresent) {
            if (isLeftTextButton) {
                CustomTextButton(
                    buttonText = leftButtonText,
                    buttonAction = leftButtonAction,
                    imageId = leftIcon,
                    buttonTextStyle = leftButtonTextStyle,
                    imageModifier = leftIconModifier,
                    isButtonEnabled = isLeftButtonEnabled
                )
            } else {
                CustomButton(
                    buttonText = leftButtonText,
                    buttonTextStyle = leftButtonTextStyle,
                    onClick = leftButtonAction,
                    isLoadingProgressBar = false,
                    imageId = leftIcon,
                    imageContentDescription = "Button Icon",
                    imageModifier = leftIconModifier,
                    buttonShape = leftButtonShape,
                    buttonTextModifier = Modifier.padding(horizontal = 8.dp),
                    isButtonEnabled = isLeftButtonEnabled

                )
            }
        }

        if (isRightButtonPresent) {
            if (isRightTextButton) {
                if (shouldShowAvatarComponent) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 15.dp)
                    ) {
                        CustomTextButton(
                            buttonText = rightButtonText,
                            buttonAction = rightButtonAction,
                            imageId = rightIcon,
                            buttonTextStyle = rightButtonTextStyle,
                            imageModifier = rightIconModifier,
                            isButtonEnabled = isRightButtonEnabled
                        )

                        UserAvatar(
                            id = userId!!,
                            firstName = userName!!.split(" ")[0],
                            lastName = userName!!.split(" ")[1],
                            size = 24.dp,
                            textStyle = TextStyle(
                                fontSize = 6.45.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                letterSpacing = 0.26.sp,
                            ),
                            onUserAvatarClick = {
                                NavigationService.navigateTo(Screens.SettingsScreen.route)
                            }
                        )
                    }
                } else {
                    CustomTextButton(
                        buttonText = rightButtonText,
                        buttonAction = rightButtonAction,
                        imageId = rightIcon,
                        buttonTextStyle = rightButtonTextStyle,
                        imageModifier = rightIconModifier,
                        isButtonEnabled = isRightButtonEnabled
                    )
                }
            } else {
                CustomButton(
                    buttonText = rightButtonText,
                    buttonTextStyle = rightButtonTextStyle,
                    onClick = rightButtonAction,
                    isLoadingProgressBar = false,
                    imageId = rightIcon,
                    imageContentDescription = "Button Icon",
                    imageModifier = Modifier.size(24.dp),
                    buttonShape = rightButtonShape,
                    buttonTextModifier = Modifier.padding(horizontal = 8.dp),
                    isButtonEnabled = isRightButtonEnabled,
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        if (rightButtonTestId != null) {
                            testTag = rightButtonTestId
                        }
                    }
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
