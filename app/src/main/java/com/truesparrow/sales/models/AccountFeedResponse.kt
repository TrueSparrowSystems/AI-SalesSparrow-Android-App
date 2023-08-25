package com.truesparrow.sales.models

data class AccountFeedResponse(
    val account_contact_associations_map_by_id: Map<String, AccountContactAssociationsMapById>,
    val account_ids: List<String>,
    val account_map_by_id: Map<String, AccountMapById?>,
    val contact_map_by_id: Map<String, ContactMapById>,
    val next_page_payload: NextPagePayload
)

data class AccountContactAssociationsMapById(
    val account_id: String,
    val contact_ids: List<String>
)

data class AccountMapById(
    val id: String,
    val name: String,
    val additional_fields: AdditionalFields
)

data class NextPagePayload(
    val pagination_identifier: String
)


data class ContactMapById(
    val id: String,
    val name: String,
    val additional_fields: AdditionalFields
)

data class AdditionalFields(
    val website: String,
    val email: String
)

data class AccountCardData(
    val id: String,
    val name: String,
    val website: String,
    val contactName: String,
)

