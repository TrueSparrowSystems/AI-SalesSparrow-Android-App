package com.example.salessparrow.viewmodals

import com.example.salessparrow.repository.SearchAccountRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.data.DataWrapper
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
}
