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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truesparrow.sales.R
import com.truesparrow.sales.util.formatTime
import com.truesparrow.sales.util.parseTime

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesCard(
    firsName: String,
    lastName: String,
    username: String,
    date: String,
    notes: String,
    onClick: () -> Unit,
    onDeleteMenuClick: () -> Unit = {}
) {
    var formattedTime: String = "";
    try {
        val parsedTime = parseTime(date)
        formattedTime = parsedTime?.let { formatTime(it) }.toString()
    } catch (e: Exception) {
        Log.i("NotesCard", "NotesCard: $e")
    }
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
                testTag = "NotesCardTestId"
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
                    userAvatarTestId = "user_avatar_note_details"
                )
                Text(
                    text = username,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF545A71),
                        letterSpacing = 0.56.sp,
                    )
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
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.setting_three_dots_outline),
                        contentDescription = "menu",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .pointerInput(true) {
                                detectTapGestures(onPress = {
                                    expanded = true
                                })
                            })
                    CustomDropDownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        onDeleteMenuClick = { onDeleteMenuClick() }
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
            )
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun noteCardPreview() {
    NotesCard(
        firsName = "John",
        lastName = "Doe",
        username = "johndoe",
        date = "2021-08-10T12:00:00.000Z",
        notes = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vi",
        onClick = {}
    )
}
