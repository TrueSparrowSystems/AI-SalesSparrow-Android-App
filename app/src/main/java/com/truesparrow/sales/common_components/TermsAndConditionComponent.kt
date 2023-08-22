package com.truesparrow.sales.common_components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.truesparrow.sales.R

@OptIn(ExperimentalTextApi::class)
@Composable
fun TermsAndConditionComponent(
    modifier: Modifier? = null
) {
    val context = LocalContext.current;

    val annotedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF444A62),
            )
        ) {
            append("By continuing, you're agreeing to the truesparrow’s")
        }
        pushStringAnnotation(
            tag = "terms",
            annotation = "https://www.truesparrow.com"
        )
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFFDD1A77),
                textDecoration = TextDecoration.Underline,
            )
        ) {
            append(" Terms and Conditions")

        }
        pop()
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF444A62),
            )
        ) {
            append(" and ")
        }
        pushStringAnnotation(
            tag = "privacy",
            annotation = "https://www.truesparrow.com"
        )
        withStyle(
            style = SpanStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFFDD1A77),
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()
    }

    ClickableText(
        text = annotedString,
        modifier = modifier ?: Modifier,
        onClick = { offset ->
            annotedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                .firstOrNull()?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://truesparrow.com/terms"));
                    context.startActivity(intent);
                    Log.d("terms URL", it.item)
                }

            annotedString.getStringAnnotations(tag = "privacy", start = offset, end = offset)
                .firstOrNull()?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://truesparrow.com/privacy"));
                    context.startActivity(intent);
                    Log.d("policy URL", it.item)
                }
        },
        style = TextStyle(
            textAlign = TextAlign.Center
        )

    )

}