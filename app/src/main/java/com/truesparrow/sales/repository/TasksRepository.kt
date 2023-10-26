package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.CreateAccountTaskRequest
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.models.TaskDetailsResponse
import com.truesparrow.sales.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val apiService: ApiService
) {
    private val _tasksLiveData = MutableLiveData<NetworkResponse<CreateAccountTaskResponse>>()
    val tasksLiveData: LiveData<NetworkResponse<CreateAccountTaskResponse>>
        get() = _tasksLiveData

    private val _updateTaskLiveData = MutableLiveData<NetworkResponse<Unit>>()
    val updateTaskLiveData: LiveData<NetworkResponse<Unit>>
        get() = _updateTaskLiveData

    private val _taskDetails = MutableLiveData<NetworkResponse<TaskDetailsResponse>>()
    val taskDetails: LiveData<NetworkResponse<TaskDetailsResponse>>
        get() = _taskDetails

    suspend fun getTaskDetails(accountId: String, taskId: String) {
        try {
            _taskDetails.postValue(NetworkResponse.Loading())
            val result = apiService.getTaskDetails(accountId, taskId)
            if (result.isSuccessful && result.body() != null) {
                _taskDetails.postValue(NetworkResponse.Success(result.body()!!))
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _taskDetails.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _taskDetails.postValue(NetworkResponse.Error("Error Fetching Task Details:}"))
            }
        } catch (e: Exception) {
            _taskDetails.postValue(NetworkResponse.Error("Error Fetching Task Details"))
        }
    }


    suspend fun createTask(
        accountId: String, crmOrganizationUserId: String, description: String, dueDate: String
    ) {
        try {
            _tasksLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.createAccountTasks(
                accountId, CreateAccountTaskRequest(
                    crm_organization_user_id = crmOrganizationUserId,
                    description = description,
                    due_date = dueDate
                )
            );
            if (result.isSuccessful && result.body() != null) {
                _tasksLiveData.postValue(NetworkResponse.Success(result.body()!!))
                Log.i("res", "result - create Task: ${result.body()}")
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _tasksLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _tasksLiveData.postValue(NetworkResponse.Error("Error Creating Task:}"))
            }
        } catch (e: Exception) {
            _tasksLiveData.postValue(NetworkResponse.Error("Error  Creating Task:"))
        }
    }

    suspend fun updateTask(
        accountId: String,
        taskId: String,
        crmOrganizationUserId: String,
        description: String,
        dueDate: String
    ) {
        try {
            _updateTaskLiveData.postValue(NetworkResponse.Loading())
            val result = apiService.updateTask(
                accountId, taskId, CreateAccountTaskRequest(
                    crm_organization_user_id = crmOrganizationUserId,
                    description = description,
                    due_date = dueDate
                )
            );
            if (result.isSuccessful && result.body() != null) {
                _updateTaskLiveData.postValue(NetworkResponse.Success(result.body()!!))
            } else if (result.errorBody() != null) {
                val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                _updateTaskLiveData.postValue(NetworkResponse.Error(errorObj.getString("message")))
            } else {
                _updateTaskLiveData.postValue(NetworkResponse.Error("Error Updating Task:}"))
            }
        } catch (e: Exception) {
            _updateTaskLiveData.postValue(NetworkResponse.Error("Error  Updating Task:"))
        }
    }
}