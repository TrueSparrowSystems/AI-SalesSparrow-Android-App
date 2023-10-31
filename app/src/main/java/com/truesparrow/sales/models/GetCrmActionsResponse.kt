package com.truesparrow.sales.models

data class GetCrmActionsResponse(
    val add_task_suggestions: List<AddTaskSuggestion>,
    val add_event_suggestions: List<AddEventSuggestions>
)

data class AddTaskSuggestion(
    val description: String,
    val due_date: String
)

data class AddEventSuggestions(
    val description: String,
    val start_datetime: String,
    val end_datetime: String
)

data class GetCrmActionRequest(
    val text: String
)

data class TaskSuggestions(
    val description: String,
    val due_date: String,
    val id : String
)