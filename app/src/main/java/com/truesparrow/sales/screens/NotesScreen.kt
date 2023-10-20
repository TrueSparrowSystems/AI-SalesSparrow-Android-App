package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.AccountListBottomSheet
import com.truesparrow.sales.common_components.CustomAlertDialog
import com.truesparrow.sales.common_components.CustomTextWithImage
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.common_components.EventCard
import com.truesparrow.sales.common_components.EventSuggestionCard
import com.truesparrow.sales.common_components.RecommandedTaskSheet
import com.truesparrow.sales.common_components.RecommondedEventSheet
import com.truesparrow.sales.common_components.SearchNameBottomSheet
import com.truesparrow.sales.common_components.TaskSuggestionCard
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.common_components.UpdateTaskDropDownMenu
import com.truesparrow.sales.models.EventDetailsObject
import com.truesparrow.sales.models.SuggestedEvents
import com.truesparrow.sales.models.Tasks
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.NoRippleInteractionSource
import com.truesparrow.sales.util.convertToISO8601
import com.truesparrow.sales.util.extractDateAndTime
import com.truesparrow.sales.viewmodals.AuthenticationViewModal
import com.truesparrow.sales.viewmodals.NotesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(
    accountName: String? = null,
    accountId: String? = null,
    isAccountSelectionEnabled: Boolean = false,
    noteId: String? = "",
    noteDescription: String = "",
    shouldShowCrmSuggestions: Boolean = false,
    isNotesScreenEditable: Boolean = true,
) {

    val notesViewModel: NotesViewModel = hiltViewModel()
    val authenticationViewModal: AuthenticationViewModal = hiltViewModel()
    var selectedTaskId by remember { mutableStateOf("") }
    var selectedEventId by remember { mutableStateOf("") }
    var addTaskId by remember { mutableStateOf("") }
    var addEventId by remember { mutableStateOf("") }
    val currentUser = authenticationViewModal.currentUserLiveData?.observeAsState()?.value

    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }
    var updateNoteApiInProgress by remember { mutableStateOf(false) }
    var updateNoteApiIsSuccess by remember { mutableStateOf(false) }


    val getCrmActionsResponse by notesViewModel.getCrmActionsLiveData.observeAsState()
    var getCrmActionLoading by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val saveNoteRespose by notesViewModel.notesLiveData.observeAsState()
    val updateNoteRespose by notesViewModel.updateNoteLiveData.observeAsState()


    var createTaskApiInProgress by remember { mutableStateOf(false) }
    var createTaskApiIsSuccess by remember { mutableStateOf(false) }
    var createEventApiInProgress by remember { mutableStateOf(false) }
    var createEventApiIsSuccess by remember { mutableStateOf(false) }

    var noteDescription = noteDescription

    var noteDesc by remember { mutableStateOf(noteDescription) }

    var tasks = notesViewModel.tasks.observeAsState()?.value

    var isTasksListUpdated by remember { mutableStateOf(false) }

    val createTaskResponse by notesViewModel.tasksLiveData.observeAsState()

    val deleteTaskResponse by notesViewModel.deleteAccountTaskLiveData.observeAsState();

    val createEventResponse by notesViewModel.eventsLiveData.observeAsState();

    val deleteEventResponse by notesViewModel.deleteAccountEventLiveData.observeAsState();

    val openDeleteDialogForEvent = remember { mutableStateOf(false) }
    val openDeleteDialogForTask = remember { mutableStateOf(false) }


    var selectedCrmUserName = remember {
        mutableStateOf("")
    }

    var selectedCrmUserId = remember {
        mutableStateOf("")
    }

    var suggestedEvents = notesViewModel.suggestedEvents.observeAsState()?.value

    if (noteId != null && accountId != null) {
        if (noteId.isNotEmpty()) {
            LaunchedEffect(key1 = noteId) {
                notesViewModel.getNoteDetails(accountId, noteId)
            }
        }
    }

    val noteDetailsResponse by notesViewModel.noteDetailsLiveData.observeAsState()

    noteDetailsResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                val noteDetails = it.data?.note_detail
                noteDescription = noteDetails?.text ?: ""
            }

            is NetworkResponse.Error -> {
                CustomToast(message = "Something went wrong", type = ToastType.Error)
            }

            is NetworkResponse.Loading -> {
            }
        }
    }


    deleteEventResponse?.let {
        deleteEventResponse
        when (deleteEventResponse) {
            is NetworkResponse.Success -> {
                CustomToast(message = "Event Deleted", type = ToastType.Success)
            }

            is NetworkResponse.Error -> {
                CustomToast(message = "Something went wrong", type = ToastType.Error)
            }

            is NetworkResponse.Loading -> {
            }

            else -> {}
        }
    }



    deleteTaskResponse?.let {
        deleteTaskResponse
        when (deleteTaskResponse) {
            is NetworkResponse.Success -> {
                CustomToast(message = "Task Deleted", type = ToastType.Success)
            }

            is NetworkResponse.Error -> {
                CustomToast(message = "Something went wrong", type = ToastType.Error)
            }

            is NetworkResponse.Loading -> {
            }

            else -> {}
        }
    }

    createEventResponse?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                createEventApiInProgress = false;
                createEventApiIsSuccess = true
                val currEvent = notesViewModel.getSuggestedEventById(addEventId)
                CustomToast(message = "Event Added", type = ToastType.Success)
                Log.i("log4--------------", "${currEvent}")
                notesViewModel.updateSuggestedEventById(
                    addEventId, SuggestedEvents(
                        start_datetime = currEvent?.start_datetime ?: "",
                        end_datetime = currEvent?.end_datetime ?: "",
                        description = currEvent?.description ?: "",
                        id = addEventId,
                        is_event_created = true,
                        event_id = response.data?.event_id
                    )
                )

            }

            is NetworkResponse.Error -> {
                createEventApiInProgress = false;
                CustomToast(message = "Something went wrong", type = ToastType.Error)
            }

            is NetworkResponse.Loading -> {
                createEventApiInProgress = true;
            }
        }
    }

    createTaskResponse?.let { response ->
        Log.i("response====", "${createTaskResponse}")
        when (response) {
            is NetworkResponse.Success -> {
                createTaskApiInProgress = false;
                createTaskApiIsSuccess = true
                val currTask = notesViewModel.getTaskById(addTaskId)
                Log.i("log4--------------", "${currTask}")

                CustomToast(
                    message = "Task Added and assigned to ${currTask?.crm_user_name}",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Success
                )

                notesViewModel.updateTaskById(
                    addTaskId, Tasks(
                        crm_user_id = currTask?.crm_user_id ?: "",
                        crm_user_name = currTask?.crm_user_name ?: "",
                        task_desc = currTask?.task_desc ?: "",
                        due_date = currTask?.due_date ?: "",
                        id = addTaskId,
                        is_task_created = true,
                        task_id = response.data?.task_id
                    )
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

    getCrmActionsResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                getCrmActionLoading = false
                var index = 0;
                val newTasks = (it.data?.add_task_suggestions?.map { task ->
                    val id = "temp_task_${index++}_${System.currentTimeMillis()}"
                    Tasks(
                        crm_user_id = "",
                        crm_user_name = "",
                        due_date = task?.due_date ?: "",
                        task_desc = task?.description ?: "",
                        id = id,
                        is_task_created = false,
                        task_id = ""
                    )
                })

                val newEvents = (it.data?.add_event_suggestions?.map { event ->
                    val id = "temp_event_${index++}_${System.currentTimeMillis()}"
                    SuggestedEvents(
                        start_datetime = event?.start_datetime ?: "",
                        end_datetime = event?.end_datetime ?: "",
                        description = event?.description ?: "",
                        id = id,
                        is_event_created = false,
                        event_id = ""
                    )
                })


                Log.i("newTasks", "$newTasks")
                if (!isTasksListUpdated) {
                    isTasksListUpdated = true
                    Log.i("Heelo", "Hello")
                    notesViewModel.setTasks(newTasks ?: emptyList())
                    notesViewModel.setSuggestedEvents(newEvents ?: emptyList())
                } else {
                }
            }

            is NetworkResponse.Error -> {
                getCrmActionLoading = false

                Log.d("NotesScreen", "Error")
            }

            is NetworkResponse.Loading -> {
                getCrmActionLoading = true
                Log.d("NotesScreen", "Loading")
            }
        }
    }

    saveNoteRespose?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                saveNoteApiInProgress = false;
                saveNoteApiIsSuccess = true
                CustomToast(
                    message = "Note is saved to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Success
                )
                LaunchedEffect(true) {
                    if (shouldShowCrmSuggestions) {
                        notesViewModel.getCrmActions(noteDesc);
                    }
                }
            }

            is NetworkResponse.Error -> {
                saveNoteApiInProgress = false;
                CustomToast(
                    message = response.message
                        ?: "Failed to save the note to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                saveNoteApiInProgress = true;
                Log.d("NotesScreen", "Loading")
            }
        }
    }

    updateNoteRespose?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                updateNoteApiInProgress = false;
                updateNoteApiIsSuccess = true;
                Log.i("NotesScreen", "Success : ${response.data}")
                CustomToast(
                    message = "Note is updated to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Success
                )
            }

            is NetworkResponse.Error -> {
                updateNoteApiInProgress = false;
                CustomToast(
                    message = response.message
                        ?: "Failed to update the note to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                updateNoteApiInProgress = true;
                Log.d("NotesScreen", "Loading")
            }
        }
    }

    var searchNameBottomSheetVisible by remember { mutableStateOf(false) }

    val toggleSearchNameBottomSheet: () -> Unit = {
        searchNameBottomSheetVisible = !searchNameBottomSheetVisible
    }


    var RecommTaksBottomSheetVisible by remember { mutableStateOf(false) }

    var RecommEventBottomSheetVisible by remember { mutableStateOf(false) }

    val toggleSheet: () -> Unit = {
        RecommTaksBottomSheetVisible = !RecommTaksBottomSheetVisible
    }

    val toggleEventSheet: () -> Unit = {
        RecommEventBottomSheetVisible = !RecommEventBottomSheetVisible
    }

    CustomAlertDialog(
        title = "Delete Event",
        message = "Are you sure you want to delete this event?",
        onConfirmButtonClick = {
            if (accountId != null) {
                val event = suggestedEvents!!.filter { it?.id == selectedEventId }
                Log.i("event", "${event} ${selectedEventId} ${selectedEventId}")
                event[0]?.event_id?.let {
                    Log.i("event id", "${it}")
                    notesViewModel.deleteAccountEvent(
                        accountId = accountId,
                        eventId = it
                    )
                }
                var updatedSuggestedEvents = suggestedEvents!!.filter { it?.id != selectedEventId }
                notesViewModel.setSuggestedEvents(updatedSuggestedEvents)
                selectedEventId = ""
                openDeleteDialogForEvent.value = false
            }
        },
        onDismissRequest = {
            openDeleteDialogForEvent.value = false
        },
        showConfirmationDialog = openDeleteDialogForEvent.value,
        titleTestTag = "txt_notes_screen_delete_event_title",
        messageTestTag = "txt_notes_screen_delete_event_message",
        confirmButtonTestTag = "btn_notes_screen_delete_event_confirm",
        dismissButtonTestTag = "btn_notes_screen_delete_event_cancel"
    )

    CustomAlertDialog(
        title = "Delete Task",
        message = "Are you sure you want to delete this task?",
        onConfirmButtonClick = {
            if (accountId != null) {
                val task = tasks!!.filter { it?.id == selectedTaskId }
                Log.i("task", "${task} ${selectedTaskId}")
                task[0]?.task_id?.let {
                    Log.i("task id", "${it}")
                    notesViewModel.deleteAccountTask(
                        accountId,
                        taskId = it
                    )
                }

                var updatedTask = tasks!!.filter { it?.id != selectedTaskId }

                notesViewModel.setTasks(updatedTask)
                selectedTaskId = ""
                openDeleteDialogForTask.value = false
            }
        },
        onDismissRequest = {
            openDeleteDialogForTask.value = false
        },
        showConfirmationDialog = openDeleteDialogForTask.value,
        titleTestTag = "txt_notes_screen_delete_task_title",
        messageTestTag = "txt_notes_screen_delete_task_message",
        confirmButtonTestTag = "btn_notes_screen_delete_task_confirm",
        dismissButtonTestTag = "btn_notes_screen_delete_task_cancel"
    )

    if (searchNameBottomSheetVisible) {
        SearchNameBottomSheet(
            toggleSearchNameBottomSheet,
            accountId = accountId!!,
            accountName = accountName!!,
            id = selectedTaskId,
            onUpdateUserName = { userId, userName ->
                selectedCrmUserId.value = userId
                selectedCrmUserName.value = userName

            }
        )
    }

    if (RecommTaksBottomSheetVisible) {
        Log.i("Updated tasks", "$tasks")
        val task = tasks?.find { it.id == selectedTaskId }
        Log.i("task==", "${task}")
        Log.i("Ids test", "${selectedTaskId}")

        RecommandedTaskSheet(
            toggleSheet,
            accountId = accountId,
            accountName = accountName!!,
            crmUserId = task?.crm_user_id ?: "",
            crmUserName = task?.crm_user_name ?: "",
            taskDesc = task?.task_desc ?: "",
            dueDate = task?.due_date ?: "",
            id = selectedTaskId,
            onSelectUSerClick = { id ->
                toggleSearchNameBottomSheet()
            },
            onCancelClick = { crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id: String ->
                Log.i(
                    "onCancelClick",
                    " crmOrganizationUserId ${crmOrganizationUserId} crmOrganizationUserName ${crmOrganizationUserName} description ${description} dueDate ${dueDate} id ${id}"
                )

                notesViewModel.updateTaskById(
                    id, Tasks(
                        crm_user_id = crmOrganizationUserId,
                        crm_user_name = crmOrganizationUserName,
                        task_desc = description,
                        due_date = dueDate,
                        id = id,
                        is_task_created = false
                    )
                )
            },
            onAddTaskClick = { crmOrganizationUserId: String, crmOrganizationUserName: String, description: String, dueDate: String, id: String, taskId: String ->
                Log.i(
                    "onAddTaskClick",
                    " crmOrganizationUserId ${crmOrganizationUserId} crmOrganizationUserName ${crmOrganizationUserName} description ${description} dueDate ${dueDate} id ${id} taskId ${taskId}"
                )
                notesViewModel.updateTaskById(
                    id, Tasks(
                        crm_user_id = crmOrganizationUserId,
                        crm_user_name = crmOrganizationUserName,
                        task_desc = description,
                        due_date = dueDate,
                        id = id,
                        is_task_created = true,
                        task_id = taskId
                    )
                )
            },
        )
    }

    if (RecommEventBottomSheetVisible) {
        val event = suggestedEvents?.find { it.id == selectedEventId }
        var startDateTime = extractDateAndTime(event!!.start_datetime)
        var endDateTime = extractDateAndTime(event!!.end_datetime)
        Log.i("event==", "${event}")
        Log.i("Ids test", "${selectedEventId}")

        RecommondedEventSheet(
            toggleEventSheet,
            accountId = accountId,
            startDate = startDateTime!!.first ?: "",
            endDate = endDateTime!!.first ?: "",
            startTime = startDateTime!!.second ?: "",
            selectedEventId = event!!.id,
            endTime = endDateTime!!.second ?: "",
            eventDescription = event?.description ?: "",
            onAddEventClick = { id: String, eventId: String, eventDescription: String, startDateTime: String, endDateTime: String ->
                notesViewModel.updateSuggestedEventById(
                    id, SuggestedEvents(
                        start_datetime = startDateTime,
                        end_datetime = endDateTime,
                        description = eventDescription,
                        id = id,
                        is_event_created = true,
                        event_id = eventId
                    )
                )
                toggleEventSheet()
            },
            onCancelEventClick = { id: String, eventDescription: String, startDateTime: String, endDateTime: String ->
                Log.i(
                    "onCancelClick",
                    " id ${id} eventDescription ${eventDescription} startDateTime ${startDateTime} endDateTime ${endDateTime}"
                )
                notesViewModel.updateSuggestedEventById(
                    id, SuggestedEvents(
                        start_datetime = startDateTime,
                        end_datetime = endDateTime,
                        description = eventDescription,
                        id = id,
                        is_event_created = false
                    )
                )
                toggleEventSheet()
            },
            eventId = event!!.event_id ?: "",
        )
    }

    Column(
        modifier = Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        Header(
            note = noteDesc,
            noteId = noteId,
            accountId = accountId!!,
            saveNoteApiInProgress = saveNoteApiInProgress,
            saveNoteApiIsSuccess = saveNoteApiIsSuccess,
            updateNoteApiInProgress = updateNoteApiInProgress,
            updateNoteApiIsSuccess = updateNoteApiIsSuccess,
            isNotesScreenEditable = isNotesScreenEditable,
        )
        NotesHeader(
            accountName = accountName, isAccountSelectionEnabled = isAccountSelectionEnabled
        )

        if (noteDetailsResponse is NetworkResponse.Loading) {
            Loader()
        } else {
            EditableTextField(note = noteDesc,
                onValueChange = {
                    noteDesc = it
                },
                placeholderText = "Add A Note",
                readOnly = !isNotesScreenEditable || saveNoteApiIsSuccess || updateNoteApiIsSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "et_create_note"
                        testTag = "et_create_note"
                        testTagsAsResourceId = true
                    })
        }



        Log.i("After posting values", "values")
        if (getCrmActionLoading) {
            RecommendedSectionHeader(
                heading = "Getting recommendations",
                shouldShowPlusIcon = false,
                accountId = accountId!!,
                accountName = accountName!!,
                testId = "txt_create_note_getting_recommendations",
                onAddEventClick = {},
                onAddTaskClick = {}
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .dashedBorder(1.dp, 5.dp, Color(0x80545A71))
                    .fillMaxWidth()
                    .height(122.dp)
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF212653), strokeWidth = 2.dp, modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Please wait, we're checking to recommend tasks or events for you.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.56.sp,
                    )
                )
            }
        } else if (tasks?.isEmpty() == true && suggestedEvents?.isEmpty() == true) {
            EmptyScreen(
                emptyText = "You are all set, no recommendation for now!",
                shouldShowIcon = true,
                height = 95.dp,
                testId = "note_screen_empty_screen"
            )
        } else {
            Log.i("NotesScreen re com", "NotesScreen: ${tasks?.size} $tasks")
            if (tasks?.isNotEmpty() == true || suggestedEvents?.isNotEmpty() == true) {
                RecommendedSectionHeader(
                    heading = "We have some recommendations ",
                    shouldShowPlusIcon = true,
                    accountId = accountId!!,
                    accountName = accountName!!,
                    testId = "txt_create_note_recommendations",
                    onAddTaskClick = {
                        selectedTaskId = ""
                        toggleSheet()
                    },
                    onAddEventClick = {
                        selectedEventId = ""
                        NavigationService.navigateToEventScreen(
                            accountId = accountId,
                            accountName,
                            null
                        )
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))
                var index = 0;
                tasks?.forEach { task ->
                    Column(
                        modifier = Modifier
                            .dashedBorder(1.dp, 5.dp, Color(0x80545A71))
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        Log.i("tasks rerendering", "${task.task_desc}")
                        TaskSuggestionCard(
                            id = task?.id!!,
                            accountId = accountId!!,
                            accountName = accountName!!,
                            crmUserId = task.crm_user_id!!,
                            crmUserName = task.crm_user_name!!,
                            taskDesc = task.task_desc!!,
                            dDate = task.due_date ?: "",
                            isTaskAdded = task.is_task_created,
                            shouldShowOptions = task.is_task_created,
                            onDeleteTaskClick = { id ->
                                selectedTaskId = id
                                openDeleteDialogForTask.value = true
                            },
                            onSelectUSerClick = { id ->
                                Log.i("onSelectUSerClick:", "${id}")
                                selectedTaskId = id
                                toggleSearchNameBottomSheet()
                            },
                            onEditTaskClick = { id ->
                                selectedTaskId = id
                                toggleSheet()
                            },
                            onCancelTaskClick = { taskId ->
                                Log.i("NotesScreen oncancel", "NotesScreen: $taskId")
                                Log.i("NotesScreen oncancel", "NotesScreen: ${tasks!!.size} $tasks")
                                val updatedTasks = tasks!!.filter { it?.id != taskId }
                                notesViewModel.setTasks(updatedTasks)
                                Log.i(
                                    "NotesScreen oncancel",
                                    "NotesScreen: ${updatedTasks!!.size} $updatedTasks"
                                )
                            },
                            createTaskApiInProgress = createTaskApiInProgress,
                            onAddTaskClick = { crmOrganizationUserId: String, description: String, dueDate: String, id: String ->
                                addTaskId = id
                                createTaskApiInProgress = true
                                notesViewModel.createTask(
                                    accountId = accountId!!,
                                    crmOrganizationUserId = crmOrganizationUserId,
                                    description = description,
                                    dueDate = dueDate,
                                )
                            },
                            noteViewModal = notesViewModel,
                            index = index++,
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }


                suggestedEvents?.forEach {
                    var startDateTime = extractDateAndTime(it!!.start_datetime)
                    var endDateTime = extractDateAndTime(it!!.end_datetime)
                    Log.i(
                        "event desc rerendering",
                        "${it.description} ${startDateTime} ${endDateTime}"
                    )
                    Column(
                        modifier = Modifier
                            .dashedBorder(1.dp, 5.dp, Color(0x80545A71))
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        if (it.is_event_created) {
                            val startDateTime = it.start_datetime;
                            val endDateTime = it.end_datetime;
                            val description = it.description;
                            val eventId = it.event_id;
                            val id = it.id
                            EventCard(
                                firsName = currentUser?.data?.current_user?.name?.split(" ")?.get(0)
                                    ?: " ",
                                lastName = currentUser?.data?.current_user?.name?.split(" ")?.get(1)
                                    ?: " ",
                                username = currentUser?.data?.current_user?.name ?: "",
                                notes = it.description,
                                date = it.start_datetime,
                                startDateTime = it.start_datetime,
                                endDateTime = it.end_datetime,
                                dueDate = it.start_datetime,
                                onClick = { },
                                index = index++,
                                isEventAdded = it.is_event_created,
                                onEditMenuClick = {
                                    var eventData = EventDetailsObject(
                                        eventId = eventId ?: "",
                                        eventStartDate = startDateTime!!,
                                        eventEndDate = endDateTime!!,
                                        eventDescription = description,
                                        isEventScreenEditable = true
                                    )
                                    selectedEventId = id
                                    toggleEventSheet()
//                                    NavigationService.navigateToEventScreen(
//                                        accountId,
//                                        accountName,
//                                        eventData
//                                    )
                                },
                                onDeleteMenuClick = {
                                    if (eventId != null) {
                                        Log.i("NotesScreen ondelete", "NotesScreen: $eventId")
                                        selectedEventId = id
                                        openDeleteDialogForEvent.value = true
                                    }

                                },
                            )
                        } else {
                            EventSuggestionCard(
                                index = index++,
                                id = it?.id!!,
                                accountId = accountId!!,
                                startDate = startDateTime!!.first ?: "",
                                endDate = endDateTime!!.first ?: "",
                                startTime = startDateTime!!.second ?: "",
                                endTime = endDateTime!!.second ?: "",
                                eventDescription = it.description,
                                isEventAdded = it.is_event_created,
                                onDeleteEventClick = {},
                                onEditEventClick = { id ->
                                    selectedEventId = id
                                    toggleEventSheet()
                                },
                                onCancelEventClick = { eventId ->
                                    val updatedEvents =
                                        suggestedEvents!!.filter { it?.id != eventId }
                                    notesViewModel.setSuggestedEvents(updatedEvents)
                                },
                                createEventApiInProgress = createEventApiInProgress,
                                noteViewModal = notesViewModel,
                                onAddEventClick = { accountId,
                                                    eventDescription,
                                                    selectedStartDateText,
                                                    selectedEndDateText,
                                                    selectedStartTimeText,
                                                    selectedEndTimeText,
                                                    id ->
                                    addEventId = id
                                    if (selectedStartDateText.isNotEmpty() && selectedEndDateText.isNotEmpty() && selectedStartTimeText.isNotEmpty() && selectedEndTimeText.isNotEmpty()) {
                                        createEventApiInProgress = true
                                        val iso8601StartDateTime =
                                            convertToISO8601(
                                                selectedStartDateText,
                                                selectedStartTimeText
                                            );
                                        val iso8601EndDateTime =
                                            convertToISO8601(
                                                selectedEndDateText,
                                                selectedEndTimeText
                                            );
                                        notesViewModel.createEvent(
                                            accountId = accountId!!,
                                            description = eventDescription,
                                            startDateTime = iso8601StartDateTime,
                                            endDateTime = iso8601EndDateTime,
                                        )
                                    }
                                })
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecommendedSectionHeader(
    heading: String,
    accountName: String,
    accountId: String,
    shouldShowPlusIcon: Boolean,
    testId: String,
    onAddTaskClick: () -> Unit = {},
    onAddEventClick: () -> Unit = {},
) {
    var recommendedPopup by remember { mutableStateOf(false) }


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(imageId = R.drawable.sparkle,
            imageContentDescription = "buildings",
            text = heading,
            imageModifier = Modifier
                .width(24.dp)
                .height(24.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF212653),
            ),
            textModifier = Modifier.semantics {
                testTagsAsResourceId = true
                testTag = testId
                contentDescription = testId
            })
        if (shouldShowPlusIcon) {
            Box {
                Image(painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "add_notes",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                        .semantics {
                            contentDescription = "add_notes"
                            testTag = "add_notes"
                            testTagsAsResourceId = true
                        }
                        .clickable(
                            interactionSource = MutableInteractionSource(), indication = null
                        ) {
                            recommendedPopup = true
                        })
                Spacer(modifier = Modifier.height(5.dp))
                UpdateTaskDropDownMenu(
                    expanded = recommendedPopup,
                    onDismissRequest = { recommendedPopup = false },
                    onAddTaskMenuClick = {
                        onAddTaskClick()
                        NavigationService.navigateToTaskScreen(accountId, accountName, null)
                    },
                    onAddEventMenuClick = {
                        onAddEventClick()
                        NavigationService.navigateToEventScreen(accountId, accountName, null)
                    }
                )
            }
        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesHeader(accountName: String?, isAccountSelectionEnabled: Boolean) {
    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    if (bottomSheetVisible) {
        AccountListBottomSheet(toggleBottomSheet, false, isAccountSelectionEnabled)
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = R.drawable.buildings),
                contentDescription = "Buildings",
                modifier = Modifier.size(size = 14.dp)
            )
            Text(
                text = "Account", color = Color(0xff212653), style = TextStyle(
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
                .padding(all = 8.dp)
                .background(color = Color(0xFFF6F6F8), shape = RoundedCornerShape(size = 4.dp))

        ) {
            Button(onClick = {
                if (isAccountSelectionEnabled) {
                    toggleBottomSheet()
                }
            },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier.semantics {
                    contentDescription = "btn_select_account"
                    testTag = "btn_select_account"
                    testTagsAsResourceId = true
                }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = accountName ?: "Select Account",
                        color = Color(0xffdd1a77),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                        modifier = Modifier.semantics {
                            contentDescription =
                                if (accountName.isNullOrBlank()) "txt_create_note_select_account" else "txt_create_note_selected_account"
                            testTag =
                                if (accountName.isNullOrBlank()) "txt_create_note_select_account" else "txt_create_note_selected_account"
                            testTagsAsResourceId = true
                        })
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Vector 56",
                    tint = Color(0xff545a71)
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Header(
    note: String,
    noteId: String?,
    accountId: String,
    saveNoteApiInProgress: Boolean,
    saveNoteApiIsSuccess: Boolean,
    updateNoteApiInProgress: Boolean,
    updateNoteApiIsSuccess: Boolean,
    isNotesScreenEditable: Boolean,
) {
    val notesViewModel: NotesViewModel = hiltViewModel()


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = if (saveNoteApiIsSuccess) {
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
                        if (saveNoteApiIsSuccess) "btn_done_create_note" else "btn_cancel_create_note"
                    contentDescription =
                        if (saveNoteApiIsSuccess) "btn_done_create_note" else "btn_cancel_create_note"
                },
        )

        val buttonColor = if (note.isNotEmpty() && accountId.isNotEmpty()) {
            Color(0xFF212653)
        } else {
            Color(0xFF212653).copy(alpha = 0.7f)
        }
        if (isNotesScreenEditable) {
            Button(onClick = {
                if (noteId.isNullOrBlank()) {
                    notesViewModel.saveNote(
                        accountId = accountId!!,
                        text = note,
                    )
                } else {
                    notesViewModel.updateNote(
                        accountId = accountId!!,
                        text = note,
                        noteId = noteId
                    )
                }
            },

                enabled = note.isNotEmpty() && accountId.isNotEmpty() && !(saveNoteApiInProgress || saveNoteApiIsSuccess || updateNoteApiInProgress || updateNoteApiIsSuccess),
                contentPadding = PaddingValues(all = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, contentColor = Color.White
                ),
                modifier = Modifier
                    .background(
                        color = buttonColor, shape = RoundedCornerShape(size = 5.dp)
                    )
                    .width(92.dp)
                    .height(46.dp)
                    .clip(shape = RoundedCornerShape(size = 5.dp))
                    .semantics {
                        testTagsAsResourceId = true
                        testTag = "btn_save_note"
                        contentDescription = "btn_save_note"
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

                    Image(painter = if (saveNoteApiInProgress || updateNoteApiInProgress) {
                        rememberAsyncImagePainter(R.drawable.loader, imageLoader)
                    } else if (saveNoteApiIsSuccess || updateNoteApiIsSuccess) {
                        painterResource(id = R.drawable.check)
                    } else {
                        painterResource(id = R.drawable.cloud)
                    },
                        contentDescription = "cloud",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .width(width = 17.dp)
                            .height(height = 12.dp)
                            .semantics {
                                testTagsAsResourceId = true
                                testTag = "cloud"
                            })
                    Text(text = if (saveNoteApiInProgress || updateNoteApiInProgress) {
                        "Saving..."
                    } else if (saveNoteApiIsSuccess || updateNoteApiIsSuccess) {
                        "Saved"
                    } else {
                        "Save"
                    }, color = Color.White, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.48.sp,
                    ), modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag =
                            if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                        contentDescription =
                            if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                    })
                }
            }
        }
    }
}
