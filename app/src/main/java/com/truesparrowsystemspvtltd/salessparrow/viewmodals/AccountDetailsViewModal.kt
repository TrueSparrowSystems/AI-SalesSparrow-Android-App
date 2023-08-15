package com.truesparrowsystemspvtltd.salessparrow.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrowsystemspvtltd.salessparrow.models.AccountNotesResponse
import com.truesparrowsystemspvtltd.salessparrow.repository.AccountDetailsRepository
import com.truesparrowsystemspvtltd.salessparrow.util.NetworkResponse
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