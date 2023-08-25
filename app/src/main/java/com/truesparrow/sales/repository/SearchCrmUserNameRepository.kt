package com.truesparrow.sales.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.models.CrmOrganisationUsersResponse
import com.truesparrow.sales.util.NetworkResponse
import javax.inject.Inject

class SearchCrmUserNameRepository @Inject constructor(private val apiService: ApiService) {

    private val _searchCrmUserLiveData = MutableLiveData<NetworkResponse<CrmOrganisationUsersResponse>>()
    val searchCrmUserLiveData: LiveData<NetworkResponse<CrmOrganisationUsersResponse>>
        get() = _searchCrmUserLiveData


    suspend fun searchCrmUsers(query: String) {
        try {
            _searchCrmUserLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.getCrmOrganisationUser(query = query);

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
