package com.example.salessparrow.viewmodals

import com.example.salessparrow.repository.SearchAccountRepository
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.models.AccountListResponse
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.NetworkResponse
import com.example.salessparrow.util.Screens
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
        NavigationService.navigateTo("account_details_screen/${accountId}");
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
