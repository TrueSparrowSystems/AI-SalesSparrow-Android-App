package com.example.salessparrow.viewmodals

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.repository.AccountListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountListViewModel @Inject constructor(private val repository: AccountListRepository) : ViewModel() {

    fun getCatImages(callback: (List<CatImage>?) -> Unit, errorCallback: (String) -> Unit) {
        repository.getCatImages(callback, errorCallback)
    }
}
