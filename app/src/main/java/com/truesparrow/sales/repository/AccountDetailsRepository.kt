package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.AccountNotesResponse
import com.truesparrow.sales.models.AccountTasksResponse
import com.truesparrow.sales.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class AccountDetailsRepository @Inject constructor(private val apiService: ApiService) {

    private val _accountNotes = MutableLiveData<NetworkResponse<AccountNotesResponse>>()
    val accountNotes: LiveData<NetworkResponse<AccountNotesResponse>>
        get() = _accountNotes

    private val _accountTasks = MutableLiveData<NetworkResponse<AccountTasksResponse>>()
    val accountTasks: LiveData<NetworkResponse<AccountTasksResponse>>
        get() = _accountTasks


    private val _deleteAccountNote = MutableLiveData<NetworkResponse<Unit>>()
    val deletAccountNote: LiveData<NetworkResponse<Unit>>
        get() = _deleteAccountNote

    suspend fun getAccountNotes(accountId: String) {

        try {
            _accountNotes.postValue(NetworkResponse.Loading())
            val response = apiService.getAccountNotes(accountId)

            if (response.isSuccessful && response.body() != null) {
                _accountNotes.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _accountNotes.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                Log.i("AccountDetails", "Exception: $response")
                _accountNotes.postValue(NetworkResponse.Error("Error went wrong"))
            }
        } catch (e: Exception) {
            Log.i("AccountDetails", "Exception: ${e.message}")
            _accountNotes.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }

    suspend fun getAccountTasks(accountId: String) {
        try {
            _accountTasks.postValue(NetworkResponse.Loading())
            val response = apiService.getAccountTasks(accountId)

            if (response.isSuccessful && response.body() != null) {
                _accountTasks.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _accountTasks.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                Log.i("AccountDetails", "Exception: $response")
                _accountTasks.postValue(NetworkResponse.Error("Error went wrong"))
            }
        } catch (e: Exception) {
            Log.i("AccountDetails", "Exception: ${e.message}")
            _accountTasks.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }

    suspend fun deleteAccountNote(accountId: String, noteId: String) {
        try {
            _deleteAccountNote.postValue(NetworkResponse.Loading())
            val response = apiService.deleteNote(accountId, noteId)

            if (response.isSuccessful && response.body() != null) {
                _deleteAccountNote.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _deleteAccountNote.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                Log.i("AccountDetails", "Exception: $response")
                _deleteAccountNote.postValue(NetworkResponse.Error("Error went wrong"))
            }
        } catch (e: Exception) {
            Log.i("AccountDetails", "Exception: ${e.message}")
            _deleteAccountNote.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }

}

