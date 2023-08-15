package com.truesparrowsystemspvtltd.salessparrow


import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truesparrowsystemspvtltd.salessparrow.common_components.CustomButton
import org.junit.Rule
import org.junit.Test

class CustomButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun testCustomButtonWithText() {
        composeTestRule.setContent {
            CustomButton(
                buttonText = "Connect With Salesforce",
                buttonTextStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                onClick = {},
                isLoadingProgressBar = false,
                imageId = null,
                imageContentDescription = null,
                imageModifier = null,
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                    testTag = "btn_connect_salesforce"
                },
                buttonShape = RectangleShape,
                buttonTextModifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        val buttonNode = composeTestRule.onNodeWithTag("btn_connect_salesforce")

        buttonNode.assertIsDisplayed()
        buttonNode.assertIsEnabled()
        buttonNode.performClick()
    }


    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun testCustomButtonWithImageAndText() {
        composeTestRule.setContent {
            CustomButton(
                buttonText = "Connect With Salesforce",
                buttonTextStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                onClick = {},
                isLoadingProgressBar = false,
//                imageId = R.drawable.event,
                imageContentDescription = "Salesforce",
                imageModifier = Modifier
                    .width(25.dp)
                    .height(18.dp),
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                    testTag = "btn_connect_salesforce"
                },
                buttonShape = RectangleShape,
                buttonTextModifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        val buttonNode = composeTestRule.onNodeWithTag("btn_connect_salesforce")

        buttonNode.assertIsDisplayed()
        buttonNode.assertIsEnabled()
        buttonNode.performClick()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun testCustomButtonLoadingState() {

        composeTestRule.setContent {
            CustomButton(
                buttonText = "Connect With Salesforce",
                buttonTextStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                onClick = {},
                isLoadingProgressBar = true,
                imageContentDescription = "Salesforce",
                imageModifier = Modifier
                    .width(25.dp)
                    .height(18.dp),
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                    testTag = "btn_connect_salesforce"
                },
                buttonShape = RectangleShape,
                buttonTextModifier = Modifier.padding(horizontal = 8.dp),
                isButtonEnabled = false
            )
        }

        // Wait for the composition to settle
        composeTestRule.waitForIdle()

        val buttonNode = composeTestRule.onNodeWithTag("btn_connect_salesforce")

        buttonNode.assertIsDisplayed()
        buttonNode.assertIsNotEnabled()

    }

}