package com.truesparrow.sales.common_components


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.truesparrow.sales.R
import com.truesparrow.sales.util.convertDateFormat
import com.truesparrow.sales.util.convertTime
import com.truesparrow.sales.util.formatTime
import com.truesparrow.sales.util.parseTime

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCard(
    firsName: String,
    lastName: String,
    username: String,
    date: String,
    notes: String,
    dueDate: String,
    onClick: () -> Unit,
    eventId: String = "",
    onDeleteMenuClick: (eventId: String) -> Unit = {},
    onEditMenuClick: (eventId: String) -> Unit = {},
    deleteMenuTestTag: String = "",
    editMenuTestTag: String = "",
    startDateTime: String = "",
    endDateTime: String = "",
    index: Number,
    isEventAdded : Boolean = false
) {
    Log.i(
        "EventCard",
        "EventCard: $notes @date: $startDateTime  endDatetime: $endDateTime date : $date"
    )

    var formattedStartDate = ""
    try {
        formattedStartDate = convertTime(startDateTime)
    } catch (e: Exception) {
        Log.i("EventCard", "NotesCard: $e")
    }

    var formattedEndDate = "";

    try {
        formattedEndDate = convertTime(endDateTime)
        Log.i("EventCard", "NotesCard: $formattedEndDate")
    } catch (e: Exception) {
        Log.i("EventCard", "NotesCard: $e")
    }

    var formattedTime: String = "";
    try {
        val parsedTime = parseTime(date)
        formattedTime = parsedTime?.let { formatTime(it) }.toString()
    } catch (e: Exception) {
        Log.i("NotesCard", "NotesCard: $e")
    }

    var formattedDueDate = convertDateFormat(startDateTime);
    var expanded by remember {
        mutableStateOf(false)
    }



    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .semantics {
                testTagsAsResourceId = true
                testTag = "event_card_${index}"
            }
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    id = "1",
                    firstName = firsName,
                    lastName = lastName,
                    size = 18.dp,
                    textStyle = TextStyle(
                        fontSize = 5.24.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF000000),
                        letterSpacing = 0.21.sp,
                    ),
                    userAvatarTestId = "user_avatar_note_details_${index}"
                )
                Text(
                    text = username,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF545A71),
                        letterSpacing = 0.56.sp,
                    ),
                    modifier = Modifier.semantics {
                        testTag = "event_details_${username}_${index}"
                        contentDescription = "event_details_${username}_${index}"
                        testTagsAsResourceId = true
                    }
                )
            }

            Row {
                Text(
                    text = formattedTime,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(300),
                        color = Color(0xFF545A71),
                        letterSpacing = 0.48.sp,
                    ),

                    modifier = Modifier.semantics {
                        testTag = "txt_account_detail_event_last_modified_time_${index}"
                        contentDescription = "txt_account_detail_event_last_modified_time_${index}"
                        testTagsAsResourceId = true
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.setting_three_dots_outline),
                        contentDescription = "menu",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .semantics {
                                testTag = "btn_account_detail_event_more_${index}"
                                contentDescription = "btn_account_detail_event_more_${index}"
                                testTagsAsResourceId = true
                            }
                            .pointerInput(true) {
                                detectTapGestures(onPress = {
                                    expanded = true
                                })
                            })
                    CustomDropDownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        editMenuTestTag = editMenuTestTag,
                        deleteMenuTestTag = deleteMenuTestTag,
                        onEditMenuClick = {
                            Log.i("NotesCard", "NotesCard: $eventId")
                            onEditMenuClick(eventId)
                        },
                        onDeleteMenuClick = {
                            Log.i("NotesCard", "NotesCard: $eventId")
                            onDeleteMenuClick(eventId)
                        },
                    )

                }
            }

        }

        Text(
            text = notes
                .split(" ")
                .take(255)
                .joinToString(" "),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFF545A71),
                letterSpacing = 0.56.sp,
            ),
            modifier = Modifier.semantics {
                testTag = "txt_account_detail_event_text_${index}"
                contentDescription = "txt_account_detail_event_text_${index}"
                testTagsAsResourceId = true
            }
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))


        Row {
            Image(
                painter = painterResource(id = R.drawable.calendar_check),
                contentDescription = "calendar_check",
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
                    .padding(0.5.dp)
            )

            Text(
                text = "From",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.48.sp,
                )
            )

            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(
                text = formattedDueDate,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.48.sp,
                )
            )
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Divider(
                color = Color(0x1A444A62),
                modifier = Modifier
                    .height(16.dp)
                    .width(1.dp)
            )

            Spacer(modifier = Modifier.padding(start = 4.dp))
            Text(
                text = formattedStartDate.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.48.sp,
                )
            )
            Text(
                text = "-",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.48.sp,
                )
            )


            Text(
                text = formattedEndDate.toString(),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.48.sp,
                )
            )
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
                            testTag = "txt_create_note_task_added"
                            contentDescription = "txt_create_note_task_added"
                        }, style = TextStyle(
                            fontSize = 12.sp, lineHeight = 24.sp, color = Color(0xFF444A62)
                        )
                    )
                }
            }
        }


    }
}

@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventCardPreview() {
    EventCard(
        firsName = "John",
        lastName = "Doe",
        username = "John Doe",
        date = "2021-07-20T12:00:00Z",
        notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
        dueDate = "2019-10-12",
        onClick = {},
        startDateTime = "2021-07-20T12:00:00Z",
        endDateTime = "2021-07-20T12:00:00Z",
        index = 0,
         isEventAdded = true
    )
}
