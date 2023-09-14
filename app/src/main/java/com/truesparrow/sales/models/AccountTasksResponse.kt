package com.truesparrow.sales.models

data class AccountTasksResponse(
    val task_ids: List<String>,
    val task_map_by_id: Map<String, TaskMapById>
)

data class TaskMapById(
    val creator_name: String,
    val crm_organization_user_name: String,
    val description: String,
    val due_date: String,
    val id: String,
    val last_modified_time: String
)

data class Task(
    val creator_name: String,
    val crm_organization_user_name: String,
    val description: String,
    val due_date: String,
    val id: String,
    val last_modified_time: String
)