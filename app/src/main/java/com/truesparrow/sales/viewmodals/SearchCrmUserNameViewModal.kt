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
        accountId: String,
        accountName: String,
        isNewTask: Boolean = false,
    ) {
        if (isNewTask) {
            NavigationService.navigateToTaskScreen(accountId, accountName, null)
        } else {
            NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/true")
        }

    }
}
