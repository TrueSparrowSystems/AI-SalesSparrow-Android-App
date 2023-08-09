package com.example.salessparrow.viewmodals

import com.example.salessparrow.repository.SearchAccountRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.salessparrow.data.DataWrapper
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAccountViewModel @Inject constructor(
    private val repository: SearchAccountRepository
) : ViewModel() {
    fun getAccountList(callback: (DataWrapper?) -> Unit, errorCallback: (String) -> Unit) {
        viewModelScope.launch {
            repository.getAccountRecords(callback, errorCallback)
        }
    }

    fun onAccountRowClicked(
        accountName: String,
        accountId: Int,
        isAccountSelectionEnabled: Boolean
    ) {
        Log.d("SearchAccountViewModel", "onAccountRowClicked: $accountName")
        NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/${isAccountSelectionEnabled}")
    }


    fun onAddNoteClicked(
        accountName: String,
        accountId: Int,
        isAccountSelectionEnabled: Boolean
    ) {
        Log.d("SearchAccountViewModel", "onAddNoteClicked: $accountName")
        NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/${isAccountSelectionEnabled}")
    }
}
