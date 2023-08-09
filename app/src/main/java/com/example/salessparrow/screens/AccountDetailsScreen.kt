package com.example.salessparrow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R
import com.example.salessparrow.common_components.AccountCard
import com.example.salessparrow.common_components.CustomTextWithImage
import com.example.salessparrow.services.NavigationService


@Composable
fun AccountDetails() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        AccountDetailsHeader()
        ContactDetailsHeader()
        AccountCard()
        NotesDetailsHeader()
        EmptyScreen()

    }
}





fun Modifier.dashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 5f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx()/2,
                size.height - width.toPx()/2,
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }

@Composable
fun EmptyScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .dashedBorder(1.dp, 5.dp, Color(0xFF545A71))
            .padding(0.75.dp)
            .fillMaxWidth()
            .height(40.dp)
            .padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp)
    ) {

        Text(
            text = "Add notes and sync with your salesforce account",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(400),
                fontStyle = FontStyle.Italic,
                color = Color(0xFF545A71),
                textAlign = TextAlign.Center,
                letterSpacing = 0.48.sp,
            )
        )
    }

}




@Composable
fun NotesDetailsHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.notes,
            imageContentDescription = "buildings",
            imageModifier = Modifier
                .width(17.dp)
                .height(17.dp),
            text = "Notes",
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF212653),
            )
        )
        Image(
            painter = painterResource(id = R.drawable.add_icon),
            contentDescription = "add_notes",
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )
    }

}


@Composable
fun ContactDetailsHeader() {
    CustomTextWithImage(
        imageId = R.drawable.buildings,
        imageContentDescription = "buildings",
        imageModifier = Modifier
            .width(17.dp)
            .height(17.dp),
        text = "Account Details",
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight(600),
            color = Color(0xFF212653),
        )
    )
}

@Composable
fun AccountDetailsHeader() {
    CustomTextWithImage(
        imageId = R.drawable.arrow_left,
        imageContentDescription = "arrow-left",
        imageModifier = Modifier
            .width(24.dp)
            .height(24.dp),
        text = "Details",
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight(600),
            color = Color(0xFF212653),
        ),
        onClick = {
          NavigationService.navigateBack();
        }
    )
}