package com.truesparrow.sales.common_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.ui.theme.white
import com.truesparrow.sales.viewmodals.GlobalStateViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchNameBottomSheet(
    bottomSheetVisible: () -> Unit,
    accountId: String = "",
    accountName: String = "",
    id : String

) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = bottomSheetState,
        containerColor = white,
        onDismissRequest = {
            bottomSheetVisible()
        },
        shape = RoundedCornerShape(
            topStart = 10.dp, topEnd = 10.dp
        ),
    ) {
        SearchNameSheetContainer(
            bottomSheetVisible,
            id = id,
            accountId = accountId,
            accountName = accountName!!,
        )
    }
}

