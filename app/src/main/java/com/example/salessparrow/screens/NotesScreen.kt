package com.example.salessparrow.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.R
import com.example.salessparrow.common_components.AccountListBottomSheet
import com.example.salessparrow.common_components.CustomToast
import com.example.salessparrow.common_components.EditableTextField
import com.example.salessparrow.common_components.ToastState
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.ui.theme.customFontFamily
import com.example.salessparrow.viewmodals.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(
    accountName: String? = null,
    accountId: String? = null,
    isAccountSelectionEnabled: Boolean = false
) {

    var note by remember { mutableStateOf("") }
    val snackbarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Log.i("NotesScreen", "statusBarHeight: $statusBarHeight")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight)
    ) {
        SnackbarHost(modifier = Modifier.align(Alignment.TopStart), hostState = snackbarState) {
            CustomToast(
                toastState = ToastState.SUCCESS,
                message = "Note is saved to your Salesforce Account"
            )
        }


    }



    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {
        Header(note = note, accountName = accountName)
        NotesHeader(
            accountName = accountName,
            accountId = accountId,
            isAccountSelectionEnabled = isAccountSelectionEnabled
        )

        EditableTextField(
            note = note,
            onValueChange = {
                note = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "et_create_note"
                    testTagsAsResourceId = true
                }
        )

        Button(onClick = {
            coroutineScope.launch {
                snackbarState.showSnackbar("")
            }
        }) {
            Text(text = "Show Success Snackbar")
        }


    }
}


@Composable
fun NotesHeader(accountName: String?, accountId: String?, isAccountSelectionEnabled: Boolean) {
    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    if (bottomSheetVisible) {
        AccountListBottomSheet(toggleBottomSheet, false, isAccountSelectionEnabled)
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Image(
                painter = painterResource(id = R.drawable.buildings),
                contentDescription = "Buildings",
                modifier = Modifier.size(size = 14.dp)
            )
            Text(
                text = "Account", color = Color(0xff212653), style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = customFontFamily,
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.small)
                .padding(all = 8.dp)
                .background(color = Color(0xFFF6F6F8), shape = RoundedCornerShape(size = 4.dp))

        ) {
            Button(
                onClick = {
                    if (isAccountSelectionEnabled) {
                        toggleBottomSheet()
                    }
                },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = remember { MutableInteractionSource() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = accountName ?: "Select Account",
                        color = Color(0xffdd1a77),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Vector 56",
                    tint = Color(0xff545a71)
                )
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Header(note: String, accountName: String?) {
    val notesViewModel: NotesViewModel = hiltViewModel()
    var saveNoteSuccess by remember { mutableStateOf(false) }
    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }


    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = if (saveNoteApiIsSuccess) {
                "Done"
            } else {
                "Cancel"
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF5D678D),
                letterSpacing = 0.56.sp,
            ),
            modifier = Modifier.clickable(onClick = { NavigationService.navigateBack() }),
        )

        Button(
            onClick = {
                saveNoteApiInProgress = true
                saveNoteSuccess = true
            },
            enabled = !(saveNoteApiInProgress || note.isEmpty() || accountName!!.isEmpty() ),
            contentPadding = PaddingValues(all = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color.White
            ),
            modifier = Modifier
                .background(color = Color(0xFF212653))
                .width(92.dp)
                .height(46.dp)
                .clip(shape = RoundedCornerShape(size = 5.dp))


        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = if (saveNoteApiInProgress) {
                        painterResource(id = R.drawable.save_note_loader)
                    } else if (saveNoteApiIsSuccess) {
                        painterResource(id = R.drawable.check)
                    } else {
                        painterResource(id = R.drawable.cloud)
                    },
                    contentDescription = "cloud",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .width(width = 17.dp)
                        .height(height = 12.dp)
                )
                Text(
                    text = if (saveNoteApiInProgress) {
                        "Saving..."
                    } else if (saveNoteApiIsSuccess) {
                        "Saved"
                    } else {
                        "Save"
                    }, color = Color.White, style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        letterSpacing = 0.48.sp,
                    )
                )
            }
        }
    }
    LaunchedEffect(saveNoteSuccess) {
        if (saveNoteSuccess) {
            val success = notesViewModel.saveNote(note)
            saveNoteApiInProgress = false
            if (success) {
                Log.i("MyApp", "Saved success")
                saveNoteApiIsSuccess = true
            } else {
                Log.i("MyApp", "Save failed")
            }
            saveNoteSuccess = false
        }
    }
}


