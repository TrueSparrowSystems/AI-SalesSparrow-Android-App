package com.truesparrow.sales.models

data class CreateAccountTaskRequest(
    val crm_organization_user_id: String,
    val description: String,
    val due_date: String
)
