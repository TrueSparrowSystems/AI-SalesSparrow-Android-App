package com.truesparrow.sales.models

data class TaskDetailsResponse(
    val task_detail: TaskDetail
)
data class TaskDetail(
    val id: String,
    val creator_name: String,
    val crm_organization_user_name: String,
    val description: String,
    val due_date: String,
    val last_modified_time: String
)