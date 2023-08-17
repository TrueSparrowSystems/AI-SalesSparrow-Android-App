package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.AccountName
import org.junit.Rule
import org.junit.Test


class AccountNameTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun TestAccountName() {
        var accountRowClickCount = 0
        var addNoteClickCount = 0

        val onAccountRowClick: () -> Unit = { accountRowClickCount++ }
        val onAddNoteClick: () -> Unit = { addNoteClickCount++ }

        composeTestRule.setContent {
            AccountName(
                name = "Test Account",
                accountRowTestId = "accountRowTestId",
                addNoteButtonTestId = "addNoteButtonTestId",
                onAccountRowClick = onAccountRowClick,
                onAddNoteClick = onAddNoteClick
            )
        }
        composeTestRule.onNodeWithTag("accountRowTestId")
            .assertExists()
            .assertIsDisplayed()
    }
}

