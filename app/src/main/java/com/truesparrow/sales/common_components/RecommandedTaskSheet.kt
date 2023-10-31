package com.truesparrow.sales.common_components

import RecommandedTaskContainer
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.ui.theme.white
import com.truesparrow.sales.viewmodals.GlobalStateViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommandedTaskSheet(
    bottomSheetVisible: () -> Unit,
    onSelectUSerClick: (String) -> Unit,
    crmUserId : String?,
    crmUserName  : String?,
    taskDesc : String?,
    dueDate  : String?,
    accountName : String?,
    accountId : String?,
    id : String?,
    onCancelClick :(crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id :String) -> Unit,
    onAddTaskClick : (crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id :String, taskId: String) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    Log.i("Tag===", "Task: $taskDesc, Due Date: $dueDate, User ID: $crmUserId, User Name: $crmUserName")
    Log.i("Ids test", "${id}")

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
        RecommandedTaskContainer(
            bottomSheetVisible = bottomSheetVisible,
            accountId = accountId,
            accountName = accountName,
            id = id!!,
            crmUserId = crmUserId,
            crmUserName = crmUserName,
            taskDesc = taskDesc,
            dueDate = dueDate,
            onSelectUSerClick = onSelectUSerClick,
            onCancelClick = onCancelClick,
            onAddTaskClick = onAddTaskClick,
        )
    }
}