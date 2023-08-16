package com.truesparrow.sales

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.truesparrow.sales.common_components.CustomText
import org.junit.Rule
import org.junit.Test

class CustomTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCustomText() {
        // Set up the text to be displayed
        val text = "Hello, Compose!"

        // Render the CustomText component
        composeTestRule.setContent {
            MaterialTheme {
                CustomText(
                    text = text
                )
            }
        }

        // Perform the assertion
        composeTestRule.onNodeWithText(text = text).assertExists()
    }
}
