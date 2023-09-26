package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.CreateAccountEventResponse
import com.truesparrow.sales.models.createAccountEventRequest
import com.truesparrow.sales.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: ApiService) {

    private val _eventsLiveData = MutableLiveData<NetworkResponse<CreateAccountEventResponse>>()
    val eventsLiveData: LiveData<NetworkResponse<CreateAccountEventResponse>>
        get() = _eventsLiveData

    private val _updateEventLiveData = MutableLiveData<NetworkResponse<Unit>>()
    val updateEventLiveData: LiveData<NetworkResponse<Unit>>
        get() = _updateEventLiveData

    suspend fun createEvent(
        accountId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        try {
            _eventsLiveData.postValue(NetworkResponse.Loading())
            Log.i("Event Screen re", "$accountId $startDateTime $endDateTime $description")
            val result = apiService.createAccountEvents(
                accountId,
                createAccountEventRequest(
                    start_datetime = startDateTime,
                    end_datetime = endDateTime,
                    description = description
                )
            )
            if (result.isSuccessful && result.body() != null) {
                _eventsLiveData.postValue(NetworkResponse.Success(result.body()!!))
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _eventsLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _eventsLiveData.postValue(NetworkResponse.Error("Error Creating Task:}"))
            }
        } catch (e: Exception) {
            _eventsLiveData.postValue(NetworkResponse.Error("Error Creating Event"))
        }
    }

    suspend fun updateEvent(
        accountId: String,
        eventId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        try {
            _updateEventLiveData.postValue(NetworkResponse.Loading())
            Log.i("Event Screen re", "$accountId $startDateTime $endDateTime $description")
            val result = apiService.updateEvent(
                accountId,
                eventId,
                createAccountEventRequest(
                    start_datetime = startDateTime,
                    end_datetime = endDateTime,
                    description = description
                )
            )
            if (result.isSuccessful && result.body() != null) {
                _updateEventLiveData.postValue(NetworkResponse.Success(result.body()!!))
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _updateEventLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _updateEventLiveData.postValue(NetworkResponse.Error("Error Updating Task:}"))
            }
        } catch (e: Exception) {
            _updateEventLiveData.postValue(NetworkResponse.Error("Error Updating Event"))
        }
    }

}