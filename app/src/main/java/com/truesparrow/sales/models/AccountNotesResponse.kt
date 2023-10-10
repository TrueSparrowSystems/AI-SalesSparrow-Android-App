package com.truesparrow.sales.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AccountNotesResponse(
    val note_ids: List<String>,
    val note_map_by_id: Map<String, NoteMapById>
)

data class NoteMapById(
    val creator: String,
    val id: String,
    val last_modified_time: String,
    val text_preview: String
)


data class Note(
    val creator: String,
    val id: String,
    val last_modified_time: String,
    val text_preview: String
)

data class NoteData(
    val id: String,
    val text: String,
    val shouldShowCrmSuggestion: Boolean = false
)

@Parcelize
data class NoteDetailsObject(
    val id: String,
    val text: String,
    val shouldShowCrmSuggestion: Boolean = false
) : Parcelable