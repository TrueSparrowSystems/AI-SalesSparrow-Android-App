package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.AccountCard
import com.truesparrow.sales.common_components.CustomAlertDialog
import com.truesparrow.sales.common_components.CustomTextWithImage
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.EventCard
import com.truesparrow.sales.common_components.NotesCard
import com.truesparrow.sales.common_components.TasksCard
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.models.Event
import com.truesparrow.sales.models.EventDetailsObject
import com.truesparrow.sales.models.Note
import com.truesparrow.sales.models.NoteData
import com.truesparrow.sales.models.Task
import com.truesparrow.sales.models.TaskData
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.AccountDetailsViewModal


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountDetails(
    accountId: String,
    accountName: String
) {

    val accountDetailsViewModal: AccountDetailsViewModal = hiltViewModel()
    var isAccountNoteDetailsLoading by remember { mutableStateOf(false) };
    var isAccountTaskDetailsLoading by remember { mutableStateOf(false) };
    var isAccountEventsDetailsLoading by remember { mutableStateOf(false) };
    val openDialogForNote = remember { mutableStateOf(false) }
    val openDialogForTask = remember { mutableStateOf(false) }
    val openDialogForEvent = remember { mutableStateOf(false) }

    var notes = accountDetailsViewModal.notes.observeAsState()?.value
    var tasks = accountDetailsViewModal.tasks.observeAsState()?.value
    var events = accountDetailsViewModal.events.observeAsState()?.value

    val accountNotesResponse by accountDetailsViewModal.accountDetailsLiveData.observeAsState()

    val accountTasksResponse by accountDetailsViewModal.accountTasksLiveData.observeAsState()

    val deleteAccountNoteResponse by accountDetailsViewModal.deleteAccountNoteLiveData.observeAsState()

    val deleteAccountTaskResponse by accountDetailsViewModal.deleteAccountTaskLiveData.observeAsState()

    val accountEventsResponse by accountDetailsViewModal.accountEventsLiveData.observeAsState()

    val deleteAccountEventResponse by accountDetailsViewModal.deleteAccountEventLiveData.observeAsState()

    val noteId = remember { mutableStateOf("") }
    val taskId = remember { mutableStateOf("") }
    val eventId = remember { mutableStateOf("") }

    LaunchedEffect(key1 = accountId) {
        accountDetailsViewModal.getAccountNotes(accountId = accountId)
        accountDetailsViewModal.getAccountTasks(accountId = accountId)
        accountDetailsViewModal.getAccountEvents(accountId = accountId)
    }

    accountNotesResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                isAccountNoteDetailsLoading = false
                val newNotes = it.data?.note_ids?.map { noteId ->
                    val noteDetails = it.data?.note_map_by_id?.get(noteId)
                    Note(
                        creator = noteDetails?.creator ?: "",
                        id = noteDetails?.id ?: "",
                        last_modified_time = noteDetails?.last_modified_time ?: "",
                        text_preview = noteDetails?.text_preview ?: ""
                    )
                }

                accountDetailsViewModal.setNotes(newNotes ?: emptyList())
                Log.i("AccountDetails", "Success: ${it.data}")
            }

            is NetworkResponse.Error -> {
                isAccountNoteDetailsLoading = false
                Log.i("AccountDetails", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                isAccountNoteDetailsLoading = true
                Log.i("AccountDetails", "Loading")
            }

        }

    }

    accountTasksResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                isAccountTaskDetailsLoading = false
                val newTasks = it.data?.task_ids?.map { taskId ->
                    val taskDetails = it.data?.task_map_by_id?.get(taskId)
                    Task(
                        creator_name = taskDetails?.creator_name ?: "",
                        crm_organization_user_name = taskDetails?.crm_organization_user_name ?: "",
                        description = taskDetails?.description ?: "",
                        due_date = taskDetails?.due_date ?: "",
                        id = taskDetails?.id ?: "",
                        last_modified_time = taskDetails?.last_modified_time ?: "",
                        crm_organization_user_id = taskDetails?.crm_organization_user_id ?: ""
                    )
                }
                accountDetailsViewModal.setTasks(newTasks ?: emptyList())
                Log.i("AccountDetails", "Success: ${it.data}")
            }

            is NetworkResponse.Error -> {
                isAccountTaskDetailsLoading = false
                Log.i("AccountDetails", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                isAccountTaskDetailsLoading = true
                Log.i("AccountDetails", "Loading")
            }
        }
    }

    accountEventsResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                isAccountEventsDetailsLoading = false
                val newEvents = it.data?.event_ids?.map { eventId ->
                    val eventDetails = it.data?.event_map_by_id?.get(eventId)
                    Event(
                        creator_name = eventDetails?.creator_name ?: "",
                        description = eventDetails?.description ?: "",
                        end_datetime = eventDetails?.end_datetime ?: "",
                        id = eventDetails?.id ?: "",
                        last_modified_time = eventDetails?.last_modified_time ?: "",
                        start_datetime = eventDetails?.start_datetime ?: ""
                    )
                }
                accountDetailsViewModal.setEvents(newEvents ?: emptyList())
                Log.i("AccountDetails", "Success: ${it.data}")
            }

            is NetworkResponse.Error -> {
                isAccountEventsDetailsLoading = false
                Log.i("AccountDetails", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                isAccountEventsDetailsLoading = true
                Log.i("AccountDetails", "Loading")
            }
        }
    }

    deleteAccountNoteResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                Log.i("AccountDetails deleteAccount Success", "Success: ${it.data}")
                LaunchedEffect(key1 = accountId) {
                    accountDetailsViewModal.getAccountNotes(accountId = accountId)
                }
                CustomToast(message = "Note Deleted", type = ToastType.Success)
