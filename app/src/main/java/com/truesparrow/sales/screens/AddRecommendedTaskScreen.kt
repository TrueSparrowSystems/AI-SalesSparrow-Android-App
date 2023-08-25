package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
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
import com.truesparrow.sales.common_components.AccountListBottomSheet
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.common_components.SearchNameBottomSheet
import com.truesparrow.sales.common_components.UserAvatar
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NoRippleInteractionSource

@Composable
fun AddRecommendedTaskScreen(
    crmUserId: String? = null,
    crmUserName: String? = null,
) {
    Log.i("crmUserId", "$crmUserName")
    Log.i("crmUserName", "$crmUserId")

    var note by remember { mutableStateOf("Presentation on how we would get prepare and plan a migration from PHP to Ruby. Get number of teams members and detailed estimates for Smagic.  Rachin to lead this. |") }
    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {
        AddTaskHeader(
        )
        Spacer(modifier = Modifier.height(20.dp))
        AddTaskContent(
            crmUserName = crmUserName, crmUserId = crmUserId
        )
        Spacer(modifier = Modifier.height(20.dp))
        EditableTextField(note = note, onValueChange = {
            note = it
        }, modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription = "et_create_note"
                testTag = "et_create_note"
            })

    }
}

@Composable
fun AddTaskContent(
    crmUserName: String? = null,
    crmUserId: String? = null,
) {

    var searchNameBottomSheetVisible by remember { mutableStateOf(false) }

    val toggleSearchNameBottomSheet: () -> Unit = {
        searchNameBottomSheetVisible = !searchNameBottomSheetVisible
    }


    if (searchNameBottomSheetVisible) {
        SearchNameBottomSheet(toggleSearchNameBottomSheet)
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Assign to",
                color = Color(0xff545A71),
                modifier = Modifier.width(64.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,

                    )
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .padding(all = 4.dp)
                .border(
                    width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
                )

        ) {
            Button(onClick = {
                toggleSearchNameBottomSheet()
            },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier
                    .width(160.dp)
                    .semantics {
                        contentDescription = ""
                    }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
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
                        userAvatarTestId = ""
                    )

                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = crmUserName ?: "Select",
                        color = Color(0xffdd1a77),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Vector 56",
                        tint = Color(0xff545a71)
                    )
                }

            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Due",
                color = Color(0xff545A71),
                modifier = Modifier.width(64.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,

                    )
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .padding(all = 4.dp)
                .border(
                    width = 1.dp, color = Color(0xFFE9E9E9), shape = RoundedCornerShape(size = 4.dp)
                )

        ) {
            Button(onClick = {},
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier
                    .width(160.dp)
                    .semantics {
                        contentDescription = "btn_select_account"
                    }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Select",
                        color = Color(0xff444A62),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                    )
                }

            }
        }
    }

}


@Composable
fun AddTaskHeader(
) {

    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Cancel",
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
                    onClick = { NavigationService.navigateBack() })
                .semantics {
                    contentDescription =
                        if (saveNoteApiIsSuccess) "btn_done_note_screen" else "btn_cancel_create_note"
                },
        )

        val buttonColor = if (saveNoteApiIsSuccess) {
            Color(0xFF212653).copy(alpha = 0.7f)
        } else {
            Color(0xFF212653)
        }
        Button(onClick = {},
            enabled = true,
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
                    contentDescription = "btn_save_note"
                }

        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically

            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build()


                if (saveNoteApiInProgress || saveNoteApiIsSuccess) {
                    Image(
                        painter = if (saveNoteApiInProgress) {
                            rememberAsyncImagePainter(R.drawable.loader, imageLoader)
                        } else {
                            painterResource(id = R.drawable.check)
                        },
                        contentDescription = "Loader",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .width(width = 17.dp)
                            .height(height = 12.dp)
                    )

                }
                Text(text = if (saveNoteApiInProgress) {
                    "Adding Task..."
                } else if (saveNoteApiIsSuccess) {
                    "Saved"
                } else {
                    "Add Task"
                }, color = Color.White, style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    letterSpacing = 0.48.sp,
                ), modifier = Modifier.semantics {
                    contentDescription =
                        if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecommendedTaskScreenPreview() {
    AddRecommendedTaskScreen()
}