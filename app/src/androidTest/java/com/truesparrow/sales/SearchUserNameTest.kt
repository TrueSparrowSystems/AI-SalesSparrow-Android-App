package com.truesparrow.sales

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.SearchUserName
import org.junit.Rule
import org.junit.Test


class SearchUserNameTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun TestSearchUserName() {
        var accountRowClickCount = 0

        val onAccountRowClick: () -> Unit = { accountRowClickCount++ }

        composeTestRule.setContent {
            SearchUserName(
                firstName = "Test",
                lastName = "Account",
                searchNameTestId = "searchNameTestId",
                onAccountRowClick = onAccountRowClick,
            )
        }
        composeTestRule.onNodeWithTag("searchNameTestId")
            .assertExists()
            .assertIsDisplayed()
    }

}

