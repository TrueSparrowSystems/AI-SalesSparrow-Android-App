package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.truesparrow.sales.common_components.EditableTextField
import org.junit.Rule
import org.junit.Test

class EditableTextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEditableTextField() {
        val initialNote = "Initial Note"

        composeTestRule.setContent {
            EditableTextField(
                note = initialNote,
                onValueChange = {},
                readOnly = false
            )
        }

        composeTestRule.onNodeWithTag("editableTextFieldTestId").assertExists()
            .assertIsDisplayed()
    }
}