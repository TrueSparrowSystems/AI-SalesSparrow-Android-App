package com.truesparrow.sales.models

import com.google.gson.annotations.SerializedName

data class CrmOrganisationUsersResponse(
    @SerializedName("crm_organization_user_ids") val crmOrganizationUserIds: List<String>,
    @SerializedName("crm_organization_user_map_by_id") val crmOrganizationUserMapById: Map<String, CrmOrganisationUserNameDetails>
)

data class CrmOrganisationUserNameDetails(
    val id: String,
    val name: String
)
