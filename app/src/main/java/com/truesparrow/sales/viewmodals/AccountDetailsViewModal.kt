package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.models.AccountEventsResponse
import com.truesparrow.sales.models.AccountNotesResponse
import com.truesparrow.sales.models.AccountTasksResponse
import com.truesparrow.sales.models.Event
import com.truesparrow.sales.models.Note
import com.truesparrow.sales.models.Task
import com.truesparrow.sales.repository.AccountDetailsRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountDetailsViewModal @Inject constructor(
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events


    fun setNotes(newNotes: List<Note>) {
        _notes.value = newNotes
    }


    fun setTasks(newTasks: List<Task>) {
        _tasks.value = newTasks
    }

    fun setEvents(newEvents: List<Event>) {
        _events.value = newEvents
    }

    val accountEventsLiveData: LiveData<NetworkResponse<AccountEventsResponse>>
        get() = accountDetailsRepository.accountEvents

    val accountDetailsLiveData: LiveData<NetworkResponse<AccountNotesResponse>>
        get() = accountDetailsRepository.accountNotes

    val accountTasksLiveData: LiveData<NetworkResponse<AccountTasksResponse>>
        get() = accountDetailsRepository.accountTasks

    val deleteAccountNoteLiveData: LiveData<NetworkResponse<Unit>>
        get() = accountDetailsRepository.deletAccountNote

    val deleteAccountTaskLiveData: LiveData<NetworkResponse<Unit>>
        get() = accountDetailsRepository.deletAccountTask

    val deleteAccountEventLiveData: LiveData<NetworkResponse<Unit>>
        get() = accountDetailsRepository.deletAccountEvent

    fun deleteAccountTask(accountId: String, taskId: String) {
        viewModelScope.launch {
            accountDetailsRepository.deleteAccountTask(
                accountId,
                taskId
            )
        }
    }


    fun getAccountNotes(accountId: String) {
        Log.i("AccountDetails", "Account Id: $accountId")
        viewModelScope.launch {
            accountDetailsRepository.getAccountNotes(accountId)
        }
    }

    fun getAccountTasks(accountId: String) {
        viewModelScope.launch {
            accountDetailsRepository.getAccountTasks(accountId)
        }
    }

    fun getAccountEvents(accountId: String) {
        viewModelScope.launch {
            accountDetailsRepository.getAccountEvents(accountId)
        }
    }

    fun deleteAccountNote(
        accountId: String,
        noteId: String
    ) {
        viewModelScope.launch {
            accountDetailsRepository.deleteAccountNote(
                accountId,
                noteId
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