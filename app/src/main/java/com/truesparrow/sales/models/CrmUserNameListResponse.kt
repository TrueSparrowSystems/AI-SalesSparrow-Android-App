package com.truesparrow.sales.models

import com.google.gson.annotations.SerializedName

data class CrmUserNameListResponse(
    @SerializedName("crm_org_user_ids") val accountIds: List<String>,
    @SerializedName("crm_org_user_map_by_id") val accountMapById: Map<String, AccountDetails>
)

data class UserNameDetails(
    val id: String,
    val name: String
)
