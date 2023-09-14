package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.GetCrmActionsResponse
import com.truesparrow.sales.models.Note
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.models.Tasks
import com.truesparrow.sales.repository.NotesRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    val note = mutableStateOf("")

    val notesLiveData: LiveData<NetworkResponse<SaveNote>>
        get() = notesRepository.notesLiveData

    private val _tasks = MutableLiveData<List<Tasks>>()
    val tasks: MutableLiveData<List<Tasks>> = _tasks

    fun setTasks(newTasks: List<Tasks>) {
        _tasks.postValue(newTasks)
        Log.i(" _tasks.value","${ _tasks.value}")
    }
    fun updateTaskById(taskId: String, updatedTask: Tasks) {

        Log.i("UpdateById=","${updatedTask} ${taskId}")

        val currentTasks = _tasks.value.orEmpty()
        Log.i("currentTasks=","${currentTasks}")
        val updatedTasks = currentTasks.map { task ->
            if (task.id == taskId) {
                Log.i("Checking","Log")
                updatedTask
            } else {
                Log.i("Existing task","${task.id}")
                task

            }
        }
        Log.i("Checking","${updatedTasks}")
//        setTasks(updatedTasks)
        _tasks.postValue(updatedTasks)

        Log.i("UpdateById","${_tasks.value}")
    }
    fun getTaskById(taskId: String): Tasks? {
        Log.i("taskId get task","${taskId}")
        return _tasks.value?.find { it.id == taskId }
    }

    val noteDetailsLiveData: LiveData<NetworkResponse<NotesDetailResponse>>
        get() = notesRepository.noteDetails

    val getCrmActionsLiveData: LiveData<NetworkResponse<GetCrmActionsResponse>>
        get() = notesRepository.getCrmActions


    fun getNoteDetails(accountId: String, noteId: String) {
        Log.i("NotesDetails", "Account Id: $accountId Note Id: $noteId");
        viewModelScope.launch {
            notesRepository.getNoteDetails(accountId, noteId)
        }
    }

    fun saveNote(accountId: String, text: String) {
        Log.i("res", "saveNote: $accountId, $text")
        viewModelScope.launch {
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
}