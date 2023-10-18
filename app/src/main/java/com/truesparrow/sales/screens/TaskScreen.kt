package com.truesparrow.sales.screens

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.common_components.SearchNameBottomSheet
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.common_components.UserAvatar
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.NoRippleInteractionSource
import com.truesparrow.sales.viewmodals.TasksViewModal
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskScreen(
    accountId: String? = null,
    accountName: String? = null,
    taskDesc: String = "",
    dueDate: String = "",
    crmUserId: String = "",
    crmUserName: String = "",
    taskId: String = "",
    isTaskScreenEditable: Boolean = true,
    shouldNavigateBackToAccountDetailsScreen: Boolean = false
) {

    var taskDesc = taskDesc
    var crmUserId = crmUserId
    var dueDate = dueDate
    var crmUserName = crmUserName

    Log.i("TaskScreen", "Success $shouldNavigateBackToAccountDetailsScreen")


    var task by remember { mutableStateOf(taskDesc) }
    val tasksViewModel: TasksViewModal = hiltViewModel()
    if (taskId.isNotEmpty()) {
        tasksViewModel.setTasksScreenSelectedUserName(crmUserName)
        tasksViewModel.setTasksScreenSelectedUserId(crmUserId)
        tasksViewModel.setTaskScreenSelectedDueDate(dueDate)
        LaunchedEffect(key1 = taskId) {
            if (accountId != null) {
                tasksViewModel.taskDetails(accountId, taskId)
            }
        }
    }

    var createTaskApiInProgress by remember { mutableStateOf(false) }
    var createTaskApiIsSuccess by remember { mutableStateOf(false) }
    var updateTaskApiInProgress by remember { mutableStateOf(false) }
    var updateTaskApiIsSuccess by remember { mutableStateOf(false) }

    val updateTaskResponse by tasksViewModel.updateTaskLiveData.observeAsState()
    val taskDetailsApiResponse by tasksViewModel.taskDetails.observeAsState()

    taskDetailsApiResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                Log.i("TaskScreen", "Success ${response.data?.task_detail?.description}")
                val taskDetails = response.data?.task_detail
                taskDesc = taskDetails?.description ?: ""
            }

            is NetworkResponse.Error -> {
                CustomToast(
                    message = response.message ?: "Something went wrong",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                Log.d("TaskScreen", "Loading")
            }
        }
    }

    updateTaskResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                updateTaskApiInProgress = false
                updateTaskApiIsSuccess = true
                CustomToast(
                    message = "Task Updated.",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Success
                )
                Log.i("TaskScreen", "Success ${response.data} $shouldNavigateBackToAccountDetailsScreen")
                if (shouldNavigateBackToAccountDetailsScreen) {
                    NavigationService.navigateBackToAccountDetailsScreen()
                }
                Log.i("TaskScreen", "Success ${response.data}")
            }

            is NetworkResponse.Error -> {
                updateTaskApiInProgress = false
                CustomToast(
                    message = response.message ?: "Failed to update the task",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                updateTaskApiInProgress = true
                Log.d("TaskScreen", "Loading")
            }
        }
    }

    val createTaskResponse by tasksViewModel.tasksLiveData.observeAsState()

    createTaskResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                createTaskApiInProgress = false
                createTaskApiIsSuccess = true
                CustomToast(
                    message = "Task Added.", duration = Toast.LENGTH_SHORT, type = ToastType.Success
                )
                if (shouldNavigateBackToAccountDetailsScreen) {
                    NavigationService.navigateBackToAccountDetailsScreen()
                }
                Log.i("TaskScreen", "Success ${response.data}")
            }

            is NetworkResponse.Error -> {
                createTaskApiInProgress = false
                CustomToast(
                    message = response.message ?: "Failed to create the task",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                createTaskApiInProgress = true
                Log.d("TaskScreen", "Loading")
            }
        }
    }

    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {
        AddTaskHeader(
            createTaskApiInProgress = createTaskApiInProgress,
            createTasksApiIsSuccess = createTaskApiIsSuccess,
            updateTaskApiInProgress = updateTaskApiInProgress,
            updateTaskApiIsSuccess = updateTaskApiIsSuccess,
            accountId = accountId,
            taskId = taskId,
            taskDesc = task,
            isTaskScreenEditable = isTaskScreenEditable
        )
        if (taskDetailsApiResponse is NetworkResponse.Loading) {
            Loader()
        }
        Spacer(modifier = Modifier.height(20.dp))
        AddTaskContent(
            accountId = accountId,
            accountName = accountName,
            dueDate = dueDate,
            crmUserName = crmUserName,
        )
        Spacer(modifier = Modifier.height(20.dp))
        EditableTextField(note = task, placeholderText =
        "Add task", onValueChange = {
            task = it
        },
            readOnly = !isTaskScreenEditable  || createTaskApiIsSuccess  || updateTaskApiIsSuccess,
            modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "et_create_task"
                testTag = "et_create_task"
                testTagsAsResourceId = true
            })

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskContent(
    dueDate: String,
    accountId: String? = null,
    accountName: String? = null,
    crmUserName: String? = "",
) {


    val tasksViewModel: TasksViewModal = hiltViewModel()
    var crmUserName = tasksViewModel.getTasksScreenSelectedUserName();
    var crmUserId = tasksViewModel.getTasksScreenSelectedUserId()

    var searchNameBottomSheetVisible by remember { mutableStateOf(false) }

    val toggleSearchNameBottomSheet: () -> Unit = {
        searchNameBottomSheetVisible = !searchNameBottomSheetVisible
    }


    if (searchNameBottomSheetVisible) {
        SearchNameBottomSheet(
            toggleSearchNameBottomSheet,
            accountId = accountId!!,
            accountName = accountName!!,
            id = "",
            onUpdateUserName = { userId, userName ->
                tasksViewModel.setTasksScreenSelectedUserName(userName)
                tasksViewModel.setTasksScreenSelectedUserId(userId)
            }
        )
    }

    val dueDateContext = LocalContext.current
    val dueDateYear: Int
    val dueDateMonth: Int
    val dueDateDay: Int

    val dueDateCalendar = Calendar.getInstance()
    dueDateYear = dueDateCalendar.get(Calendar.YEAR)
    dueDateMonth = dueDateCalendar.get(Calendar.MONTH)
    dueDateDay = dueDateCalendar.get(Calendar.DAY_OF_MONTH)

    dueDateCalendar.time = Date()
    val selectedDueDate = remember { mutableStateOf(dueDate) }

    val mDatePickerDialog = DatePickerDialog(
        dueDateContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val formattedDay = String.format("%02d", mDayOfMonth)
            val formattedMonth = String.format("%02d", mMonth + 1)
            selectedDueDate.value = "$mYear-$formattedMonth-$formattedDay"
            tasksViewModel.setTaskScreenSelectedDueDate(selectedDueDate.value)
        }, dueDateYear, dueDateMonth, dueDateDay
    )
    mDatePickerDialog.datePicker.minDate = dueDateCalendar.timeInMillis

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Assign to",
                color = Color(0xff545A71),
                modifier = Modifier
                    .width(64.dp)
                    .semantics {
                        testTag = "txt_assign_to"
                        testTagsAsResourceId = true
                        contentDescription = "txt_assign_to"
                    },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,
                ),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .padding(all = 4.dp)
                .border(
                    width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
                )
                .width(180.dp)

        ) {
            Button(onClick = {
                toggleSearchNameBottomSheet()
            },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier.semantics {
                    testTag = "btn_select_user"
                    testTagsAsResourceId = true
                    contentDescription = if (crmUserId == "1") {
                        "btn_select_user"
                    } else {
                        "btn_select_user_${crmUserName}"
                    }
                }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (crmUserId.isNotEmpty()) {
                        UserAvatar(
                            id = crmUserId,
                            firstName = crmUserName.split(" ")[0],
                            lastName = crmUserName.split(" ")[0],
                            size = 18.dp,
                            textStyle = TextStyle(
                                fontSize = 5.24.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF000000),
                                letterSpacing = 0.21.sp,
                            ),
                            userAvatarTestId = "user_avatar_task_screen_$crmUserName"
                        )
                    }

                    val selectTabColor = if (crmUserId == "1") {
                        Color(0xff444A62)
                    } else {
                        Color(0xffdd1a77)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = crmUserName,
                        color = selectTabColor,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Vector 56",
                        tint = Color(0xff545a71)
                    )
                }

            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Due",
                color = Color(0xff545A71),
                modifier = Modifier
                    .width(64.dp)
                    .semantics {
                        testTag = "txt_due"
                        testTagsAsResourceId = true
                        contentDescription = "txt_due"
                    },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .padding(all = 4.dp)
                .border(
                    width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
                )

        ) {
            Button(onClick = {
                mDatePickerDialog.show()
            },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier
                    .width(180.dp)
                    .semantics {
                        testTag = "btn_select_due_date" + selectedDueDate.value
                            .replace("-", "/")
                            .ifEmpty {
                                dueDate
                                    .replace("-", "/")
                                    .ifEmpty {
                                        "Select"
                                    }
                            }

                        testTagsAsResourceId = true
                        contentDescription = "btn_select_account"
                    }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = selectedDueDate.value.replace("-", "/").ifEmpty {
                            dueDate.replace("-", "/").ifEmpty {
                                "Select"
                            }
                        },
                        color = Color(0xff444A62),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }

            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddTaskHeader(
    createTaskApiInProgress: Boolean,
    createTasksApiIsSuccess: Boolean,
    updateTaskApiInProgress: Boolean,
    updateTaskApiIsSuccess: Boolean,
    accountId: String? = null,
    taskDesc: String,
    taskId: String = "",
    isTaskScreenEditable: Boolean = true
) {

    val tasksViewModel: TasksViewModal = hiltViewModel()

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = if (createTasksApiIsSuccess) {
                "Done"
            } else {
                "Cancel"
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF5D678D),
                letterSpacing = 0.56.sp,
            ),
            modifier = Modifier
                .clickable(interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { NavigationService.navigateBack() })
                .semantics {
                    testTagsAsResourceId = true
                    testTag =
                        if (createTasksApiIsSuccess) "btn_done_create_task" else "btn_cancel_create_task"
                    contentDescription =
                        if (createTasksApiIsSuccess) "btn_done_create_task" else "btn_cancel_create_task"
                },
        )

        val buttonColor = if (createTasksApiIsSuccess) {
            Color(0xFF212653).copy(alpha = 0.7f)
        } else {
            Color(0xFF212653)
        }

        if (isTaskScreenEditable) {
            Button(onClick = {
                if (taskId.isNotEmpty()) {
                    tasksViewModel.updateTask(
                        accountId = accountId!!,
                        taskId = taskId,
                        crmOrganizationUserId = tasksViewModel.getTasksScreenSelectedUserId(),
                        description = taskDesc,
                        dueDate = tasksViewModel.getTaskScreenSelectedDueDate(),
                    )
                } else {
                    tasksViewModel.createTask(
                        accountId = accountId!!,
                        crmOrganizationUserId = tasksViewModel.getTasksScreenSelectedUserId(),
                        description = taskDesc,
                        dueDate = tasksViewModel.getTaskScreenSelectedDueDate(),
                    )
                }
            },
                enabled = true,
                contentPadding = PaddingValues(all = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.White
                ),
                modifier = Modifier
                    .background(
                        color = buttonColor, shape = RoundedCornerShape(size = 5.dp)
                    )
                    .width(112.dp)
                    .height(46.dp)
                    .clip(shape = RoundedCornerShape(size = 5.dp))
                    .semantics {
                        testTagsAsResourceId = true
                        testTag = "btn_save_task"
                        contentDescription = "btn_save_task"
                    }

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()


                    if (createTaskApiInProgress || createTasksApiIsSuccess || updateTaskApiInProgress || updateTaskApiIsSuccess) {
                        Image(
                            painter = if (createTaskApiInProgress) {
                                rememberAsyncImagePainter(R.drawable.loader, imageLoader)
                            } else {
                                painterResource(id = R.drawable.check)
                            },
                            contentDescription = "Loader",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .width(width = 12.dp)
                                .height(height = 12.dp)
                                .semantics {
                                    testTagsAsResourceId = true
                                    testTag = "task_screen_cloud"
                                }
                        )

                    }
                    Text(text = if (createTaskApiInProgress || updateTaskApiInProgress) {
                        "Saving Task..."
                    } else if (createTasksApiIsSuccess || updateTaskApiIsSuccess) {
                        "Saved"
                    } else {
                        "Save Task"
                    }, color = Color.White, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.48.sp,
                    ), modifier = Modifier.semantics {
                        if (createTasksApiIsSuccess) "txt_create_task_saved" else "txt_create_task_save"
                        contentDescription =
                            if (createTasksApiIsSuccess) "txt_create_task_saved" else "txt_create_task_save"
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    TaskScreen()
}