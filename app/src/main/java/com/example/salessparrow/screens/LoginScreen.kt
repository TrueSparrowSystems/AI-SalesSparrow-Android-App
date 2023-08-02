package com.example.salessparrow.screens

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.salessparrow.common_components.CustomHeader
import com.example.salessparrow.R
import com.example.salessparrow.common_components.AccountListBottomSheet

@Composable
fun LogInScreen() {

    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    CustomHeader(
        isLeftButtonPresent = true,
        leftIcon = R.drawable.buildings,
        leftButtonText = "Accounts",
        leftButtonAction = { /* Add your action here */ },
        isLeftTextButton = true,
        leftButtonShape = CircleShape,

        isRightButtonPresent = true,
        rightIcon = R.drawable.search_icon,
        rightButtonText = "",
        rightButtonAction = toggleBottomSheet,
        isRightTextButton = true,
        rightButtonShape = CircleShape
    )
//    AccountCard()

    if (bottomSheetVisible) {
        AccountListBottomSheet( toggleBottomSheet )
    }
}



