package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.CreateAccountEventResponse
import com.truesparrow.sales.models.EventDetailsResponse
import com.truesparrow.sales.repository.EventRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModal @Inject constructor(private val eventRepository: EventRepository) : ViewModel(){

    val eventsLiveData : LiveData<NetworkResponse<CreateAccountEventResponse>>
        get() = eventRepository.eventsLiveData

    val updateEventLiveData : LiveData<NetworkResponse<Unit>>
        get() = eventRepository.updateEventLiveData

    val eventDetails : LiveData<NetworkResponse<EventDetailsResponse>>
        get() = eventRepository.eventDetails

     fun createEvent(
        accountId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        viewModelScope.launch {
            Log.i("EventScreen", "$accountId $startDateTime $endDateTime $description")
            eventRepository.createEvent(accountId, startDateTime, endDateTime, description)
        }

    }

    fun updateEvent(
        accountId: String,
        eventId: String,
        startDateTime: String,
        endDateTime: String,
        description: String,
    ) {
        viewModelScope.launch {
            Log.i("EventScreen", "$accountId $startDateTime $endDateTime $description")
            eventRepository.updateEvent(accountId, eventId, startDateTime, endDateTime, description)
        }

    }

    fun eventDetails(
        accountId: String,
        eventId: String,
    ) {
        viewModelScope.launch {
            eventRepository.getEventDetails(accountId, eventId)
        }
    }


}