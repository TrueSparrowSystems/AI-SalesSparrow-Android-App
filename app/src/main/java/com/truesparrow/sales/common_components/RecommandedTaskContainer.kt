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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.common_components.UserAvatar
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.NoRippleInteractionSource
import com.truesparrow.sales.viewmodals.NotesViewModel
import com.truesparrow.sales.viewmodals.TasksViewModal
import java.util.Calendar
import java.util.Date

@Composable
fun RecommandedTaskContainer(
    accountId: String? = null,
    accountName: String? = null,
    bottomSheetVisible: () -> Unit,
    onSelectUSerClick: (String) -> Unit,
    id: String,
    crmUserId: String?,
    crmUserName: String?,
    taskDesc: String?,
    dueDate: String?,
    onCancelClick: (crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id: String) -> Unit,
    onAddTaskClick: (crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id: String, taskId: String) -> Unit
) {

    val tasksViewModel: TasksViewModal = hiltViewModel()
    val notesViewModel : NotesViewModel = hiltViewModel()

    var currTask = notesViewModel.getTaskById(id)

    var taskDescription by remember { mutableStateOf(taskDesc) }

    var createTaskApiInProgress by remember { mutableStateOf(false) }
    var createTaskApiIsSuccess by remember { mutableStateOf(false) }


    val createTaskResponse by tasksViewModel.tasksLiveData.observeAsState()
    val selectedDueDate = remember { mutableStateOf(dueDate) }

    createTaskResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                createTaskApiInProgress = false
                createTaskApiIsSuccess = true
                if (id.isNotEmpty()){
                    CustomToast(
                        message = "Task Added.", duration = Toast.LENGTH_SHORT, type = ToastType.Success
                    )
                    onAddTaskClick(
                        currTask?.crm_user_id ?: "",
                        currTask?.crm_user_name ?: "",
                        taskDescription!!,
                        selectedDueDate.value!!,
                        id,
                        createTaskResponse?.data?.task_id ?: ""
                    )
                    bottomSheetVisible()
                }
                Log.i("Task", "Task Created Successfully")

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

    val dueDateContext = LocalContext.current
    val dueDateYear: Int
    val dueDateMonth: Int
    val dueDateDay: Int

    val dueDateCalendar = Calendar.getInstance()
    dueDateYear = dueDateCalendar.get(Calendar.YEAR)
    dueDateMonth = dueDateCalendar.get(Calendar.MONTH)
    dueDateDay = dueDateCalendar.get(Calendar.DAY_OF_MONTH)

    dueDateCalendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        dueDateContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val formattedDay = String.format("%02d", mDayOfMonth)
            val formattedMonth = String.format("%02d", mMonth + 1)
            selectedDueDate.value = "$mYear-$formattedMonth-$formattedDay"
        }, dueDateYear, dueDateMonth, dueDateDay
    )

    mDatePickerDialog.datePicker.minDate = dueDateCalendar.timeInMillis

    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Cancel",
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
                        onClick = {
                            onCancelClick(
                                currTask?.crm_user_id ?: "",
                                currTask?.crm_user_name ?: "",
                                taskDescription!!,
                                selectedDueDate.value!!,
                                id
                            )
                            bottomSheetVisible()
                        })
                    .semantics {
                        contentDescription = if (createTaskApiInProgress) "" else ""
                    },
            )

            val buttonColor = if (createTaskApiIsSuccess) {
                Color(0xFF212653).copy(alpha = 0.7f)
            } else {
                Color(0xFF212653)
            }

            Button(onClick = {
                tasksViewModel.createTask(
                    accountId = accountId!!,
                    crmOrganizationUserId = crmUserId ?: "",
                    description = taskDescription ?: "",
                    dueDate = selectedDueDate.value ?: "",
                )
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
                        contentDescription = ""
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


                    if (createTaskApiInProgress || createTaskApiIsSuccess) {
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
                        )

                    }
                    Text(text = if (createTaskApiInProgress) {
                        "Saving Task..."
                    } else if (createTaskApiIsSuccess) {
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
                        contentDescription = if (createTaskApiIsSuccess) "" else ""
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

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
                    modifier = Modifier.width(64.dp),
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
                        width = 1.dp,
                        color = Color(0xFFE9E9E9),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .width(180.dp)

            ) {
                Button(onClick = {
                    onSelectUSerClick(id)
                },
                    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    interactionSource = NoRippleInteractionSource(),
                    modifier = Modifier.semantics {
                        contentDescription = ""
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (currTask?.crm_user_id?.isNotEmpty() == true) {
                            UserAvatar(
                                id = currTask?.crm_user_id ?: "",
                                firstName = currTask?.crm_user_name?.split(" ")?.get(0) ?: "",
                                lastName = currTask?.crm_user_name?.split(" ")?.get(0) ?: "",
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

                        val selectTabColor = if (crmUserId == "1") {
                            Color(0xff444A62)
                        } else {
                            Color(0xffdd1a77)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if(currTask?.crm_user_name?.isEmpty() == true) {
                                "Select"
                            }else{
                                currTask?.crm_user_name ?: ""
                                 },
                            color = selectTabColor,
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = customFontFamily,
                            ),
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
                    modifier = Modifier.width(64.dp),
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
                        width = 1.dp,
                        color = Color(0xFFE9E9E9),
                        shape = RoundedCornerShape(size = 4.dp)
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
                            contentDescription = "btn_select_account"
                        }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = selectedDueDate.value!!.ifEmpty {
                                dueDate!!.ifEmpty { "Select" }
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
        Spacer(modifier = Modifier.height(20.dp))
        EditableTextField(note = taskDescription!!, placeholderText = if (id == "1") {
            "Add task"
        } else {
            ""
        }, onValueChange = {
            taskDescription = it
        }, modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "et_create_note"
                testTag = "et_create_note"
            })

    }
}
