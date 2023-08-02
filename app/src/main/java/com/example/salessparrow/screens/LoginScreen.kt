package com.example.salessparrow.screens

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import com.example.salessparrow.common_components.CustomHeader
import com.example.salessparrow.R

@Composable
fun LogInScreen() {
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
//    AccountCard()
}
