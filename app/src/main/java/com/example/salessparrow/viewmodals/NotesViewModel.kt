package com.example.salessparrow.viewmodals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.data.DataWrapper
import com.example.salessparrow.models.SaveNote
import com.example.salessparrow.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    suspend fun saveNote(text: String): Boolean {
        notesRepository.saveNote(text) ?: return false
        return true

    }
}