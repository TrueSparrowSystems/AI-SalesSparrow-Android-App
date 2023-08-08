package com.example.salessparrow.common_components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salessparrow.data.Attribute
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.eastBay_70
import com.example.salessparrow.ui.theme.port_gore
import com.example.salessparrow.ui.theme.ruby
import com.example.salessparrow.ui.theme.walkaway_gray

@Composable
fun AccountName(
    name: String,
    showAddNote: Boolean = false,
    attributes: Attribute
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
//                          handle redirect url attributes.url
            },
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),

            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            interactionSource = remember { MutableInteractionSource() },
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomText(
                    text = name, Typography.labelMedium, color = walkaway_gray
                )
                Spacer(Modifier.weight(1f))
                if (showAddNote) {
                    CustomText(
                        text = "Add Note", Typography.labelSmall, color = ruby
                    )
                }
            }
        }

    }

    Divider(
        color = eastBay_70,
        thickness = 0.5.dp,
        modifier = Modifier
            .height(1.dp)
    )
}

@Composable
@Preview(showBackground = true)
fun AccountNamePreview() {
    AccountName(
        "SalseSparrow",
        true,
        attributes = Attribute(
            "Account",
            "/services/data/v58.0/sobjects/Account/0011e00000bVSCyAAO"
        ),
    )
}