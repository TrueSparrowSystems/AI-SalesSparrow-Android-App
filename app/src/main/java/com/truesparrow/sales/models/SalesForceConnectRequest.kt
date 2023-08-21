package com.truesparrow.sales.models

data class SalesForceConnectRequest(
    val code: String,
    val redirect_uri: String
)