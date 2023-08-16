package com.truesparrowsystemspvtltd.salessparrow.common_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.truesparrowsystemspvtltd.salessparrow.R

@OptIn(ExperimentalTextApi::class)
@Composable
fun TermsAndConditionComponent(
    modifier: Modifier? = null
) {

    Text(
        buildAnnotatedString {
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
            withStyle(
                style = SpanStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFDD1A77),
                    textDecoration = TextDecoration.Underline,
                )
            ) {
                withAnnotation(
                    tag = "URL",
                    annotation = "https://www.truesparrow.com"
                ){
                    append(" Terms and Conditions")
                }
            }
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
            withStyle(
                style = SpanStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFDD1A77),
                    textDecoration = TextDecoration.Underline
                )
            ) {
                withAnnotation(
                    tag = "URL",
                    annotation = "https://www.truesparrow.com"
                ){
                    append("Privacy Policy")
                }

            }
        },
        textAlign = TextAlign.Center,
        modifier = modifier ?: Modifier
    )

}