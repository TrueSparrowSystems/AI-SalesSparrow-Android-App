package com.truesparrow.sales.screens

import android.text.Html
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.NotesViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteDetailScreen(
    accountId: String,
    accountName: String,
    noteId: String,
) {

    val notesViewModel: NotesViewModel = hiltViewModel()

    var note by remember { mutableStateOf("") }

    val noteDetailsResponse by notesViewModel.noteDetailsLiveData.observeAsState()

    LaunchedEffect(key1 = accountId + noteId) {
        notesViewModel.getNoteDetails(accountId = accountId, noteId = noteId)
    }


    noteDetailsResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                note = it.data?.note_detail?.text ?: ""
                Log.i("NoteDetailScreen", "Success: ${it.data?.note_detail?.text}")
            }

            is NetworkResponse.Error -> {
                Log.i("NoteDetailScreen", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                Log.i("NoteDetailScreen", "Loading")

            }
        }
    }


    Column(
        modifier = Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
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
            modifier = Modifier.clickable {
                NavigationService.navigateBack()
            }
        )


        NotesHeader(
            accountName = accountName,
            isAccountSelectionEnabled = false
        )

        if (noteDetailsResponse is NetworkResponse.Loading) {
            Loader()
        } else {
            Text(
                text = note, style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    color = Color(0xFF444A62),
                    letterSpacing = 0.72.sp,
                )
            )
        }


    }
}

@Composable
fun HtmlWebView(htmlContent: String, onValueChange: (String) -> Unit) {
    Log.i("HtmlWebView", "HtmlWebView: $htmlContent")
    val plainText = Html.fromHtml(htmlContent)
    EditableTextField(note = plainText.toString(), onValueChange = onValueChange)
}


@Preview(showBackground = true)
@Composable
fun NoteDetailsScreenPreview() {
    NoteDetailScreen(
        accountId = "123",
        accountName = "Test Account",
        noteId = "123"
    )
}


