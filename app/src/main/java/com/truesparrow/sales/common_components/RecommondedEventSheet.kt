package com.truesparrow.sales.common_components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.screens.EventScreen
import com.truesparrow.sales.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommondedEventSheet(
    bottomSheetVisible: () -> Unit,
    accountId: String? = "",
    startDate: String = "",
    endDate: String = "",
    startTime: String = "",
    endTime: String = "",
    eventDescription: String = "",
    selectedEventId: String = "",
    onAddEventClick: (
        id: String
    ) -> Unit = { _ -> },
    onCancelEventClick: (id: String, eventDescription: String, startDateTime: String, endDateTime: String) -> Unit = { _, _, _, _ -> },
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Log.i("event===", "Event: $eventDescription, Start Date: $startDate, End Date: $endDate, Start Time: $startTime, End Time: $endTime")

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = bottomSheetState,
        containerColor = white,
        onDismissRequest = {
            bottomSheetVisible()
        },
        shape = RoundedCornerShape(
            topStart = 10.dp, topEnd = 10.dp
        ),
    ) {

        EventScreen(
            accountId = accountId,
            startDate = startDate,
            endDate = endDate,
            startTime = startTime,
            endTime = endTime,
            eventDescription = eventDescription,
            selectedEventId = selectedEventId,
            eventId = "",
            onCancelEventClick = onCancelEventClick,
            onAddEventClick = onAddEventClick,
        )

    }
}
