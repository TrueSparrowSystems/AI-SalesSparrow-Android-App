package com.truesparrow.sales.models

data class CreateAccountTaskRequest(
    val crm_organization_user_id: String,
    val description: String,
    val due_date: String
)

data class createAccountEventRequest(
    val start_datetime: String,
    val end_datetime: String,
    val description: String
)
