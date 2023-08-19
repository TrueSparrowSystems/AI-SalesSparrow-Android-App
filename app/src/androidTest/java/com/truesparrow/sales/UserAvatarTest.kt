package com.truesparrow.sales

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.common_components.UserAvatar
import org.junit.Rule
import org.junit.Test

class UserAvatarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testUserAvatar() {

        composeTestRule.setContent {
            UserAvatar(
                id = "user123",
                firstName = "John",
                lastName = "Doe",
                modifier = Modifier.padding(16.dp)
            )
        }

        composeTestRule.onNodeWithTag("userAvatarTestId").assertExists().assertIsDisplayed()
    }
}