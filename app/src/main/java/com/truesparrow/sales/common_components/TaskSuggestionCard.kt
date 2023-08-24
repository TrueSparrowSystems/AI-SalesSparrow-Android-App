package com.truesparrow.sales.common_components

import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TaskSuggestionCard(
    taskTitle: String, crmUserName: String?
) {
    var EditTaskPopup by remember { mutableStateOf(false) }
    var showLoader by remember { mutableStateOf(false) }


    if (EditTaskPopup) {
        Popup(
            alignment = Alignment.BottomEnd,
            onDismissRequest = { EditTaskPopup = false },
            offset = IntOffset(-50, -320),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0xFFDBDEEB),
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .width(110.dp)
                    .height(100.dp)
                    .background(
                        color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp)
                    )
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        EditTaskPopup = false
                        // Todo: Add Edit suggested task functionality

                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.edit),
                        contentDescription = "edit_tasks",
                        contentScale = ContentScale.None
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Edit", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF545A71),
                        )
                    )
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        EditTaskPopup = false
//                        Todo: Add Delete suggested task functionality

                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "delete_tasks",
                        contentScale = ContentScale.None
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Delete", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF545A71),
                        )
                    )
                }
            }

        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
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
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()

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

                    Image(painter = painterResource(id = R.drawable.setting_three_dots_outline),
                        contentDescription = "add_notes",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable {
                                EditTaskPopup = true
                            })
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp))
                    .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
                    .clickable {
                        NavigationService.navigateTo(Screens.AddRecommendedTaskScreen.route)

                    }

            ) {
                Text(
                    text = taskTitle, style = TextStyle(
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
                    .background(
                        color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp)
                    )
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
                    .background(
                        color = Color(0xFFF6F7F8), shape = RoundedCornerShape(size = 5.dp)
                    )
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
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    showLoader = true
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)
                        showLoader = false
                    }
                },
                    contentPadding = PaddingValues(all = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent, contentColor = Color.White
                    ),
                    modifier = Modifier
                        .background(
                            color = Color(0xFF212653), shape = RoundedCornerShape(size = 5.dp)
                        )
                        .height(32.dp)
                        .clip(shape = RoundedCornerShape(size = 5.dp))
                        .semantics {
                            var contentDescription = ""
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

                        if (showLoader) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.loader, imageLoader),
                                contentDescription = "Loader",
                                colorFilter = ColorFilter.tint(Color.White),
                                modifier = Modifier
                                    .width(width = 12.dp)
                                    .height(height = 12.dp)
                            )
                        }

                        Text(text = if (showLoader) {
                            "Adding Task..."
                        } else {
                            "Add Task"
                        }, color = Color.White, style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            letterSpacing = 0.48.sp,
                        ), modifier = Modifier.semantics {
                            contentDescription = ""
                        })
                    }
                }
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(32.dp)
                        .border(1.dp, Color(0xFF5D678D), shape = RoundedCornerShape(4.dp))
                ) {
                    Text(text = "Cancel",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF5D678D),
                            letterSpacing = 0.48.sp,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { /* Handle button click */ })
                }
            }
        }
    }
}