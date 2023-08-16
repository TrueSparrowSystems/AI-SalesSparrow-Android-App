package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.AccountNotesResponse
import com.truesparrow.sales.repository.AccountDetailsRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountDetailsViewModal @Inject constructor(
    private val accountDetailsRepository: AccountDetailsRepository
) : ViewModel() {

    val accountDetailsLiveData: LiveData<NetworkResponse<AccountNotesResponse>>
        get() = accountDetailsRepository.accountNotes



    fun getAccountNotes(accountId: String) {
        Log.i("AccountDetails", "Account Id: $accountId")
        viewModelScope.launch {
            accountDetailsRepository.getAccountNotes(accountId)
        }

    }
}