package com.example.salessparrow.common_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R
import com.example.salessparrow.util.formatTime
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesCard(firsName: String, lastName: String, username: String, date: String, notes: String) {
    val parsedTime = Instant.parse(date)
    val formattedTime = formatTime(parsedTime)

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .fillMaxWidth()
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
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
                    )
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