//                val updatedNotes = notes?.filter { note -> note.id != noteId.value }
//                accountDetailsViewModal.setNotes(updatedNotes ?: emptyList())
            }

            is NetworkResponse.Error -> {
                CustomToast(message = it.message ?: "Something went wrong", type = ToastType.Error)
                Log.i("AccountDetails deleteAccount Error", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                Log.i("AccountDetails deleteAccount Loading", "Loading")
            }
        }
    }

    deleteAccountTaskResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                Log.i("AccountDetails deleteAccount Success", "Success: ${it.data}")
                LaunchedEffect(key1 = accountId) {
                    accountDetailsViewModal.getAccountTasks(accountId = accountId)
                }
                CustomToast(message = "Task Deleted", type = ToastType.Success)
            }

            is NetworkResponse.Error -> {
                CustomToast(message = it.message ?: "Something went wrong", type = ToastType.Error)
                Log.i("AccountDetails deleteAccount Error", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                Log.i("AccountDetails deleteAccount Loading", "Loading")
            }
        }
    }

    deleteAccountEventResponse?.let {
        when(it){
            is NetworkResponse.Success -> {
                Log.i("AccountDetails deleteAccount Success", "Success: ${it.data}")
                LaunchedEffect(key1 = accountId) {
                    accountDetailsViewModal.getAccountEvents(accountId = accountId)
                }
                CustomToast(message = "Event Deleted", type = ToastType.Success)
            }

            is NetworkResponse.Error -> {
                CustomToast(message = it.message ?: "Something went wrong", type = ToastType.Error)
                Log.i("AccountDetails deleteAccount Error", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                Log.i("AccountDetails deleteAccount Loading", "Loading")
            }
        }
    }


    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {
        CustomAlertDialog(
            title = "Delete Note",
            message = "Are you sure you want to delete this note?",
            onConfirmButtonClick = {
                accountDetailsViewModal.deleteAccountNote(
                    accountId = accountId,
                    noteId = noteId.value
                )
                openDialogForNote.value = false
            },
            onDismissRequest = {
                openDialogForNote.value = false
            },
            showConfirmationDialog = openDialogForNote.value,
            titleTestTag = "txt_account_detail_delete_note_title",
            messageTestTag = "txt_account_detail_delete_note_message",
            confirmButtonTestTag = "btn_account_detail_delete_note_confirm",
            dismissButtonTestTag = "btn_account_detail_delete_note_cancel"
        )

        CustomAlertDialog(
            title = "Delete Task",
            message = "Are you sure you want to delete this task?",
            onConfirmButtonClick = {
                accountDetailsViewModal.deleteAccountTask(
                    accountId = accountId,
                    taskId = taskId.value
                )
                openDialogForTask.value = false
            },
            onDismissRequest = {
                openDialogForTask.value = false
            },
            showConfirmationDialog = openDialogForTask.value,
            titleTestTag = "txt_account_detail_delete_task_title",
            messageTestTag = "txt_account_detail_delete_task_message",
            confirmButtonTestTag = "btn_account_detail_delete_task_confirm",
            dismissButtonTestTag = "btn_account_detail_delete_task_cancel"
        )

        CustomAlertDialog(
            title = "Delete Event",
            message = "Are you sure you want to delete this event?",
            onConfirmButtonClick = {
                accountDetailsViewModal.deleteAccountEvent(
                    accountId = accountId,
                    eventId = eventId.value
                )
                openDialogForEvent.value = false
            },
            onDismissRequest = {
                openDialogForEvent.value = false
            },
            showConfirmationDialog = openDialogForEvent.value,
            titleTestTag = "txt_account_detail_delete_event_title",
            messageTestTag = "txt_account_detail_delete_event_message",
            confirmButtonTestTag = "btn_account_detail_delete_event_confirm",
            dismissButtonTestTag = "btn_account_detail_delete_event_cancel"
        )

        AccountDetailsHeader()
        ContactDetailsHeader()
        AccountCard(
            accountName,
            onAccountCardClick = {},
            "",
            "",
            textModifier = Modifier.semantics {
                testTagsAsResourceId = true
                testTag = "txt_account_detail_${accountName}"
            },
            accountCardTestTag = "",
            accountListCardWebsiteTestTag = "",
            accountListContactNameTestTag = "",
        )
        NotesDetailsHeader(accountId, accountName = accountName)

        if (isAccountNoteDetailsLoading) {
            Loader()
        } else if (notes?.isEmpty() == true || notes == null) {
            EmptyScreen(
                "Add notes and sync with your salesforce account",
                testId = "txt_account_detail_note_empty_screen"
            )
        } else {
            var index = 0;
            notes?.forEach { note ->
                NotesCard(
                    firsName = note.creator.split(" ")[0],
                    lastName = note.creator.split(" ")[1],
                    username = note.creator,
                    notes = note.text_preview,
                    date = note.last_modified_time,
                    noteId = note.id,
                    index = index++,
                    onClick = {
                        Log.i("AccountDetails", "NoteId: ${note.id}")
                        var noteData = NoteData(
                            id = note.id,
                            text = note.text_preview,
                            shouldShowCrmSuggestion = false,
                            isNoteScreenEditable = false
                        )
                        NavigationService.navigateToNotesScreen(accountId, accountName, false, noteData)
                    },
                    onDeleteMenuClick = { noteID ->
                        Log.i("AccountDetails onDeleteMenuClick", "NoteId: ${note.id}")
                        noteId.value = noteID
                        openDialogForNote.value = true
                    },
                    onEditMenuClick = {
                        Log.i("AccountDetails onEditMenuClick", "NoteId: ${note.id}")
                        var noteData = NoteData(
                            id = note.id,
                            text = note.text_preview,
                            shouldShowCrmSuggestion = false,
                            isNoteScreenEditable = true
                        )
                        NavigationService.navigateToNotesScreen(accountId, accountName, false, noteData)
                    },
                    editMenuTestTag = "btn_account_detail_edit_note_${index}",
                    deleteMenuTestTag = "btn_account_detail_delete_note_${index}"
                )
            }
        }

        TaskDetailsHeader(accountId, accountName = accountName)

        if (isAccountTaskDetailsLoading) {
            Loader()
        } else if (tasks?.isEmpty() == true || tasks == null) {
            EmptyScreen(
                "Add tasks, set due dates and assign to your team",
                testId = "txt_account_detail_task_empty_screen"
            )
        } else {
            var index = 0;
            tasks?.forEach { task ->
                TasksCard(
                    firsName = task.creator_name.split(" ")[0],
                    lastName = task.creator_name.split(" ")[1],
                    username = task.creator_name,
                    notes = task.description,
                    date = task.last_modified_time,
                    assignedTaskUserName = task.crm_organization_user_name,
                    dueDate = task.due_date,
                    index = index++,
                    onClick = {
                        Log.i("AccountDetails", "TaskId: ${task.id}")
                        var taskData = TaskData(
                            creator_name = task.creator_name,
                            crm_organization_user_name = task.crm_organization_user_name,
                            description = task.description,
                            due_date = task.due_date,
                            id = task.id,
                            crm_organization_user_id = task.crm_organization_user_id,
                            isTaskScreenEditable = false,
                            shouldNavigateBackToAccountDetailsScreen = true
                        )
                        NavigationService.navigateToTaskScreen(accountId, accountName, taskData)
                    },
                    taskId = task.id,
                    onDeleteMenuClick = { task ->
                        Log.i("AccountDetails onDeleteMenuClick", "TaskId: $task")
                        taskId.value = task
                        openDialogForTask.value = true
                    },
                    onEditMenuClick = {
                        Log.i("AccountDetails onEditMenuClick", "TaskId: $task")
                        var taskData = TaskData(
                            creator_name = task.creator_name,
                            crm_organization_user_name = task.crm_organization_user_name,
                            description = task.description,
                            due_date = task.due_date,
                            id = task.id,
                            crm_organization_user_id = task.crm_organization_user_id,
                            isTaskScreenEditable = true,
                            shouldNavigateBackToAccountDetailsScreen = true
                        )
                        NavigationService.navigateToTaskScreen(accountId, accountName, taskData)
                    },
                    editMenuTestTag = "btn_account_detail_edit_task_${index}",
                    deleteMenuTestTag = "btn_account_detail_delete_task_${index}"
                )
            }
        }

        EventDetailsHeader(accountId, accountName = accountName)

        if (isAccountEventsDetailsLoading) {
            Loader()
        } else if (events?.isEmpty() == true || events == null) {
            EmptyScreen(
                emptyText = "Setup events, meetings and loop in your team",
                testId = "txt_account_detail_event_empty_screen"
            )
        } else {
            var index = 0;
            events?.forEach { event ->
                Log.i("AccountDetails", "Event: ${event.id} ${event.end_datetime} ")
                EventCard(
                    firsName = event.creator_name.split(" ")[0],
                    lastName = event.creator_name.split(" ")[1],
                    username = event.creator_name,
                    notes = event.description,
                    date = event.last_modified_time,
                    startDateTime = event.start_datetime,
                    endDateTime = event.end_datetime,
                    dueDate = event.start_datetime,
                    index = index++,
                    onClick = {
                        Log.i("AccountDetails", "EventId: ${event.id}")
                        var eventData = EventDetailsObject(
                            eventId = event.id,
                            eventStartDate = event.start_datetime,
                            eventEndDate = event.end_datetime,
                            eventDescription = event.description,
                            isEventScreenEditable = false,
                            shouldNavigateBackToAccountDetailsScreen = true
                        )
                        NavigationService.navigateToEventScreen(accountId,accountName, eventData)
                    },
                    eventId = event.id,
                    onDeleteMenuClick = { task ->
                        Log.i("AccountDetails onDeleteMenuClick", "EventId: $task")
                        eventId.value = task
                        openDialogForEvent.value = true
                    },
                    onEditMenuClick = { task ->
                        Log.i("AccountDetails onEditMenuClick", "EventId: $task")
                        var eventData = EventDetailsObject(
                            eventId = event.id,
                            eventStartDate = event.start_datetime,
                            eventEndDate = event.end_datetime,
                            eventDescription = event.description,
                            isEventScreenEditable = true,
                            shouldNavigateBackToAccountDetailsScreen = true
                        )
                        NavigationService.navigateToEventScreen(accountId,accountName, eventData)
                    }
                )
            }
        }
    }
}

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )
    }
}

