package com.truesparrow.sales.common_components

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.truesparrow.sales.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
    type: ToastType,
    testTag: String? = null
) {
    var show by remember { mutableStateOf(true) }
    if (show) {
        val coroutineScope = rememberCoroutineScope()

        val opacity by animateFloatAsState(
            targetValue = if (show) 1f else 0f,
            animationSpec = tween(durationMillis = 300)
        )

        val backgroundColor = when (type) {
            ToastType.Info -> Color(0xFF2196F3)
            ToastType.Warning -> Color(0xFFFFC107)
            ToastType.Error -> Color(0xFFF44336)
            ToastType.Success -> Color(0xFF4CAF50)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalAlignment = Alignment.Bottom,

            modifier = Modifier
                .fillMaxWidth()
                .height(93.dp)
                .alpha(opacity)
                .background(backgroundColor)
                .padding(
                    start = 24.dp,
                    top = 10.dp,
                    end = 24.dp,
                    bottom = 10.dp
                )
                .zIndex(Float.MAX_VALUE)
                .semantics {
                    testTagsAsResourceId = true
                    testTag
                }

        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
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

        LaunchedEffect(key1 = show) {
            if (show) {
                coroutineScope.launch {
                    delay(if (duration == Toast.LENGTH_SHORT) 2000 else 3500)
                    show = false
                }
            }
        }
    }
}

enum class ToastType {
    Info, Warning, Error, Success
}
