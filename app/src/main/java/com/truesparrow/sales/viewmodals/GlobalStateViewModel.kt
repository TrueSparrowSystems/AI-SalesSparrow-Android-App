package com.truesparrow.sales.viewmodals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GlobalStateViewModel : ViewModel() {

    private val recommdedTasksMap = mutableMapOf<String, DataValues>()
    data class DataValues(
        val taskDesc: MutableLiveData<String> = MutableLiveData(),
        val crmUserId: MutableLiveData<String> = MutableLiveData(),
        val crmUserName: MutableLiveData<String> = MutableLiveData(),
        val dueDate: MutableLiveData<String> = MutableLiveData()
    )

    fun setValuesById(
        id: String,
        taskDesc: String? = null,
        crmUserId: String? = null,
        crmUserName: String? = null,
        dueDate: String? = null
    ) {
        val dataValues = recommdedTasksMap[id] ?: DataValues()
        taskDesc?.let { dataValues.taskDesc.value = it }
        crmUserId?.let { dataValues.crmUserId.value = it }
        crmUserName?.let { dataValues.crmUserName.value = it }
        dueDate?.let { dataValues.dueDate.value = it }
        recommdedTasksMap[id] = dataValues
    }

    fun getTaskDescById(id: String): LiveData<String>? = recommdedTasksMap[id]?.taskDesc
    fun getCrmUserIdById(id: String): LiveData<String>? = recommdedTasksMap[id]?.crmUserId
    fun getCrmUserNameById(id: String): LiveData<String>? = recommdedTasksMap[id]?.crmUserName
    fun getDueDateById(id: String): LiveData<String>? = recommdedTasksMap[id]?.dueDate
}