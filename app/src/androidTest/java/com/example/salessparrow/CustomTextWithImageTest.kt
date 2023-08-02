package com.example.salessparrow

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.salessparrow.common_components.CustomTextWithImage
import org.junit.Rule
import org.junit.Test

class CustomTextWithImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCustomTextWithImage() {
        val imageId = R.drawable.events
        val imageContentDescription = "image content description"
        val text = "Hello!"
        val textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)

        composeTestRule.setContent {
            MaterialTheme {
                CustomTextWithImage(
                    imageId = imageId,
                    imageContentDescription = imageContentDescription,
                    text = text,
                    textStyle = textStyle
                )
            }
        }

        val textNode = composeTestRule.onNodeWithText(text = text)



        textNode.assertIsDisplayed()
        textNode.assertTextEquals(text)

        textNode.performClick()
    }
}
