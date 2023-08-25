package com.truesparrow.sales.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.AccountFeedResponse
import com.truesparrow.sales.repository.HomeRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModal @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    val accountFeedLiveData: LiveData<NetworkResponse<AccountFeedResponse>>
        get() = homeRepository.accountFeedLiveData


    init {
        viewModelScope.launch {
            homeRepository.getAccountFeed()
        }
    }
}