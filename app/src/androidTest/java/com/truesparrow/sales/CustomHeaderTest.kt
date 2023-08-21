package com.truesparrow.sales

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.truesparrow.sales.common_components.AccountName
import com.truesparrow.sales.common_components.CustomHeader
import org.junit.Rule
import org.junit.Test

class CustomHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCustomHeader() {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithText("Accounts").assertExists()
        composeTestRule.onNodeWithText("Hello").assertExists()
    }

}