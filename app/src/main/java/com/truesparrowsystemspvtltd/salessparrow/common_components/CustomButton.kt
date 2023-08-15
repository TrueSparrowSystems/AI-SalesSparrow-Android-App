package com.truesparrowsystemspvtltd.salessparrow.common_components

import android.os.Build
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrowsystemspvtltd.salessparrow.R


@Composable
fun CustomButton(
    buttonText: String,
    buttonTextStyle: TextStyle? = TextStyle.Default,
    onClick: () -> Unit,
    isLoadingProgressBar: Boolean? = false,
    imageId: Int? = null,
    imageContentDescription: String? = null,
    imageModifier: Modifier? = Modifier,
    modifier: Modifier = Modifier,
    buttonShape: Shape?,
    buttonTextModifier: Modifier? = Modifier,
    isButtonEnabled: Boolean = true,
    loadingButtonText: String? = null,
    loadingButtonTextStyle: TextStyle? = null,
    loadingButtonTextModifier: Modifier? = null,
) {
    val imageLoader = ImageLoader.Builder(LocalContext.current).components {
        if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()

    if (buttonShape != null) {
        Button(
            onClick = onClick,
            modifier = modifier,
            shape = buttonShape,
            enabled = isButtonEnabled
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (isLoadingProgressBar == true) {
                    Image(
                        painter = rememberAsyncImagePainter(R.drawable.loader, imageLoader),
                        contentDescription = "loader",
                        modifier = Modifier.size(12.dp)
                    )
                    if (loadingButtonText != null) {
                        Text(
                            text = loadingButtonText,
                            style = loadingButtonTextStyle!!,
                            modifier = loadingButtonTextModifier!!
                        )
                    }
                } else {

                    if (imageId != null) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = imageContentDescription,
                            modifier = imageModifier!!
                        )
                    }
                    Text(
                        text = buttonText,
                        style = buttonTextStyle!!,
                        modifier = buttonTextModifier!!
                    )
                }
            }
        }
    }
}