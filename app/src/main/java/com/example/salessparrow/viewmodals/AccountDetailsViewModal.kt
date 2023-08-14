package com.example.salessparrow.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.models.AccountNotesResponse
import com.example.salessparrow.repository.AccountDetailsRepository
import com.example.salessparrow.util.NetworkResponse
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