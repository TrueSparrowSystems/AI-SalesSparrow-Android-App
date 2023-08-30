package com.truesparrow.sales.common_components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.truesparrow.sales.models.Tasks
import com.truesparrow.sales.ui.theme.Typography
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.ui.theme.eastBay_70
import com.truesparrow.sales.ui.theme.walkaway_gray
import com.truesparrow.sales.viewmodals.GlobalStateViewModel
import com.truesparrow.sales.viewmodals.NotesViewModel
import com.truesparrow.sales.viewmodals.TasksViewModal

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchUserName(
    firstName: String,
    lastName: String,
    crmUserId: String,
    searchNameTestId: String,
    onAccountRowClick: () -> Unit,
    globalStateViewModel: GlobalStateViewModel,
    id: String
) {
    val noteViewModal: NotesViewModel = hiltViewModel()
    val currTask = noteViewModal.getTaskById(id)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(start = 20.dp, top = 11.dp, end = 10.dp, bottom = 11.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
                testTag = searchNameTestId
            }
            .clickable {
                noteViewModal.updateTaskById(
                    id, Tasks(
                        crm_user_id = crmUserId,
                        crm_user_name = firstName,
                        task_desc = currTask?.task_desc ?: "",
                        due_date = currTask?.due_date ?: "",
                        id = id
                    )
                )
                globalStateViewModel.setValuesById(id, crmUserName = firstName)
                globalStateViewModel.setValuesById(id, crmUserId = crmUserId)
                onAccountRowClick()
            }

        ) {

            UserAvatar(
                id = "crm_user_$firstName",
                firstName = firstName,
                lastName = lastName,
                size = 20.dp,
                textStyle = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 5.sp,
                ),
                userAvatarTestId = "txt_search_user_user_initials_${firstName}${lastName}"

            )
            Spacer(modifier = Modifier.width(4.dp))
            CustomText(
                text = "$firstName", Typography.labelMedium, color = walkaway_gray
            )
        }
    }

    Divider(
        color = eastBay_70, thickness = 0.5.dp, modifier = Modifier.height(1.dp)
    )
}

//@Composable
//@Preview(showBackground = true)
//fun SearchUserNamePreview() {
//    SearchUserName(firstName = "John",
//        lastName = "Doe",
//        crmUserId = "1234",
//        searchNameTestId = "accountRow",
//
//        onAccountRowClick = {})
//}
