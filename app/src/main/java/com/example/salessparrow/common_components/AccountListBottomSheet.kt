package com.example.salessparrow.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salessparrow.R
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.port_gore
import com.example.salessparrow.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListBottomSheet(
    bottomSheetVisible: () -> Unit,
) {

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = rememberSheetState(
            skipHalfExpanded = true
        ),
        containerColor = white,
        onDismissRequest = {
            bottomSheetVisible()
        },
        shape = RoundedCornerShape(
            topStart = 10.dp, topEnd = 10.dp
        ),
    ) {
        CustomBottomSheetContainer()
    }
}


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
            AccountName()
            AccountName(showAddNote = true)
            AccountName(showAddNote = true)
            AccountName(showAddNote = true)
            AccountName(showAddNote = true)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomBottomSheetContainerPreview() {
    CustomBottomSheetContainer()
}