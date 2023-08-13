package com.example.salessparrow.viewmodals

import androidx.lifecycle.ViewModel
import com.example.salessparrow.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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