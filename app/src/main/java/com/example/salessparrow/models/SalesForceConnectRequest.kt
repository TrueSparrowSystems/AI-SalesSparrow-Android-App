package com.example.salessparrow.models

data class SalesForceConnectRequest(
    val code: String,
    val redirect_uri: String
)