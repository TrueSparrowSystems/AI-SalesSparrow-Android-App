package com.truesparrowsystemspvtltd.salessparrow.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrowsystemspvtltd.salessparrow.models.NotesDetailResponse
import com.truesparrowsystemspvtltd.salessparrow.models.SaveNote
import com.truesparrowsystemspvtltd.salessparrow.repository.NotesRepository
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
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
}