@Composable
fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 5f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx() / 2,
                size.height - width.toPx() / 2,
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmptyScreen(
    emptyText: String,
    shouldShowIcon: Boolean = false,
    height: Dp = 40.dp,
    testId: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .dashedBorder(1.dp, 5.dp, Color(0xFF545A71))
            .padding(0.75.dp)
            .fillMaxWidth()
            .height(height)
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
    ) {

        if (shouldShowIcon) {
            Image(
                painter = painterResource(id = R.drawable.check_marked),
                contentDescription = "check_marked",
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp)
            )
        }

        Text(
            text = emptyText,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(400),
                fontStyle = FontStyle.Italic,
                color = Color(0xFF545A71),
                textAlign = TextAlign.Center,
                letterSpacing = 0.48.sp,
            ),
           modifier = Modifier.semantics {
                testTagsAsResourceId = true
                testTag = testId
                contentDescription = testId
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskDetailsHeader(
    accountId: String,
    accountName: String,
    isAccountSelectionEnabled: Boolean? = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.tasks,
            imageContentDescription = "buildings",
            imageModifier = Modifier
                .width(17.dp)
                .height(17.dp)
                .semantics {
                    testTag = "img_account_detail_task_icon"
                    contentDescription = "img_account_detail_task_icon"
                    testTagsAsResourceId = true
                },
            text = "Tasks",
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF212653),
            ),
            textModifier = Modifier
                .semantics {
                    testTag = "txt_account_detail_task_title"
                    contentDescription = "txt_account_detail_task_title"
                    testTagsAsResourceId = true
                }
        )
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "add_tasks",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .semantics {
                    testTag = "btn_account_detail_add_task"
                    testTagsAsResourceId = true
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    val taskData = TaskData(
                        creator_name = "",
                        crm_organization_user_name = "",
                        description = "",
                        due_date = "",
                        id = "",
                        crm_organization_user_id = "",
                        isTaskScreenEditable = true,
                        shouldNavigateBackToAccountDetailsScreen = true
                    )
                    NavigationService.navigateToTaskScreen(accountId, accountName, taskData)
                }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EventDetailsHeader(
    accountId: String,
    accountName: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.events,
            imageContentDescription = "buildings",
            imageModifier = Modifier
                .width(17.dp)
                .height(17.dp)
                .semantics {
                    testTag = "img_account_detail_event_icon"
                    contentDescription = "img_account_detail_event_icon"
                    testTagsAsResourceId = true
                },
            text = "Events",
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF212653),
            ),
            textModifier = Modifier
                .semantics {
                    testTag = "txt_account_detail_event_title"
                    contentDescription = "txt_account_detail_event_title"
                    testTagsAsResourceId = true
                }
        )
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "add_tasks",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .semantics {
                    testTag = "btn_account_detail_add_event"
                    testTagsAsResourceId = true
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {

                    val eventData = EventDetailsObject(
                        eventId = "",
                        eventStartDate = "",
                        eventEndDate = "",
                        eventDescription = "",
                        isEventScreenEditable = true,
                        shouldNavigateBackToAccountDetailsScreen = true
                    )
                    NavigationService.navigateToEventScreen(accountId, accountName,eventData)
                }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesDetailsHeader(
    accountId: String,
    accountName: String,
    isAccountSelectionEnabled: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.notes,
            imageContentDescription = "buildings",
            imageModifier = Modifier
                .width(17.dp)
                .height(17.dp)
                .semantics {
                    testTag = "img_account_detail_note_icon"
                    contentDescription = "img_account_detail_note_icon"
                    testTagsAsResourceId = true
                },
            text = "Notes",
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF212653),
            ),
            textModifier = Modifier
                .semantics {
                    testTag = "txt_account_detail_notes_title"
                    contentDescription = "txt_account_detail_notes_title"
                    testTagsAsResourceId = true
                }
        )
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "img_account_detail_create_note_icon",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .semantics {
                    testTag = "btn_account_detail_add_note"
                    testTagsAsResourceId = true
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    NavigationService.navigateToNotesScreen(accountId, accountName, isAccountSelectionEnabled, null)
                }
        )
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContactDetailsHeader() {
    CustomTextWithImage(
        imageId = R.drawable.buildings,
        imageContentDescription = "buildings",
        imageModifier = Modifier
            .width(17.dp)
            .height(17.dp)
            .semantics {
                testTagsAsResourceId = true
                testTag = "img_account_detail_account_icon"
                contentDescription = "img_account_detail_account_icon"
            },
        text = "Account Details",
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight(600),
            color = Color(0xFF212653),
        ),
        textModifier = Modifier.semantics {
            testTagsAsResourceId = true
            testTag = "txt_account_detail_account_details_title"
            contentDescription = "txt_account_detail_account_details_title"
        })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AccountDetailsHeader() {
    CustomTextWithImage(
        imageId = R.drawable.arrow_left,
        imageContentDescription = "arrow-left",
        imageModifier = Modifier
            .width(24.dp)
            .height(24.dp)
            .semantics {
                testTag = "btn_account_detail_back"
                testTagsAsResourceId = true
            },
        text = "Details",
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight(600),
            color = Color(0xFF212653),
        ),
        textModifier = Modifier.semantics {
            testTag = "txt_account_detail_account_details_title"
            testTagsAsResourceId = true
        },
        onClick = {
            NavigationService.navigateBack();
        }
    )
}