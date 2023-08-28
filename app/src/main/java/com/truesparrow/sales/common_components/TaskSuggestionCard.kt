package com.truesparrow.sales.common_components

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.GlobalStateViewModel
import com.truesparrow.sales.viewmodals.TasksViewModal
import java.util.Calendar
import java.util.Date

@Composable
fun TaskSuggestionCard(
    id: String,
    taskTitle: String = "",
    crmUserId: String = "",
    crmUserName: String = "",
    dueDate: String = "",
    onDeleteTaskClick: () -> Unit,
    accountId: String,
    accountName: String,
    globalStateViewModel: GlobalStateViewModel,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val taskDesc = globalStateViewModel.getTaskDescById(id)?.value ?: ""
    val userId = globalStateViewModel.getCrmUserIdById(id)?.value ?: ""
    val userName = globalStateViewModel.getCrmUserNameById(id)?.value ?: "Select"
    val dDate = globalStateViewModel.getDueDateById(id)?.value ?: "Select"


    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    var searchNameBottomSheetVisible by remember { mutableStateOf(false) }

    val toggleSearchNameBottomSheet: () -> Unit = {
        searchNameBottomSheetVisible = !searchNameBottomSheetVisible
    }


    if (searchNameBottomSheetVisible) {
        SearchNameBottomSheet(
            toggleSearchNameBottomSheet,
            globalStateViewModel = globalStateViewModel,
            accountId = accountId,
            id = id,
            accountName = accountName!!
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
    val dueDate = remember { mutableStateOf(dDate) }

    val mDatePickerDialog = DatePickerDialog(
        dueDateContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dueDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, dueDateYear, dueDateMonth, dueDateDay
    )

    globalStateViewModel.setValuesById(id, dueDate = dueDate.value)


    val tasksViewModel: TasksViewModal = hiltViewModel()
    var createTaskApiInProgress by remember { mutableStateOf(false) }
    var createTaskApiIsSuccess by remember { mutableStateOf(false) }


    val createTaskResponse by tasksViewModel.tasksLiveData.observeAsState()

    createTaskResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                createTaskApiInProgress = false;
                createTaskApiIsSuccess = true
                CustomToast(
                    message = "Task Added.", duration = Toast.LENGTH_SHORT, type = ToastType.Success
                )
            }

            is NetworkResponse.Error -> {
                createTaskApiInProgress = false;
                CustomToast(
                    message = response.message ?: "Failed to create the task",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )
            }

            is NetworkResponse.Loading -> {
                createTaskApiInProgress = true;
                Log.d("TaskScreen", "Loading")
            }
        }
    }




    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
            )
            .border(
                width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(0.75.dp)
            .fillMaxWidth()
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
            .onSizeChanged {
                itemHeight = with(density) { (it.height).toDp() }
            }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserAvatar(
                        id = "1",
                        firstName = "D",
                        lastName = "S",
                        size = 18.dp,
                        textStyle = TextStyle(
                            fontSize = 5.24.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            letterSpacing = 0.21.sp,
                        ),
                        userAvatarTestId = "user_avatar_note_details"
                    )
                    Text(
                        text = "John Doe", style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.56.sp,
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tuesday, 5:49pm", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Box {
                        Image(painter = painterResource(id = R.drawable.setting_three_dots_outline),
                            contentDescription = "menu",
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .pointerInput(true) {
                                    detectTapGestures(onPress = {
                                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                        expanded = true
                                    })
                                })

                        CustomDropDownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            onDeleteMenuClick = onDeleteTaskClick
                        )
                    }
                }

            }

            val modifiedDate = dDate.replace("/", "-")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
                    .clickable {
                        NavigationService.navigateTo(
                            "task_screen/${id}/${userId}/${userName}/${modifiedDate}"
                        )
                    }

            ) {
                Text(
                    text = taskDesc.ifEmpty {
                        taskTitle.ifEmpty {
                            "Select"
                        }
                    }, style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.64.sp,
                    )
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(
                        color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(8.dp)

            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        toggleSearchNameBottomSheet()
                    }) {

                    Text(
                        text = "Assign to", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    UserAvatar(
                        id = "1",
                        firstName = "D",
                        lastName = "S",
                        size = 17.dp,
                        textStyle = TextStyle(
                            fontSize = 5.24.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            letterSpacing = 0.21.sp,
                        ),
                        userAvatarTestId = "user_avatar_search_user"
                    )
                    Text(
                        text = userName.ifEmpty {
                            crmUserName.ifEmpty { "Select" }
                        }, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFDD1A77),
                            letterSpacing = 0.48.sp,
                        )
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .background(
                        color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(8.dp)
                    .width(160.dp)

            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { mDatePickerDialog.show() }) {

                    Text(
                        text = "Due", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    Text(
                        text = dDate.ifEmpty {
                            dueDate.value.replace("-", "/").ifEmpty { "Select" }
                        }, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    createTaskApiInProgress = true
                    tasksViewModel.createTask(
                        accountId = accountId!!,
                        crmOrganizationUserId = userId,
                        description = taskDesc,
                        dueDate = dDate,
                    )
                },
                    contentPadding = PaddingValues(all = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, contentColor = Color.White
                    ),
                    modifier = Modifier
                        .background(
                            color = Color(0xFF212653), shape = RoundedCornerShape(size = 5.dp)
                        )
                        .height(32.dp)
                        .clip(shape = RoundedCornerShape(size = 5.dp))
                        .semantics {
                            var contentDescription = ""
                        }

                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            4.dp, Alignment.CenterHorizontally
                        ), verticalAlignment = Alignment.CenterVertically

                    ) {
                        val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                            if (Build.VERSION.SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }.build()

                        if (createTaskApiInProgress) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.loader, imageLoader),
                                contentDescription = "Loader",
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier
                                    .width(width = 12.dp)
                                    .height(height = 12.dp)
                            )
                        }

                        Text(text = if (createTaskApiInProgress) {
                            "Adding Task..."
                        } else {
                            "Add Task"
                        }, color = Color.White, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp,
                        ), modifier = Modifier.semantics {
                            contentDescription = ""
                        })
                    }
                }
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .border(1.dp, Color(0xFF5D678D), shape = RoundedCornerShape(4.dp))
                ) {
                    Text(text = "Cancel",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF5D678D),
                            letterSpacing = 0.48.sp,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { /* Handle button click */ })
                }
            }
        }
    }
}