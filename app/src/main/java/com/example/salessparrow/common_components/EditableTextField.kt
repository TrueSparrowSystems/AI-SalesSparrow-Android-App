package com.example.salessparrow.common_components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.salessparrow.R

@Composable
fun EditableTextField(note: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier){

    TextField(
        value = note,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontFamily = FontFamily(Font(R.font.nunito_regular)),
            fontWeight = FontWeight(500),
            color = Color(0xFF545A71),
        ),

        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xCC545A71),
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = Modifier,
        placeholder = {
            Text(
                text = "Add a note",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(400),
                    fontStyle = FontStyle.Italic,
                    color = Color(0xCC545A71),
                    letterSpacing = 0.72.sp
                )
            )
        }
    )
}