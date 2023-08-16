package com.truesparrow.sales.models

import com.google.gson.annotations.SerializedName

data class AccountListResponse(
    @SerializedName("account_ids") val accountIds: List<String>,
    @SerializedName("account_map_by_id") val accountMapById: Map<String, AccountDetails>
)

data class AccountDetails(
    val id: String,
    val name: String
)
