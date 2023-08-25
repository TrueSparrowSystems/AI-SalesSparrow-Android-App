package com.truesparrow.sales.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.repository.SearchCrmUserNameRepository
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCrmUserNameViewModal @Inject constructor(
    private val repository: SearchCrmUserNameRepository
) : ViewModel() {

//    Todo:Ds replace AccountListResponse with CrmUserNameListResponse once APi is ready from BE

    init {
        viewModelScope.launch {
            repository.searchCrmUsers("");
        }
    }

    val searchAccountLiveDataData: LiveData<NetworkResponse<AccountListResponse>>
        get() = repository.searchCrmUserLiveData


    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch {
            repository.searchCrmUsers(query);
        }
    }

    fun onAccountRowClicked(
        crmUserId: String,
        crmUserName: String,
        accountId: String,
        accountName: String,
        isNewTask: Boolean = false
    ) {
        if (isNewTask) {
            NavigationService.navigateTo("task_screen/${crmUserId}/${crmUserName}")
        } else {
            NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/{true}/${crmUserId}/${crmUserName}")
        }

    }
}
