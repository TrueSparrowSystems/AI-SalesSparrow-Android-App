package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.AccountFeedResponse
import com.truesparrow.sales.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {

    private val _accountFeedLiveData = MutableLiveData<NetworkResponse<AccountFeedResponse>>()
    val accountFeedLiveData: LiveData<NetworkResponse<AccountFeedResponse>>
        get() = _accountFeedLiveData


    suspend fun getAccountFeed(paginationIdentifier: String ? = "") {
        try {
            _accountFeedLiveData.postValue(NetworkResponse.Loading())
            val response = if (paginationIdentifier?.isNotBlank() == true) {
                apiService.getAccountFeedWithPagination(paginationIdentifier!!)
            } else {
                apiService.getAccountFeed()
            }
            if (response.isSuccessful && response.body() != null) {
                _accountFeedLiveData.postValue(NetworkResponse.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _accountFeedLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _accountFeedLiveData.postValue(NetworkResponse.Error("Something went wrong"))
            }
        } catch (e: Exception) {
            _accountFeedLiveData.postValue(NetworkResponse.Error("Something went wrong"))
        }
    }
}