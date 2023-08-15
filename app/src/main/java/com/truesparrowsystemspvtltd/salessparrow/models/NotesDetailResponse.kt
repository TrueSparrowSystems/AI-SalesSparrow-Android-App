package com.truesparrowsystemspvtltd.salessparrow.models

data class NotesDetailResponse(
    val note_detail: NoteDetail
)

data class NoteDetail(
    val creator: String,
    val id: String,
    val last_modified_time: String,
    val text: String
)