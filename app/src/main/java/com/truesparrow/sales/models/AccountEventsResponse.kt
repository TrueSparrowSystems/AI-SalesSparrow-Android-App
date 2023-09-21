package com.truesparrow.sales.models

data class AccountEventsResponse(
    val event_ids: List<String>,
    val event_map_by_id: Map<String, EventMapById>
)

data class EventMapById(
    val id: String,
    val creator_name: String,
    val description: String,
    val start_datetime: String,
    val end_datetime: String,
    val last_modified_time: String
)

data class Event(
    val id: String,
    val creator_name: String,
    val description: String,
    val start_datetime: String,
    val end_datetime: String,
    val last_modified_time: String
)