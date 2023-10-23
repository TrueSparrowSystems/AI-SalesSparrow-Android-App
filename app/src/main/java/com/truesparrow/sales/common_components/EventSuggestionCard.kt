package com.truesparrow.sales.common_components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.models.SuggestedEvents
import com.truesparrow.sales.util.convertToISO8601
import com.truesparrow.sales.viewmodals.NotesViewModel
import java.util.Calendar


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventSuggestionCard(
    accountId: String? = "",
    startDate: String = "",
    endDate: String = "",
    startTime: String = "",
    endTime: String = "",
    eventDescription: String = "",
    eventId: String = "",
    isEventAdded: Boolean = false,
    onDeleteEventClick: (id: String) -> Unit = {},
    onAddEventClick: (
        accountId: String?,
        eventDescription: String,
        startDate: String,
        endDate: String,
        startTime: String,
        endTime: String,
        id: String
    ) -> Unit = { _, _, _, _, _, _, _ -> },
    onEditEventClick: (id: String) -> Unit,
    onCancelEventClick: (id: String) -> Unit = {},
    createEventApiInProgress: Boolean = false,
    id: String = "",
    index: Int = 0,
    noteViewModal: NotesViewModel? = null
) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedStartDateText by remember { mutableStateOf(startDate) }
    var selectedEndDateText by remember { mutableStateOf(endDate) }
    var selectedStartTimeText by remember { mutableStateOf(startTime) }
    var selectedEndTimeText by remember { mutableStateOf(endTime) }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val startDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedStartDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            Log.i(
                "EventScreen",
                "Selected Date ${
                    convertToISO8601(
                        selectedStartDateText,
                        selectedStartTimeText
                    )
                } $selectedStartDateText $selectedStartTimeText "
            )
            noteViewModal!!.updateSuggestedEventById(
                id,
                SuggestedEvents(
                    description = eventDescription,
                    start_datetime = convertToISO8601(selectedStartDateText, selectedStartTimeText),
                    end_datetime = convertToISO8601(selectedEndDateText, selectedEndTimeText),
                    id = id,
                    is_event_created = false
                )
            )
        }, year, month, dayOfMonth
    )

    val endDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedEndDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            noteViewModal!!.updateSuggestedEventById(
                id,
                SuggestedEvents(
                    description = eventDescription,
                    start_datetime = convertToISO8601(selectedStartDateText, selectedStartTimeText),
                    end_datetime = convertToISO8601(selectedEndDateText, selectedEndTimeText),
                    id = id,
                    is_event_created = false
                )
            )
        }, year, month, dayOfMonth
    )

    val startTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            Log.i("EventScreen", "Selected Time $selectedHour:$selectedMinute")
            selectedStartTimeText = "$selectedHour:$selectedMinute"
            noteViewModal!!.updateSuggestedEventById(
                id,
                SuggestedEvents(
                    description = eventDescription,
                    start_datetime = convertToISO8601(selectedStartDateText, selectedStartTimeText),
                    end_datetime = convertToISO8601(selectedEndDateText, selectedEndTimeText),
                    id = id,
                    is_event_created = false
                )
            )
        }, hour, minute, false
    )

    val endTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedEndTimeText = "$selectedHour:$selectedMinute"
            noteViewModal!!.updateSuggestedEventById(
                id,
                SuggestedEvents(
                    description = eventDescription,
                    start_datetime = convertToISO8601(selectedStartDateText, selectedStartTimeText),
                    end_datetime = convertToISO8601(selectedEndDateText, selectedEndTimeText),
                    id = id,
                    is_event_created = false
                )
            )
        }, hour, minute, false
    )

    endDatePicker.datePicker.minDate = calendar.timeInMillis
    startDatePicker.datePicker.minDate = calendar.timeInMillis

    Column(verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
            )
            .border(
                width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(0.75.dp)
            .fillMaxWidth()
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
            .onSizeChanged {
                itemHeight = with(density) { (it.height).toDp() }
            }) {

        Column(
            verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 5.dp)
                )
        ) {

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x0F000000),
                        shape = RoundedCornerShape(size = 5.57143.dp)
                    )
                    .fillMaxWidth()
                    .background(
                        color = Color(0x33F6F7F8),
                        shape = RoundedCornerShape(size = 5.57143.dp)
                    )
