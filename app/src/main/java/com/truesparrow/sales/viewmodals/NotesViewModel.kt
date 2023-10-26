package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.CreateAccountEventResponse
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.models.GetCrmActionsResponse
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.models.SuggestedEvents
import com.truesparrow.sales.models.Tasks
import com.truesparrow.sales.repository.AccountDetailsRepository
import com.truesparrow.sales.repository.EventRepository
import com.truesparrow.sales.repository.NotesRepository
import com.truesparrow.sales.repository.TasksRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val tasksRepository: TasksRepository,
    private val eventRepository: EventRepository,
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    val note = mutableStateOf("")

    val notesLiveData: LiveData<NetworkResponse<SaveNote>>
        get() = notesRepository.notesLiveData

    private val _tasks = MutableLiveData<List<Tasks>>()
    val tasks: MutableLiveData<List<Tasks>> = _tasks

    private val _suggestedEvents = MutableLiveData<List<SuggestedEvents>>()
    val suggestedEvents: MutableLiveData<List<SuggestedEvents>> = _suggestedEvents

    val updateNoteLiveData: LiveData<NetworkResponse<Unit>>
        get() = notesRepository.updateNoteLiveData


    fun setTasks(newTasks: List<Tasks>) {
        _tasks.postValue(newTasks)
        Log.i(" _tasks.value", "${_tasks.value}")
    }

    fun setSuggestedEvents(newSuggestedEvents: List<SuggestedEvents>) {
        _suggestedEvents.postValue(newSuggestedEvents)
        Log.i(" _suggestedEvents.value", "${_suggestedEvents.value}")
    }

    fun updateSuggestedEventById(eventId: String, updatedEvent: SuggestedEvents) {

        Log.i("UpdateById=", "${updatedEvent} ${eventId}")

        val currentEvents = _suggestedEvents.value.orEmpty()
        Log.i("currentEvents=", "${currentEvents}")
        val updatedEvents = currentEvents.map { event ->
            if (event.id == eventId) {
                Log.i("Checking", "Log")
                updatedEvent
            } else {
                Log.i("Existing event", "${event.id}")
                event

            }
        }
        Log.i("Checking", "${updatedEvents}")
        _suggestedEvents.postValue(updatedEvents)


        Log.i("UpdateById", "${_suggestedEvents.value}")
    }

    fun getSuggestedEventById(eventId: String): SuggestedEvents? {
        Log.i("eventId get event", "${eventId}")
        return _suggestedEvents.value?.find { it.id == eventId }
    }

    fun updateTaskById(taskId: String, updatedTask: Tasks) {

        Log.i("UpdateById=", "${updatedTask} ${taskId}")

        val currentTasks = _tasks.value.orEmpty()
        Log.i("currentTasks=", "${currentTasks}")
        val updatedTasks = currentTasks.map { task ->
            if (task.id == taskId) {
                Log.i("Checking", "Log")
                updatedTask
            } else {
                Log.i("Existing task", "${task.id}")
                task

            }
        }
        Log.i("Checking", "${updatedTasks}")
//        setTasks(updatedTasks)
        _tasks.postValue(updatedTasks)

        Log.i("UpdateById", "${_tasks.value}")
    }

    fun getTaskById(taskId: String): Tasks? {
        Log.i("taskId get task", "${taskId}")
        return _tasks.value?.find { it.id == taskId }
    }


    val noteDetailsLiveData: LiveData<NetworkResponse<NotesDetailResponse>>
        get() = notesRepository.noteDetails

    val getCrmActionsLiveData: LiveData<NetworkResponse<GetCrmActionsResponse>>
        get() = notesRepository.getCrmActions

    val eventsLiveData: LiveData<NetworkResponse<CreateAccountEventResponse>>
        get() = eventRepository.eventsLiveData

    val tasksLiveData: LiveData<NetworkResponse<CreateAccountTaskResponse>>
        get() = tasksRepository.tasksLiveData

    val deleteAccountTaskLiveData: LiveData<NetworkResponse<Unit>>
        get() = accountDetailsRepository.deletAccountTask

    val deleteAccountEventLiveData: LiveData<NetworkResponse<Unit>>
        get() = accountDetailsRepository.deletAccountEvent




    fun getNoteDetails(accountId: String, noteId: String) {
        Log.i("NotesDetails", "Account Id: $accountId Note Id: $noteId");
        viewModelScope.launch {
            notesRepository.getNoteDetails(accountId, noteId)
        }
    }

    fun updateNote(
        accountId: String,
        noteId: String,
        text: String,
    ) {
        viewModelScope.launch {
            notesRepository.updateNote(
                accountId = accountId,
                noteId = noteId,
                text = text
            )
        }
    }

    fun saveNote(accountId: String, text: String) {
        Log.i("res", "saveNote: $accountId, $text")
        viewModelScope.launch {
            val response = notesRepository.saveNote(accountId = accountId, text = text)
            notesRepository.saveNote(
                accountId = accountId,
                text = text
            )
        }
    }

    fun getCrmActions(text: String) {
        viewModelScope.launch {
            notesRepository.getCrmActions(text)
        }
    }

    fun createTask(
        accountId: String, crmOrganizationUserId: String, description: String, dueDate: String
    ) {
        Log.i(
            "Task",
            "Account ID: $accountId, User ID: $crmOrganizationUserId, Description: $description, Due Date: $dueDate"
        )

        viewModelScope.launch {
            tasksRepository.createTask(
                accountId = accountId,
                crmOrganizationUserId = crmOrganizationUserId,
                description = description,
                dueDate = dueDate
            )
        }
    }

    fun createEvent(
        accountId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        viewModelScope.launch {
            Log.i("EventScreen", "$accountId $startDateTime $endDateTime $description")
            eventRepository.createEvent(accountId, startDateTime, endDateTime, description)
        }

    }

    fun deleteAccountTask(accountId: String, taskId: String) {
        viewModelScope.launch {
            accountDetailsRepository.deleteAccountTask(
                accountId,
                taskId
            )
        }
    }

    fun deleteAccountEvent(
        accountId: String,
        eventId: String
    ) {
        viewModelScope.launch {
            accountDetailsRepository.deleteAccountEvent(
                accountId,
                eventId
            )
        }
    }
}