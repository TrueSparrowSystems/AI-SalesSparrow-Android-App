package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.repository.TasksRepository
import com.truesparrow.sales.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class TasksViewModal @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    val tasksLiveData: LiveData<NetworkResponse<CreateAccountTaskResponse>>
        get() = tasksRepository.tasksLiveData

    fun createTask(
        accountId: String, crmOrganizationUserId: String, description: String, dueDate: String
    ) {
        viewModelScope.launch {
            tasksRepository.createTask(
                accountId = accountId,
                crmOrganizationUserId = crmOrganizationUserId,
                description = description,
                dueDate = dueDate
            )
        }
    }
}