//                    .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
                    .clickable {
                        if (!isEventAdded) {
                            onEditEventClick(id)
                        }
                    },
            ) {
                Text(
                    text = eventDescription,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.64.sp,
                    ),
                    modifier = Modifier
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "txt_event_suggestion_description_${index}"
                            contentDescription = "txt_event_suggestion_task_description_${index}"
                        }
                )

            }

            Spacer(modifier = Modifier.height(10.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Start",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.48.sp,
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9E9E9),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(138.dp)
                        .height(36.dp)
                        .padding(start = 14.dp, end = 8.dp, bottom = 10.dp)
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_event_suggestion_start_date_${index}"
                            contentDescription = "btn_event_suggestion_start_date_${index}"
                        }
                        .clickable {
                            startDatePicker.show()
                        }
                ) {
                    Text(
                        text = if (selectedStartDateText.isNotEmpty()) {
                            selectedStartDateText
                        } else {
                            "Select Date"
                        },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar"
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        androidx.compose.ui.Alignment.CenterHorizontally
                    ),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9E9E9),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(138.dp)
                        .height(36.dp)
                        .padding(start = 14.dp, end = 8.dp, bottom = 10.dp)
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_event_suggestion_start_time_${index}"
                            contentDescription = "btn_event_suggestion_start_time_${index}"
                        }
                        .clickable {
                            startTimePicker.show()
                        }
                ) {
                    Text(
                        text = if (selectedStartTimeText.isNotEmpty()) {
                            selectedStartTimeText
                        } else {
                            "Select Time"
                        },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "clock"
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    androidx.compose.ui.Alignment.CenterHorizontally
                ),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {

                Text(
                    text = "End  ",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.48.sp,
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        androidx.compose.ui.Alignment.CenterHorizontally
                    ),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9E9E9),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(138.dp)
                        .height(36.dp)
                        .padding(start = 14.dp, end = 8.dp, bottom = 10.dp)
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_event_suggestion_end_date_${index}"
                            contentDescription = "btn_event_suggestion_end_date_${index}"
                        }
                        .clickable {
                            endDatePicker.show()
                        }
                ) {
                    Text(
                        text = if (selectedEndDateText.isNotEmpty()) {
                            selectedEndDateText
                        } else {
                            "Select Date"
                        },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar"
                    )

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        androidx.compose.ui.Alignment.CenterHorizontally
                    ),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9E9E9),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(138.dp)
                        .height(36.dp)
                        .padding(start = 14.dp, end = 8.dp, bottom = 10.dp)
                        .clickable { endTimePicker.show() }
                ) {
                    Text(
                        text = if (selectedEndTimeText.isNotEmpty()) {
                            selectedEndTimeText
                        } else {
                            "Select Time"
                        },
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "clock"
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))

            if (!isEventAdded) {
                val buttonColor = if (selectedEndDateText.isNotEmpty() && selectedStartDateText.isNotEmpty() && selectedStartTimeText.isNotEmpty() && selectedEndTimeText.isNotEmpty() && eventDescription.isNotEmpty()) {
                    Color(0xFF212653)
                } else {
                    Color(0xFF212653).copy(alpha = 0.7f)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {
                        onAddEventClick(
                            accountId,
                            eventDescription,
                            selectedStartDateText,
                            selectedEndDateText,
                            selectedStartTimeText,
                            selectedEndTimeText,
                            id
                        )
                    },
                        enabled = selectedEndDateText.isNotEmpty() && selectedStartDateText.isNotEmpty() && selectedStartTimeText.isNotEmpty() && selectedEndTimeText.isNotEmpty() && eventDescription.isNotEmpty(),
                        contentPadding = PaddingValues(all = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.White
                        ),
                        modifier = Modifier
                            .background(
                                color = buttonColor, shape = RoundedCornerShape(size = 5.dp)
                            )
                            .height(32.dp)
                            .clip(shape = RoundedCornerShape(size = 5.dp))
                            .semantics {
                                testTagsAsResourceId = true
                                testTag = if (createEventApiInProgress) {
                                    "txt_create_event_adding_task_${index}"
                                } else {
                                    "btn_create_event_add_task_${index}"
                                }
                                contentDescription = if (createEventApiInProgress) {
                                    "txt_create_event_adding_task_${index}"
                                } else {
                                    "btn_create_event_add_task_${index}"
                                }
                            }

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                4.dp, Alignment.CenterHorizontally
                            ), verticalAlignment = Alignment.CenterVertically

                        ) {
                            val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    add(ImageDecoderDecoder.Factory())
                                } else {
                                    add(GifDecoder.Factory())
                                }
                            }.build()

                            if (createEventApiInProgress) {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        R.drawable.loader, imageLoader
                                    ),
                                    contentDescription = "Loader",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .width(width = 12.dp)
                                        .height(height = 12.dp)
                                )
                            }

                            Text(text = if (createEventApiInProgress) {
                                "Adding Event..."
                            } else {
                                "Add Event"
                            }, color = Color.White, style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF),
                                letterSpacing = 0.48.sp,
                            ), modifier = Modifier.semantics {
                                testTagsAsResourceId = true
                                testTag = if (!createEventApiInProgress) {
                                    "txt_create_event_add_task_${index}"
                                } else {
                                    "txt_create_event_adding_task_${index}"
                                }
                                contentDescription = if (!createEventApiInProgress) {
                                    "txt_create_event_add_task_${index}"
                                } else {
                                    "txt_create_event_adding_task_${index}"
                                }
                            })
                        }
                    }
                    Box(modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .border(1.dp, Color(0xFF5D678D), shape = RoundedCornerShape(4.dp))
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_create_event_cancel"
                            contentDescription = "btn_create_event_cancel"
                        }

                    ) {
                        Text(text = "Cancel", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF5D678D),
                            letterSpacing = 0.48.sp,
                        ), modifier = Modifier
                            .padding(8.dp)
                            .semantics {
                                testTagsAsResourceId = true
                                testTag = "txt_create_event_cancel"
                                contentDescription = "txt_create_event_cancel"
                            }
                            .clickable {
                                onCancelEventClick(id)
                            })

                    }
                }
            }
            if (isEventAdded) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(Color(0x3362E17D))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE9E9E9),
                                shape = RoundedCornerShape(size = 0.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.success_toast_check),
                            contentDescription = "Success",
                            modifier = Modifier
                                .height(18.dp)
                                .width(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Event added", modifier = Modifier.semantics {
                                testTagsAsResourceId = true
                                testTag = "txt_create_event_task_added"
                                contentDescription = "txt_create_event_task_added"
                            }, style = TextStyle(
                                fontSize = 12.sp, lineHeight = 24.sp, color = Color(0xFF444A62)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventSuggestionCardPreview() {
    EventSuggestionCard(
        startDate = "12/12/2021",
        endDate = "12/12/2021",
        startTime = "12:00",
        endTime = "12:00",
        eventDescription = "Event Description",
        eventId = "123",
        isEventAdded = false,
        onAddEventClick = { _, _, _, _, _, _, _ -> },
        onEditEventClick = {},
        onCancelEventClick = {},
        createEventApiInProgress = false,
        id = "123",
        index = 0,
        noteViewModal = null,
    )
}