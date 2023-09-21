package com.truesparrow.sales.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.CreateAccountEventResponse
import com.truesparrow.sales.util.NetworkResponse
import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: ApiService) {

    private val _eventsLiveData = MutableLiveData<NetworkResponse<CreateAccountEventResponse>>()
    val tasksLiveData: LiveData<NetworkResponse<CreateAccountEventResponse>>
        get() = _eventsLiveData

    suspend fun createEvent(
        accountId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        try {
//            _eventsLiveData.postValue(NetworkResponse.Loading())
//            val result = apiService.createAccountEvents(
//                accountId,
//                startDateTime,
//                endDateTime,
//                description
//            )
//            if (result.isSuccessful && result.body() != null) {
//                _eventsLiveData.postValue(NetworkResponse.Success(result.body()!!))
//            } else {
//                _eventsLiveData.postValue(NetworkResponse.Error("Error Creating Event"))
//            }
        } catch (e: Exception) {
            _eventsLiveData.postValue(NetworkResponse.Error("Error Creating Event"))
        }
    }
}