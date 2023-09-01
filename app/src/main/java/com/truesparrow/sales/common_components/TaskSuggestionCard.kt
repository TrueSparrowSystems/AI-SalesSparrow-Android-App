package com.truesparrow.sales.common_components

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
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
import com.truesparrow.sales.models.Tasks
import com.truesparrow.sales.viewmodals.AuthenticationViewModal
import com.truesparrow.sales.viewmodals.NotesViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskSuggestionCard(
    id: String,
    onDeleteTaskClick: (id: String) -> Unit,
    onSelectUSerClick: (id: String) -> Unit,
    onEditTaskClick: (id: String) -> Unit,
    accountId: String,
    accountName: String,
    crmUserId: String,
    crmUserName: String,
    taskDesc: String,
    dDate: String,
    shouldShowOptions: Boolean = false,
    onCancelTaskClick: (id: String) -> Unit,
    onAddTaskClick: (
        crmOrganizationUserId: String, description: String, dueDate: String, id : String
    ) -> Unit,
    createTaskApiInProgress: Boolean = false,
    isTaskAdded: Boolean = false,
    noteViewModal: NotesViewModel
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val authenticationViewModal: AuthenticationViewModal = hiltViewModel()
    val currentUser = authenticationViewModal.currentUserLiveData?.observeAsState()?.value
    val currentUserName = currentUser?.data?.current_user?.name ?: "John ve"

    Log.i(
        "Task Sugg===",
        "Task: $taskDesc, isTaskAdded: $isTaskAdded, User ID: $crmUserId, User Name: $crmUserName id : $id dDate : $dDate"
    )


    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current


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
            val formattedMonth = String.format("%02d", mMonth + 1)
            val formattedDay = String.format("%02d", mDayOfMonth)
            dueDate.value = "$mYear-$formattedMonth-$formattedDay"

            Log.i("log2--------------","${id}")
            noteViewModal.updateTaskById(
                id, Tasks(
                    crm_user_id = crmUserId,
                    crm_user_name = crmUserName,
                    task_desc = taskDesc,
                    due_date =  dueDate.value ,
                    id = id,
                    is_task_created = false
                )
            )
        }, dueDateYear, dueDateMonth, dueDateDay
    )

    mDatePickerDialog.datePicker.minDate = dueDateCalendar.timeInMillis


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
                        firstName = currentUserName!!.split(" ")[0],
                        lastName = currentUserName!!.split(" ")[1],
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
                        text = currentUserName, style = TextStyle(
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
                        text = "Just Now", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    if (shouldShowOptions) {
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
                                onDeleteMenuClick = {
                                    onDeleteTaskClick(id)
                                }
                            )
                        }
                    }
                }

            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
                    .clickable {
                        if (!isTaskAdded) {
                            Log.i("log3--------------", "${id}")
                            noteViewModal.updateTaskById(
                                id, Tasks(
                                    crm_user_id = crmUserId,
                                    crm_user_name = crmUserName,
                                    task_desc = taskDesc,
                                    due_date = dDate,
                                    id = id,
                                    is_task_created = false
                                )
                            )
                            onEditTaskClick(id)
                        }
                    }

            ) {
                Text(
                    text = taskDesc.ifEmpty {
                        "Select"
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
                    modifier = Modifier
                        .clickable {
                            Log.i("isTaskAdded=== ", "${isTaskAdded} ${id}")
                            if (!isTaskAdded) {
                                onSelectUSerClick(id)
                            }
                        }
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_create_note_assign_to"
                            contentDescription = "btn_create_note_assign_to"
                        }) {

                    Text(text = "Assign to", style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.48.sp,
                    ), modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "txt_create_note_assign_to"
                        contentDescription = "txt_create_note_assign_to"
                    })

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
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
                            userAvatarTestId = "user_avatar_note_details"
                        )
                    }
                    Text(text = crmUserName.ifEmpty {
                        "Select"
                    }, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFFDD1A77),
                        letterSpacing = 0.48.sp,
                    ), modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = if (crmUserName.isEmpty()) {
                            "txt_create_note_select_account"
                        } else {
                            "txt_create_note_selected_account"
                        }
                        contentDescription = if (crmUserName.isEmpty()) {
                            "txt_create_note_select_account"
                        } else {
                            "txt_create_note_selected_account"
                        }
                    })
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.up_arrow),
                        contentDescription = "Up arrow",
                        modifier = Modifier
                            .width(8.dp)
                            .height(8.dp)
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
                    modifier = Modifier
                        .clickable {
                            if (!isTaskAdded) {
                                mDatePickerDialog.show()
                            }
                        }
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_create_note_due"
                            contentDescription = "btn_create_note_due"
                        }) {
                    Text(text = "Due", style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.48.sp,
                    ), modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "txt_create_note_due"
                        contentDescription = "txt_create_note_due"
                    })

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    Text(
                        text = if (dDate.isEmpty()) {
                            "Select"
                        } else {
                            dDate.replace("-", "/")
                        }, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            if (!isTaskAdded) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        onAddTaskClick(
                            crmUserId,
                            taskDesc,
                            dDate,
                            id
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
                                testTagsAsResourceId = true
                                testTag = if (createTaskApiInProgress) {
                                    "txt_create_note_adding_task"
                                } else {
                                    "btn_create_note_add_task"
                                }
                                contentDescription = if (createTaskApiInProgress) {
                                    "txt_create_note_adding_task"
                                } else {
                                    "btn_create_note_add_task"
                                }
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
                                    painter = rememberAsyncImagePainter(
                                        R.drawable.loader, imageLoader
                                    ),
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
                                testTagsAsResourceId = true
                                testTag = if (!createTaskApiInProgress) {
                                    "txt_create_note_add_task"
                                } else {
                                    "txt_create_note_adding_task"
                                }
                                contentDescription = if (!createTaskApiInProgress) {
                                    "txt_create_note_add_task"
                                } else {
                                    "txt_create_note_adding_task"
                                }
                            })
                        }
                    }
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .border(1.dp, Color(0xFF5D678D), shape = RoundedCornerShape(4.dp))
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_create_note_cancel"
                            contentDescription = "btn_create_note_cancel"
                        }

                    ) {
                        Text(text = "Cancel", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF5D678D),
                            letterSpacing = 0.48.sp,
                        ), modifier = Modifier
                            .padding(8.dp)
                            .semantics {
                                testTagsAsResourceId = true
                                testTag = "txt_create_note_cancel"
                                contentDescription = "txt_create_note_cancel"
                            }
                            .clickable {
                                onCancelTaskClick(id)
                            })

                    }
                }
            }
        }
    }
    if (isTaskAdded){
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color(0x3362E17D))
                    .border(width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 0.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.success_toast_check),
                    contentDescription = "Success",
                    modifier = Modifier
                        .height(18.dp)
                        .width(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Task added", modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "txt_create_note_task_added"
                        contentDescription = "txt_create_note_task_added"
                    }, style = TextStyle(
                        fontSize = 12.sp, lineHeight = 24.sp, color = Color(0xFF444A62)
                    )
                )
            }
        }
    }
}
