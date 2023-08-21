package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.NotesCard
import org.junit.Rule
import org.junit.Test

class NotesCardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNotesCard() {
        composeTestRule.setContent {
            NotesCard(
                "John",
                "Doe",
                "johnd",
                "August 17, 2023",
                "Remember to submit the monthly report by Friday.",
                onClick = {}
            )
        }
        composeTestRule.onNodeWithTag("NotesCardTestId").assertExists().assertIsDisplayed()
    }
}