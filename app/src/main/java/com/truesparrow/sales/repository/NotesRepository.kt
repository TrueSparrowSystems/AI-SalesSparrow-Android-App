package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.GetCrmActionRequest
import com.truesparrow.sales.models.GetCrmActionsResponse
import com.truesparrow.sales.models.NotesDetailResponse
import com.truesparrow.sales.models.SaveNote
import com.truesparrow.sales.models.SaveNoteRequest
import com.truesparrow.sales.util.NetworkResponse
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

    private val _getCrmActions = MutableLiveData<NetworkResponse<GetCrmActionsResponse>>()
    val getCrmActions: LiveData<NetworkResponse<GetCrmActionsResponse>>
        get() = _getCrmActions

    private val _updateNoteLiveData = MutableLiveData<NetworkResponse<Unit>>()
    val updateNoteLiveData: LiveData<NetworkResponse<Unit>>
        get() = _updateNoteLiveData

    suspend fun updateNote(
        accountId: String,
        noteId: String,
        text: String,
    ) {
        try {
            _updateNoteLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.updateNote(
                accountId, noteId, SaveNoteRequest(text = text)
            );
            if (result.isSuccessful && result.body() != null) {
                _updateNoteLiveData.postValue(NetworkResponse.Success(result.body()!!))
                Log.i("res", "result - update Note: ${result.body()}")
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _updateNoteLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _updateNoteLiveData.postValue(NetworkResponse.Error("Error Updating Note:}"))
            }
        } catch (e: Exception) {
            _updateNoteLiveData.postValue(NetworkResponse.Error("Error Updating Note:"))
        }
    }

    suspend fun saveNote(
        accountId: String,
        text: String,
    ) {
        try {
            _notesLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.saveNote(accountId, SaveNoteRequest(text = text));

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

    suspend fun getCrmActions(text: String) {
        try {
            _getCrmActions.postValue(NetworkResponse.Loading())
            val response = apiService.getCrmActions(GetCrmActionRequest(text = text))
            if (response.isSuccessful && response.body() != null) {
                Log.i("response", "${response.body()}")
                _getCrmActions.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _getCrmActions.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _getCrmActions.postValue(NetworkResponse.Error("Error went wrong"))
            }

        } catch (e: Exception) {
            _getCrmActions.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }
}