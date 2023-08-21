package com.truesparrow.sales.common_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import androidx.compose.ui.zIndex
import com.truesparrow.sales.R


enum class ToastState(val value: String) {
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info")
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomToast(
    toastState: ToastState,
    message: String,
) {
    val backgroundColor = when (toastState) {
        ToastState.SUCCESS -> Color(0xFF63E17D)
        ToastState.ERROR ->  Color(0xFFFF3B30)
        else -> Color.Red
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.Bottom,

        modifier = Modifier
            .fillMaxWidth()
            .height(93.dp)
            .background(backgroundColor)
            .padding(start = 24.dp, top = 10.dp, end = 24.dp, bottom = 10.dp)
            .zIndex(Float.MAX_VALUE).semantics {
                testTagsAsResourceId = true
                testTag = "toastMessageRowTestId"
            }

    ) {
        when (toastState) {
            ToastState.SUCCESS -> {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.Top,
                ) {
                    Image(
//                        TODO: Change it with suitable icon
                        painter = painterResource(id = R.drawable.success_toast_check),
                        contentDescription = "Success",
                        modifier = Modifier
                            .height(24.dp)
                            .width(28.dp)
                    )
                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }

            ToastState.ERROR -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.Top,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.success_toast_check),
                        contentDescription = "Error",
                        modifier = Modifier
                            .height(24.dp)
                            .width(28.dp)
                    )
                    Text(
                        text = message,
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }

            ToastState.WARNING -> {
                // Compose elements for warning state
            }

            ToastState.INFO -> {
                // Compose elements for info state
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomToastPreview() {
    CustomToast(
        toastState = ToastState.ERROR,
        message = "Note is saved to your Salesforce Account"
    )
}