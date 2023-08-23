package com.truesparrow.sales.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.models.CrmUserNameListResponse
import com.truesparrow.sales.util.NetworkResponse
import javax.inject.Inject

class SearchCrmUserNameRepository @Inject constructor(private val apiService: ApiService) {


//    Todo:Ds replace AccountListResponse with CrmUserNameListResponse once APi is ready from BE

    private val _searchCrmUserLiveData = MutableLiveData<NetworkResponse<AccountListResponse>>()
    val searchCrmUserLiveData: LiveData<NetworkResponse<AccountListResponse>>
        get() = _searchCrmUserLiveData


    suspend fun searchCrmUsers(query: String) {
        try {
            _searchCrmUserLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.getCrmUser(query = query);

            if (result.isSuccessful && result.body() != null) {
                _searchCrmUserLiveData.postValue(NetworkResponse.Success(result.body()!!))
            } else if (result.errorBody() != null) {
                _searchCrmUserLiveData.postValue(NetworkResponse.Error("Error fetching records: ${result.errorBody()}"))
            } else {
                _searchCrmUserLiveData.postValue(NetworkResponse.Error("Error fetching records: Error}"))
            }
        } catch (e: Exception) {
            _searchCrmUserLiveData.postValue(NetworkResponse.Error("Error fetching records: ${e.message.toString()}"))
        }

    }
}
