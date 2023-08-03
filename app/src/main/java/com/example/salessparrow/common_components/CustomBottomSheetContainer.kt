package com.example.salessparrow.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.salessparrow.R
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.port_gore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer() {
    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier

                        .wrapContentWidth()
                        .padding(10.dp)
                        .heightIn(min = 1.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        CustomText(
                            text = "Search", Typography.labelSmall, color = port_gore
                        )
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            val accountNames = listOf(
                "Google", "Microsoft", "Apple", "Amazon", "Facebook",
                "IBM", "Oracle", "Adobe", "Intel", "Nvidia"
            )
            accountNames.forEach { name ->
                AccountName(name = name, true)
            }
        }
    }
}