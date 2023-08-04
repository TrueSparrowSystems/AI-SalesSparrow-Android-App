package com.example.salessparrow.viewmodals

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.repository.AccountListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AccountListViewModel @Inject constructor(private val repository: AccountListRepository) :
    ViewModel() {

    fun getAccountList(callback: (List<CatImage>?) -> Unit, errorCallback: (String) -> Unit) {
        viewModelScope.launch {
            repository.getCatImages(callback, errorCallback)
        }
    }
}
