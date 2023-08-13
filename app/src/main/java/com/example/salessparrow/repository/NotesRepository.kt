package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.salessparrow.api.ApiService
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
}