package com.truesparrowsystemspvtltd.salessparrow.common_components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun HorizontalBar() {
    Divider(thickness = 1.dp, modifier = Modifier.width(308.dp), color = Color(0x1A2A2E4F))
}