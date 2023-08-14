package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.models.NotesDetailResponse
import com.example.salessparrow.models.SaveNote
import com.example.salessparrow.models.SaveNoteRequest
import com.example.salessparrow.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val apiService: ApiService
) {
    private val _notesLiveData = MutableLiveData<NetworkResponse<SaveNote>>()
    val notesLiveData: LiveData<NetworkResponse<SaveNote>>
        get() = _notesLiveData

    private val _noteDetails = MutableLiveData<NetworkResponse<NotesDetailResponse>>()

    val noteDetails: LiveData<NetworkResponse<NotesDetailResponse>>
        get() = _noteDetails

    suspend fun saveNote(
        accountId: String,
        text: String,
    ) {
        try {
            _notesLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.saveNote(accountId,  SaveNoteRequest( text = text));

            if (result.isSuccessful && result.body() != null) {
                _notesLiveData.postValue(NetworkResponse.Success(result.body()!!))
                Log.i("res", "result: ${result.body()}")
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _notesLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _notesLiveData.postValue(NetworkResponse.Error("Error Saving Notes:}"))
            }
        } catch (e: Exception) {
            _notesLiveData.postValue(NetworkResponse.Error("Error Saving Notes:"))
        }
    }

    suspend fun getNoteDetails(accountId: String, noteId: String) {
        try {
            _noteDetails.postValue(NetworkResponse.Loading())
            val response = apiService.getNoteDetails(accountId, noteId)

            if (response.isSuccessful && response.body() != null) {
                _noteDetails.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _noteDetails.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                Log.i("AccountDetails", "Exception: $response")
                _noteDetails.postValue(NetworkResponse.Error("Error went wrong"))
            }
        } catch (e: Exception) {
            Log.i("AccountDetails", "Exception: ${e.message}")
            _noteDetails.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }
}