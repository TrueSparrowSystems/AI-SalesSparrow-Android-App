package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.AccountCard
import com.truesparrow.sales.common_components.CustomTextWithImage
import com.truesparrow.sales.common_components.NotesCard
import com.truesparrow.sales.models.Note
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.AccountDetailsViewModal


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AccountDetails(
    accountId: String,
    accountName: String
) {

    val accountDetailsViewModal: AccountDetailsViewModal = hiltViewModel()
    var isAccountNoteDetailsLoading by remember { mutableStateOf(false) };
    var notes by remember { mutableStateOf<List<Note>?>(null) }

    val accountNotesResponse by accountDetailsViewModal.accountDetailsLiveData.observeAsState()

    LaunchedEffect(key1 = accountId) {
        accountDetailsViewModal.getAccountNotes(accountId = accountId)
    }


    accountNotesResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                isAccountNoteDetailsLoading = false
                notes = it.data?.note_ids?.map { noteId ->
                    val noteDetails = it.data?.note_map_by_id?.get(noteId)
                    Note(
                        creator = noteDetails?.creator ?: "",
                        id = noteDetails?.id ?: "",
                        last_modified_time = noteDetails?.last_modified_time ?: "",
                        text_preview = noteDetails?.text_preview ?: ""
                    )
                }
                Log.i("AccountDetails", "Success: ${it.data}")
            }

            is NetworkResponse.Error -> {
                isAccountNoteDetailsLoading = false
                Log.i("AccountDetails", "Failure: ${it.message}")
            }

            is NetworkResponse.Loading -> {
                isAccountNoteDetailsLoading = true
                Log.i("AccountDetails", "Loading")
            }

        }

    }


    Column(
        modifier = Modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {

        AccountDetailsHeader()
        ContactDetailsHeader()
        AccountCard(accountName)
        NotesDetailsHeader(accountId, accountName = accountName)

        if (isAccountNoteDetailsLoading) {
            Loader()
        } else if (notes?.isEmpty() == true || notes == null) {
            EmptyScreen()
        } else {
            notes?.forEach { note ->
                NotesCard(
                    firsName = note.creator.split(" ")[0],
                    lastName = note.creator.split(" ")[1],
                    username = note.creator,
                    notes = note.text_preview,
                    date = note.last_modified_time,
                    onClick = {
                        Log.i("AccountDetails", "NoteId: ${note.id}")
                        NavigationService.navigateTo("note_details_screen/${accountId}/${accountName}/${note.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
        )
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
                size.width - width.toPx() / 2,
                size.height - width.toPx() / 2,
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
fun NotesDetailsHeader(
    accountId: String,
    accountName: String,
    isAccountSelectionEnabled: Boolean? = false
) {
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
                .clickable( interactionSource =  MutableInteractionSource(),
                    indication = null) {
                    NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/${isAccountSelectionEnabled}")
                }
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