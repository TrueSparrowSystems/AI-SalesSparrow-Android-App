package com.truesparrow.sales.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.AccountListResponse
import com.truesparrow.sales.models.CrmOrganisationUsersResponse
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

    init {
        viewModelScope.launch {
            repository.searchCrmUsers("");
        }
    }

    val searchAccountLiveDataData: LiveData<NetworkResponse<CrmOrganisationUsersResponse>>
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
        isNewTask: Boolean = false,
        id : String
    ) {
        if (isNewTask) {
            NavigationService.navigateTo("task_screen/${id}/${crmUserId}/${crmUserName}/Select")
        } else {
            NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/true/${crmUserId}/${crmUserName}")
        }

    }
}
