package com.truesparrow.sales

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.truesparrow.sales.common_components.TermsAndConditionComponent
import org.junit.Rule
import org.junit.Test

class TermsAndConditionComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun testTermsAndConditionComponent() {
        composeTestRule.setContent {
            TermsAndConditionComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        testTagsAsResourceId = true
                        testTag = "TermsAndCondition"
                    }
            )
        }

        // Perform the assertions
        val termsAndConditionsNode = composeTestRule.onNodeWithTag("TermsAndCondition")

        // Assert that the TermsAndCondition node is displayed
        termsAndConditionsNode.assertIsDisplayed()
    }

}
