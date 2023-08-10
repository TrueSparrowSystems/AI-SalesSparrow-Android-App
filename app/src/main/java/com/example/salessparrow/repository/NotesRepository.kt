package com.example.salessparrow.repository

import android.util.Log
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.models.SaveNote
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun saveNote(
        text: String,
    ): SaveNote? {
        return try {
            val response = apiService.saveNote(text);
            Log.i("response", "note: ${response}")
            val note = response.body()

            Log.i("res", "note: $note")

            if (response.isSuccessful) {
                note!!
            } else {
                null
            }
        } catch (e: Exception) {
            Log.i("Authentication exception", "getCurrentUser: $e")
            null
        }
    }
}