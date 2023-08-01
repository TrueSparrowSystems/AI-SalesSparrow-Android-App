package com.example.salessparrow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R

private val customFontFamily = FontFamily(
    Font(R.font.nunito_medium),
    Font(R.font.nunito_bold),
    Font(R.font.nunito_light),
    Font(R.font.nunito_regular),
    Font(R.font.nunito_semibold),
    Font(R.font.nunito_extrabold),
    Font(R.font.nunito_extralight),
    Font(R.font.nunito_black),
    Font(R.font.nunito_semibolditalic),
    Font(R.font.nunito_extrabolditalic),
    Font(R.font.nunito_extralightitalic),
    Font(R.font.nunito_blackitalic),
    Font(R.font.nunito_bolditalic),
    Font(R.font.nunito_lightitalic),
    Font(R.font.nunito_italic),
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )


)