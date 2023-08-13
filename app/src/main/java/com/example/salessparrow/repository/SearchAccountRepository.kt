package com.example.salessparrow.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.salessparrow.api.ApiService
import com.example.salessparrow.models.AccountListResponse
import com.example.salessparrow.util.NetworkResponse
import javax.inject.Inject

class SearchAccountRepository @Inject constructor(private val apiService: ApiService) {


    private val _searchAccountLiveData = MutableLiveData<NetworkResponse<AccountListResponse>>()
    val searchAccountLiveData: LiveData<NetworkResponse<AccountListResponse>>
        get() = _searchAccountLiveData


    suspend fun searchAccounts(query: String) {
       try {
           val result = apiService.getAccounts(query = query);

           if (result.isSuccessful && result.body() != null) {
               _searchAccountLiveData.postValue(NetworkResponse.Success(result.body()!!))
           } else if (result.errorBody() != null) {
               _searchAccountLiveData.postValue(NetworkResponse.Error("Error fetching records: ${result.errorBody()}"))
           }
           else {
               _searchAccountLiveData.postValue(NetworkResponse.Error("Error fetching records: Error}"))
           }
       } catch (e: Exception) {
           _searchAccountLiveData.postValue(NetworkResponse.Error("Error fetching records: ${e.message.toString()}"))
       }

    }

    suspend fun getAccountRecords(
        callback: (AccountListResponse?) -> Unit, errorCallback: (String) -> Unit
    ) {
        try {
            val response = apiService.getAccounts(query = "test")
            Log.i("MyApp", "Response: $response")
            if (response.isSuccessful && response.body() != null) {
                callback(response.body())
            } else {
                errorCallback("Error fetching records: Error}")
            }
        } catch (e: Exception) {
            errorCallback("Error fetching records: ${e.message}")
        }
    }
}
