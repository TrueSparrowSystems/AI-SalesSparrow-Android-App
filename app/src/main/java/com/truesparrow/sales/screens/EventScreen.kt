package com.truesparrow.sales.screens

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.convertToISO8601
import com.truesparrow.sales.viewmodals.EventViewModal
import java.util.Calendar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EventScreen(
    accountId: String? = "",
    accountName: String? = "",
    startDate: String = "",
    endDate: String = "",
    startTime: String = "",
    endTime: String = "",
    eventDescription: String = "",
    eventId: String = "",
    onAddEventClick: (id: String) -> Unit = {},
    onCancelEventClick: (id: String, eventDescription: String, startDateTime: String, endDateTime: String) -> Unit = { _, _, _, _ -> },
    selectedEventId: String = "",
    isEventScreenEditable: Boolean = true,
    shouldNavigateBackToAccountDetailsScreen: Boolean = false
) {


    var createEventApiIsSuccess by remember { mutableStateOf(false) }
    var createEventApiInProgress by remember { mutableStateOf(false) }

    var updateEventApiIsSuccess by remember { mutableStateOf(false) }
    var updateEventApiInProgress by remember { mutableStateOf(false) }

    var eventDescription = eventDescription
    var eventDesc by remember { mutableStateOf(eventDescription) }
    val eventViewModal: EventViewModal = hiltViewModel()

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

    val eventDetailsApiRespone by eventViewModal.eventDetails.observeAsState()

    if (eventId.isNotEmpty()) {
        LaunchedEffect(key1 = accountId + eventId) {
            if (accountId != null) {
                eventViewModal.eventDetails(accountId = accountId, eventId = eventId)
            }
        }
    }


    eventDetailsApiRespone?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                Log.i("EventScreen cc", "Event Details ${response.data?.event_detail?.description}")
                eventDescription = response.data?.event_detail?.description ?: ""
            }

            is NetworkResponse.Loading -> {
            }

            is NetworkResponse.Error -> {
            }
        }

    }


    val startDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedStartDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val endDatePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedEndDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    val startTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            Log.i("EventScreen", "Selected Time $selectedHour:$selectedMinute")
            selectedStartTimeText = "$selectedHour:$selectedMinute"
            if (selectedEndTimeText.isEmpty()) {
                selectedEndTimeText = "${selectedHour + 1}:$selectedMinute"
            }
        }, hour, minute, false
    )

    val endTimePicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            selectedEndTimeText = "$selectedHour:$selectedMinute"
        }, hour, minute, false
    )



    endDatePicker.datePicker.minDate = calendar.timeInMillis
    startDatePicker.datePicker.minDate = calendar.timeInMillis


    val createEventResponse by eventViewModal.eventsLiveData.observeAsState()

    createEventResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                createEventApiIsSuccess = true
                createEventApiInProgress = false
                CustomToast(message = "Event Added", type = ToastType.Success)
                if (selectedEventId.isNotEmpty()) {
                    onAddEventClick(selectedEventId)
                } else if (shouldNavigateBackToAccountDetailsScreen) {
                    NavigationService.navigateBackToAccountDetailsScreen()
                }
            }

            is NetworkResponse.Loading -> {
                createEventApiInProgress = true
            }

            is NetworkResponse.Error -> {
                createEventApiInProgress = false
                CustomToast(
                    message = it.message ?: "Failed to create the event",
                    type = ToastType.Error
                )
            }
        }
    }

    val updateEventResponse by eventViewModal.updateEventLiveData.observeAsState()

    updateEventResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                updateEventApiIsSuccess = true
                updateEventApiInProgress = false

                CustomToast(message = "Event Updated", type = ToastType.Success)
                if (shouldNavigateBackToAccountDetailsScreen) {
                    NavigationService.navigateBackToAccountDetailsScreen()
                } else {
                    NavigationService.navigateBackToAccountDetailsScreen()
                }
            }

            is NetworkResponse.Loading -> {
                updateEventApiInProgress = true
            }

            is NetworkResponse.Error -> {
                updateEventApiInProgress = false
                CustomToast(
                    message = it.message ?: "Failed to update the event",
                    type = ToastType.Error
                )
            }
        }
    }


    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = if (createEventApiIsSuccess) {
                    "Done"
                } else {
                    "Cancel"
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF5D678D),
                    letterSpacing = 0.56.sp,
                ),
                modifier = Modifier
                    .clickable(interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {
                            if (selectedEventId.isNotEmpty()) {
                                val iso8601StartDateTime =
                                    convertToISO8601(selectedStartDateText, selectedStartTimeText);
                                val iso8601EndDateTime =
                                    convertToISO8601(selectedEndDateText, selectedEndTimeText);

                                onCancelEventClick(
                                    selectedEventId,
                                    eventDesc,
                                    iso8601StartDateTime,
                                    iso8601EndDateTime
                                )
                            } else {
                                NavigationService.navigateBack()
                            }
                        })
                    .semantics {
                        testTagsAsResourceId = true
                        testTag =
                            if (createEventApiIsSuccess) "btn_done_create_event" else "btn_cancel_create_event"
                        contentDescription =
                            if (createEventApiIsSuccess) "btn_done_create_event" else "btn_cancel_create_event"
                    },
            )

            val buttonColor =
                if (eventDesc.isNotEmpty() && accountId?.isNotEmpty() == true) {
                    Color(0xFF212653)
                } else {
                    Color(0xFF212653).copy(alpha = 0.7f)
                }
            if (accountId != null && isEventScreenEditable) {
                Button(onClick = {
                    if (selectedStartDateText.isEmpty() || selectedStartTimeText.isEmpty() || selectedEndDateText.isEmpty() || selectedEndTimeText.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please select start and end date",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                    if (selectedEndDateText == selectedStartDateText) {
                        if (selectedEndTimeText < selectedStartTimeText) {
                            Toast.makeText(
                                context,
                                "End time should be greater than start time",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    if (selectedEndDateText != selectedStartDateText) {
                        val startDate = selectedStartDateText.split("/")
                        val endDate = selectedEndDateText.split("/")
                        val startYear = startDate[2].toInt()
                        val startMonth = startDate[1].toInt()
                        val startDay = startDate[0].toInt()
                        val endYear = endDate[2].toInt()
                        val endMonth = endDate[1].toInt()
                        val endDay = endDate[0].toInt()
                        val startDateCalendar = Calendar.getInstance()
                        startDateCalendar.set(startYear, startMonth, startDay)
                        val endDateCalendar = Calendar.getInstance()
                        endDateCalendar.set(endYear, endMonth, endDay)
                        val difference =
                            endDateCalendar.timeInMillis - startDateCalendar.timeInMillis
                        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
                        if (days > 14) {
                            Toast.makeText(
                                context,
                                "Difference between start date and end date should be less than 14 days",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    //if all value exist then execute the below functions
                    if (selectedStartDateText.isNotEmpty() && selectedEndDateText.isNotEmpty() && selectedStartTimeText.isNotEmpty() && selectedEndTimeText.isNotEmpty() && eventDesc.isNotEmpty() && accountId.isNotEmpty() && !(createEventApiInProgress || createEventApiIsSuccess)) {
                        val iso8601StartDateTime =
                            convertToISO8601(selectedStartDateText, selectedStartTimeText);
                        val iso8601EndDateTime =
                            convertToISO8601(selectedEndDateText, selectedEndTimeText);


                        Log.i(
                            "EventScreen",
                            "Create Event Clicked $accountId $iso8601StartDateTime $iso8601EndDateTime  $eventDesc "
                        )

                        if (eventId.isNotEmpty()) {
                            eventViewModal.updateEvent(
                                accountId = accountId,
                                eventId = eventId,
                                startDateTime = iso8601StartDateTime,
                                endDateTime = iso8601EndDateTime,
                                description = eventDesc
                            )
                        } else {

                            eventViewModal.createEvent(
                                accountId = accountId,
                                startDateTime = iso8601StartDateTime,
                                endDateTime = iso8601EndDateTime,
                                description = eventDesc
                            )
                        }
                    }
                },

                    enabled = eventDesc.isNotEmpty() && accountId.isNotEmpty() && !(createEventApiInProgress || createEventApiIsSuccess),
                    contentPadding = PaddingValues(all = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, contentColor = Color.White
                    ),
                    modifier = Modifier
                        .background(
                            color = buttonColor, shape = RoundedCornerShape(size = 5.dp)
                        )
                        .width(92.dp)
                        .height(46.dp)
                        .clip(shape = RoundedCornerShape(size = 5.dp))
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_save_event"
                            contentDescription = "btn_save_event"
                        }


                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                            if (Build.VERSION.SDK_INT >= 28) {
                                add(ImageDecoderDecoder.Factory())
                            } else {
                                add(GifDecoder.Factory())
                            }
                        }.build()

                        Image(painter = if (createEventApiInProgress || updateEventApiInProgress) {
                            rememberAsyncImagePainter(R.drawable.loader, imageLoader)
                        } else if (createEventApiIsSuccess || updateEventApiIsSuccess) {
                            painterResource(id = R.drawable.check)
                        } else {
                            painterResource(id = R.drawable.cloud)
                        },
                            contentDescription = "cloud",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .width(width = 17.dp)
                                .height(height = 12.dp)
                                .semantics {
                                    testTagsAsResourceId = true
                                    testTag = "cloud"
                                })
                        Text(text = if (createEventApiInProgress || updateEventApiInProgress) {
                            "Saving..."
                        } else if (createEventApiIsSuccess || updateEventApiIsSuccess) {
                            "Saved"
                        } else {
                            "Save"
                        }, color = Color.White, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp,
                        ), modifier = Modifier.semantics {
                            testTagsAsResourceId = true
                            testTag =
                                if (createEventApiInProgress) "txt_create_event_saved" else "txt_create_event_save"
                            contentDescription =
                                if (createEventApiIsSuccess) "txt_create_event_saved" else "txt_create_event_save"
                        })
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (eventDetailsApiRespone is NetworkResponse.Loading) {
            Loader()
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(
                    color = Color(0xFFF6F7F8),
                    shape = RoundedCornerShape(size = 5.dp)
                )
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
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
                    verticalAlignment = Alignment.CenterVertically,
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
                            testTag = "btn_start_date_event_screen"
                            contentDescription = "btn_start_date_event_screen"
                            testTagsAsResourceId = true
                        }
                        .clickable {
                            if (isEventScreenEditable) {
                                startDatePicker.show()
                            }
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
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
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
                            testTag = "btn_start_time_event_screen"
                            contentDescription = "btn_start_time_event_screen"
                            testTagsAsResourceId = true
                        }
                        .clickable {
                            if (isEventScreenEditable) {
                                startTimePicker.show()
                            }
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
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
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
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
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
                            testTag = "btn_end_date_event_screen"
                            contentDescription = "btn_end_date_event_screen"
                            testTagsAsResourceId = true
                        }
                        .clickable {
                            if (isEventScreenEditable) {
                                endDatePicker.show()
                            }
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
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
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
                            testTag = "btn_end_time_event_screen"
                            contentDescription = "btn_end_time_event_screen"
                            testTagsAsResourceId = true
                        }
                        .clickable {
                            if (isEventScreenEditable) {
                                endTimePicker.show()
                            }
                        }
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
        }

        Log.i("EventScreen", "Event Description: $eventDesc")

        EditableTextField(
            note = eventDesc,
            onValueChange = {
                eventDesc = it
            },
            placeholderText = "Add Event",
            readOnly = createEventApiIsSuccess || updateEventApiIsSuccess || !isEventScreenEditable,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "et_create_event"
                    testTag = "et_create_event"
                    testTagsAsResourceId = true
                })
    }

}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    EventScreen(
        accountId = "Account Id",
    )
}

