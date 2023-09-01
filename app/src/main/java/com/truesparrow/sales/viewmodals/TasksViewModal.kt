package com.truesparrow.sales.viewmodals

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.models.Task
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


    private var tasksScreenSelectedUserName: String = "Select"

   private var tasksScreenSelectedUserId: String= ""

    private var taskScreenSelectedDueDate: String = ""


    fun getTasksScreenSelectedUserName() : String{
        return tasksScreenSelectedUserName
    }

    fun getTasksScreenSelectedUserId() : String{
        return tasksScreenSelectedUserId
    }

    fun getTaskScreenSelectedDueDate() : String{
        return taskScreenSelectedDueDate
    }

    fun setTasksScreenSelectedUserId(userId: String){
        tasksScreenSelectedUserId = userId
    }

    fun setTasksScreenSelectedUserName(userName: String){
        tasksScreenSelectedUserName = userName
    }

    fun setTaskScreenSelectedDueDate(dueDate: String){
        taskScreenSelectedDueDate = dueDate
    }


    fun createTask(
        accountId: String, crmOrganizationUserId: String, description: String, dueDate: String
    ) {
        Log.i("Task", "Account ID: $accountId, User ID: $crmOrganizationUserId, Description: $description, Due Date: $dueDate")

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