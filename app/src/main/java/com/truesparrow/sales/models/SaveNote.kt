package com.truesparrow.sales.models

data class SaveNote(
    val note_id: String
)

data class Tasks(
    val crm_user_id: String,
    val crm_user_name: String,
    val task_desc: String,
    val due_date: String,
    val id: String,
    val is_task_created: Boolean,
    val task_id: String ? = ""
)

data class SuggestedEvents(
    val description: String,
    val start_datetime: String,
    val end_datetime: String,
    val id: String,
    val is_event_created: Boolean,
    val event_id: String ? = ""
)

