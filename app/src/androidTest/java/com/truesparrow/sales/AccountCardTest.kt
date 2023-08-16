package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.truesparrow.sales.common_components.AccountCard
import org.junit.Rule
import org.junit.Test

class AccountCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAccountNameText() {
        composeTestRule.setContent {
            AccountCard(accountName = "ACCOUNT")
        }
        composeTestRule.onNodeWithText("ACCOUNT").assertIsDisplayed()
        composeTestRule.onNodeWithText("CONTACT").assertIsDisplayed()
    }
}

