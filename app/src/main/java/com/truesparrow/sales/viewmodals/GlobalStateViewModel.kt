package com.truesparrow.sales.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GlobalStateViewModel : ViewModel() {

    private val taskDesc = MutableLiveData<String>()
    private val crmUserId = MutableLiveData<String>()
    private val crmUserName = MutableLiveData<String>()
    private val dueDate = MutableLiveData<String>()

    fun getTaskDesc(): LiveData<String> = taskDesc
    fun getCrmUserId(): LiveData<String> = crmUserId
    fun getCrmUserName(): LiveData<String> = crmUserName
    fun getDueDate(): LiveData<String> = dueDate


    fun setTaskDesc(data: String) {
        taskDesc.value = data
    }
    fun setCrmUserId(id: String) {
        crmUserId.value = id
    }
    fun setCrmUserName(name: String) {
        crmUserName.value = name
    }
    fun setDueDate(date: String) {
        dueDate.value = date
    }
}