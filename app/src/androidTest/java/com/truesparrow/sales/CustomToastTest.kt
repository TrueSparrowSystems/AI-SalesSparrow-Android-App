package com.truesparrow.sales

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.ToastState
import org.junit.Rule
import org.junit.Test

class CustomToastTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCustomToast() {
        val message = "Note is saved to your Salesforce Account"

        composeTestRule.setContent {
            CustomToast(
                toastState = ToastState.SUCCESS,
                message = message
            )
        }
        composeTestRule.onNodeWithTag("toastMessageRowTestId")
            .assertExists()
            .assertIsDisplayed()
    }
}
