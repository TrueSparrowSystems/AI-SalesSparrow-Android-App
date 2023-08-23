package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.repository.NotesRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    val notesLiveData: LiveData<NetworkResponse<SaveNote>>
        get() = notesRepository.notesLiveData


    val noteDetailsLiveData: LiveData<NetworkResponse<NotesDetailResponse>>
        get() = notesRepository.noteDetails

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