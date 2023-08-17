package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.Fab
import com.truesparrow.sales.common_components.ToastState
import org.junit.Rule
import org.junit.Test

class FabTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFab() {
        composeTestRule.setContent {
           Fab()
        }
        composeTestRule.onNodeWithTag("btn_create_note")
            .assertExists()
            .assertIsDisplayed()
    }
}
