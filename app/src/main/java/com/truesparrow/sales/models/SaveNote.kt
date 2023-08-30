package com.truesparrow.sales.models

data class SaveNote(
    val note_id: String
)

data class Tasks(
    val crm_user_id: String,
    val crm_user_name : String,
    val task_desc : String,
    val due_date : String,
    val id : String,
)