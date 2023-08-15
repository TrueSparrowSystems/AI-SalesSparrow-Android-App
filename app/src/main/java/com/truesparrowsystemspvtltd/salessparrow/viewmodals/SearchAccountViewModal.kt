package com.truesparrowsystemspvtltd.salessparrow.viewmodals

import com.truesparrowsystemspvtltd.salessparrow.repository.SearchAccountRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrowsystemspvtltd.salessparrow.models.AccountListResponse
import com.truesparrowsystemspvtltd.salessparrow.services.NavigationService
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAccountViewModel @Inject constructor(
    private val repository: SearchAccountRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.searchAccounts("");
        }
    }

    val searchAccountLiveDataData: LiveData<NetworkResponse<AccountListResponse>>
        get() = repository.searchAccountLiveData


    fun onSearchQueryChanged(query: String) {
        Log.d("SearchAccountViewModel", "onSearchQueryChanged: $query")
        viewModelScope.launch {
            repository.searchAccounts(query);
        }
    }

    fun onAccountRowClicked(
        accountName: String,
        accountId: String,
        isAccountSelectionEnabled: Boolean
    ) {
        Log.d("SearchAccountViewModel", "onAccountRowClicked: $accountName")
        NavigationService.navigateTo("account_details_screen/${accountId}/${accountName}")
    }


    fun onAddNoteClicked(
        accountName: String,
        accountId: String,
        isAccountSelectionEnabled: Boolean
    ) {
        Log.d("SearchAccountViewModel", "onAddNoteClicked: $accountName")
        NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/${isAccountSelectionEnabled}")
    }
}
