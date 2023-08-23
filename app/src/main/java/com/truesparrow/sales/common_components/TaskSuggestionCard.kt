package com.truesparrow.sales.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truesparrow.sales.R

@Composable
fun TaskSuggestionCard() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0xFFE9E9E9),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(0.75.dp)
            .fillMaxWidth()
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserAvatar(
                        id = "1",
                        firstName = "D",
                        lastName = "S",
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
                        text = "John Doe", style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.56.sp,
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tuesday, 5:49pm", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(300),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.setting_three_dots_outline),
                        contentDescription = "add_notes",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }
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
                    .fillMaxWidth()
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)

            ) {
                Text(
                    text = "Presentation to plan a migration from PHP to Ruby.", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        letterSpacing = 0.64.sp,
                    )
                )
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
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(8.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.width(160.dp)
                ) {

                    Text(
                        text = "Assign to", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    UserAvatar(
                        id = "1",
                        firstName = "D",
                        lastName = "S",
                        size = 17.dp,
                        textStyle = TextStyle(
                            fontSize = 5.24.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000),
                            letterSpacing = 0.21.sp,
                        ),
                        userAvatarTestId = "user_avatar_search_user"
                    )
                    Text(
                        text = "Zaire", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFFDD1A77),
                            letterSpacing = 0.48.sp,
                        )
                    )
                }
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
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(8.dp)
                    .width(160.dp)

            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Due", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.seperator),
                        contentDescription = "separator",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )

                    Text(
                        text = "04/07/2023", style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF444A62),
                            letterSpacing = 0.48.sp,
                        )
                    )
                }
            }

        }
    }
}