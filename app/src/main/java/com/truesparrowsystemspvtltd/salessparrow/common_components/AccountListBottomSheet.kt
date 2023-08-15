package com.truesparrowsystemspvtltd.salessparrow.common_components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.truesparrowsystemspvtltd.salessparrow.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListBottomSheet(
    bottomSheetVisible: () -> Unit,
    showAddNote: Boolean = false,
    isAccountSelectionEnabled: Boolean = false
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
        CustomBottomSheetContainer(showAddNote, isAccountSelectionEnabled, bottomSheetVisible)
    }
}

