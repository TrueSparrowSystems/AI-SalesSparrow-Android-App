package com.example.salessparrow.common_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salessparrow.api.getAccountList
import com.example.salessparrow.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListBottomSheet(
    bottomSheetVisible: () -> Unit,
) {

    val context = LocalContext.current
    getAccountList(context)

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


@Composable
@Preview(showBackground = true)
fun CustomBottomSheetContainerPreview() {
    CustomBottomSheetContainer()
}