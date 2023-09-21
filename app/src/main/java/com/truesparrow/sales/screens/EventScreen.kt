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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.services.NavigationService

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EventScreen(
    accountName : String ?= "",
    accountId : String ?= "",
) {

    val createEventApiIsSuccess by remember { mutableStateOf(false)}
    val createEventApiInProgress by remember { mutableStateOf(false)}
    var taskDescription by remember { mutableStateOf("") }

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
                    onClick = { NavigationService.navigateBack() })
                .semantics {
                    testTagsAsResourceId = true
                    testTag =
                        if (createEventApiIsSuccess) "btn_done_create_event" else "btn_cancel_create_event"
                    contentDescription =
                        if (createEventApiIsSuccess) "btn_done_create_event" else "btn_cancel_create_event"
                },
        )

        val buttonColor = if (taskDescription.isNotEmpty() && accountId?.isNotEmpty() == true) {
            Color(0xFF212653)
        } else {
            Color(0xFF212653).copy(alpha = 0.7f)
        }
        if (accountId != null) {
            Button(onClick = {
//                notesViewModel.saveNote(
//                    accountId = accountId!!,
//                    text = taskDescription,
//                )
            },

                enabled = taskDescription.isNotEmpty() && accountId.isNotEmpty() && !(createEventApiInProgress || createEventApiIsSuccess),
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

                    Image(painter = if (createEventApiInProgress) {
                        rememberAsyncImagePainter(R.drawable.loader, imageLoader)
                    } else if (createEventApiIsSuccess) {
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
                    Text(text = if (createEventApiInProgress) {
                        "Saving..."
                    } else if (createEventApiIsSuccess) {
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
    
}


