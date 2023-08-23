package com.truesparrow.sales.models

data class GetCrmActionsResponse(
    val add_task_suggestions: List<AddTaskSuggestion>
)

data class AddTaskSuggestion(
    val description: String,
    val due_date: String
)

data class GetCrmActionRequest(
    val text: String
)