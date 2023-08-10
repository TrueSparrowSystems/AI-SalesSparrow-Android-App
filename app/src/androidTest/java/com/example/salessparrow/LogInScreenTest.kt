package com.example.salessparrow

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.salessparrow.screens.LogInScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLogInScreen() {


        composeTestRule.setContent {
            LogInScreen();
        }


        // Perform interactions and assertions here
        composeTestRule.onNodeWithTag("salesforce_button")
            .assertExists()
            .assertIsDisplayed()
    }

}