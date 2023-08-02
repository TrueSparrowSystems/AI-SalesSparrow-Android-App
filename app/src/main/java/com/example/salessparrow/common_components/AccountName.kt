package com.example.salessparrow.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.eastBay_70
import com.example.salessparrow.ui.theme.port_gore

@Composable
fun AccountName(
    name : String,
    showAddNote: Boolean = false,
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(10.dp)
            .heightIn(min = 1.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CustomText(
                text = name, Typography.labelSmall, color = port_gore
            )
            Spacer(Modifier.weight(1f))
            if (showAddNote) {
                CustomText(
                    text = "Add Note", Typography.labelSmall, color = port_gore
                )
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
    AccountName("SalseSparrow")
}