package com.truesparrow.sales.screens

import android.text.Html
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

        SettingHeader()

        NotesHeader(
            accountName = accountName,
            isAccountSelectionEnabled = false
        )

        HtmlWebView(htmlContent = note)

    }
}

@Composable
fun HtmlWebView(htmlContent: String) {
    Log.i("HtmlWebView", "HtmlWebView: $htmlContent")
    val plainText = Html.fromHtml(htmlContent)
    Text(text = plainText.toString())
}



