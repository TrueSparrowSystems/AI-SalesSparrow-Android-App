package com.example.salessparrow.models

data class CurrentUser(
    val email: String,
    val id: Int? = null,
    val name: String
)