package com.truesparrow.sales.models

data class EventDetailsResponse(
    val event_detail: EventDetail
)

data class EventDetail(
    val id: String,
    val creator_name: String,
    val description: String,
    val start_datetime: String,
    val end_datetime: String,
    val last_modified_time: String
)