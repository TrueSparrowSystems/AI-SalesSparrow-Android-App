package com.truesparrow.sales.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truesparrow.sales.api.ApiService
import com.truesparrow.sales.models.CreateAccountTaskRequest
import com.truesparrow.sales.models.CreateAccountTaskResponse
import com.truesparrow.sales.util.NetworkResponse
import org.json.JSONObject
import javax.inject.Inject

class TasksRepository @Inject constructor(
    private val apiService: ApiService
) {
    private val _tasksLiveData = MutableLiveData<NetworkResponse<CreateAccountTaskResponse>>()
    val tasksLiveData: LiveData<NetworkResponse<CreateAccountTaskResponse>>
        get() = _tasksLiveData


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
}