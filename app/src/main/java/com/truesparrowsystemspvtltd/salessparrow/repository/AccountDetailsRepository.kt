package com.truesparrowsystemspvtltd.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrowsystemspvtltd.salessparrow.api.ApiService
import com.truesparrowsystemspvtltd.salessparrow.models.AccountNotesResponse
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class AccountDetailsRepository @Inject constructor(private val apiService: ApiService) {

    private val _accountNotes = MutableLiveData<NetworkResponse<AccountNotesResponse>>()
    val accountNotes: LiveData<NetworkResponse<AccountNotesResponse>>
        get() = _accountNotes

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
}

