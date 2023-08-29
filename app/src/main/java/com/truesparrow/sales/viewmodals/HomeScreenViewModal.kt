package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.AccountCardData
import com.truesparrow.sales.models.AccountFeedResponse
import com.truesparrow.sales.repository.HomeRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModal @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

   private val _accountCardList = MutableLiveData<List<AccountCardData>>()
    val accountCardList: LiveData<List<AccountCardData>> = _accountCardList

     var currentPaginationIdentifier : String ?= null

    val accountFeedLiveData: LiveData<NetworkResponse<AccountFeedResponse>>
        get() = homeRepository.accountFeedLiveData


    init {
        viewModelScope.launch {
            Log.i("HomeScreenViewModal", "init viewModelScope")
            homeRepository.getAccountFeed(currentPaginationIdentifier)
        }
    }

    fun getAccountFeed(paginationIdentifier: String) {
        viewModelScope.launch {
            Log.i("HomeScreenViewModal", "getAccountFeed $paginationIdentifier")
            homeRepository.getAccountFeed(paginationIdentifier)
        }
    }
}