package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.AccountListBottomSheet
import com.truesparrow.sales.common_components.CustomTextWithImage
import com.truesparrow.sales.common_components.CustomToast
import com.truesparrow.sales.common_components.EditableTextField
import com.truesparrow.sales.common_components.SearchNameBottomSheet
import com.truesparrow.sales.common_components.ToastState
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.NoRippleInteractionSource
import com.truesparrow.sales.viewmodals.NotesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(
    accountName: String? = null,
    accountId: String? = null,
    isAccountSelectionEnabled: Boolean = false
) {

    var note by remember { mutableStateOf("") }
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

    val notesViewModel: NotesViewModel = hiltViewModel()
    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }
    var snackbarShown by remember { mutableStateOf(false) }
    var recommendedPopup by remember { mutableStateOf(false) }


    notesViewModel.notesLiveData.observe(LocalLifecycleOwner.current) { response ->
        when (response) {
            is NetworkResponse.Success -> {
                saveNoteApiInProgress = false;
                saveNoteApiIsSuccess = true
                if (!snackbarShown) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "",
                            duration = SnackbarDuration.Short
                        )
                    }
                    snackbarShown = true
                }

            }

            is NetworkResponse.Error -> {
                saveNoteApiInProgress = false;
                if (!snackbarShown) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            "",
                            duration = SnackbarDuration.Short
                        )
                    }
                    snackbarShown = true
                }

            }

            is NetworkResponse.Loading -> {
                saveNoteApiInProgress = true;
                Log.d("NotesScreen", "Loading")
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight)
            .zIndex(1f)
    ) {
        SnackbarHost(modifier = Modifier.align(Alignment.TopStart), hostState = snackbarHostState) {
            CustomToast(
                toastState = if (saveNoteApiIsSuccess) ToastState.SUCCESS else ToastState.ERROR,
                message = if (saveNoteApiIsSuccess) "Note is saved to your Salesforce Account" else "Failed to save the note to your Salesforce Account"
            )
        }
    }

    Column(modifier = Modifier.padding(vertical = 30.dp, horizontal = 16.dp)) {
        Header(
            note = note,
            accountName = accountName,
            accountId = accountId!!,
            saveNoteApiInProgress = saveNoteApiInProgress,
            saveNoteApiIsSuccess = saveNoteApiIsSuccess,
            snackbarHostState
        )
        NotesHeader(
            accountName = accountName,
            isAccountSelectionEnabled = isAccountSelectionEnabled
        )

        EditableTextField(
            note = note,
            onValueChange = {
                note = it
            },
            readOnly = saveNoteApiIsSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "et_create_note"
                    testTag = "et_create_note"
                    testTagsAsResourceId = true
                }
        )

//        SearchName()
        if (true) {
            RecommendedSectionHeader(
                accountId,
                accountName = accountName!!,
                isAccountSelectionEnabled,
                onPlusClicked = {
                    recommendedPopup = true
                }
            )
        }

        if (recommendedPopup) {
            Popup(
                alignment = Alignment.BottomEnd,
                onDismissRequest = { recommendedPopup = false },
                offset = IntOffset(-50, 350),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFDBDEEB),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .width(215.dp)
                        .height(126.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            recommendedPopup = false
                            //TODO: Navigate to Add Tasks Screen
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tasks),
                            contentDescription = "add_tasks",
                            contentScale = ContentScale.None
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Add Tasks",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF545A71),
                            )
                        )
                    }
                }

            }
        }

    }
}

@Composable
fun SearchName() {
    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    if (bottomSheetVisible) {
        SearchNameBottomSheet(toggleBottomSheet)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
    ) {
       Button(onClick =  toggleBottomSheet) {
           Text(
               text = "Search Name",
               style = TextStyle(
                   fontSize = 14.sp,
                   fontWeight = FontWeight.Bold,
                   fontFamily = customFontFamily,
               )
           )
       }
fun RecommendedSectionHeader(
    accountId: String,
    accountName: String,
    isAccountSelectionEnabled: Boolean? = false,
    onPlusClicked: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.sparkle,
            imageContentDescription = "buildings",
            text = "We have some recommendations",
            imageModifier = Modifier
                .width(24.dp)
                .height(24.dp),
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
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    onPlusClicked()
//                    NavigationService.navigateTo("notes_screen/${accountId}/${accountName}/${isAccountSelectionEnabled}")
                }
        )
    }

}


@Composable
fun NotesHeader(accountName: String?, isAccountSelectionEnabled: Boolean) {
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
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier.semantics {
                    contentDescription = "btn_select_account"
                }
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
                        ),
                        modifier = Modifier
                            .semantics {
                                contentDescription =
                                    if (accountName.isNullOrBlank()) "txt_create_note_selected_account" else "txt_create_note_select_account"
                            }
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
fun Header(
    note: String,
    accountName: String?,
    accountId: String,
    saveNoteApiInProgress: Boolean,
    saveNoteApiIsSuccess: Boolean,
    snackbarState: SnackbarHostState
) {
    val notesViewModel: NotesViewModel = hiltViewModel()


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
            modifier = Modifier
                .clickable(interactionSource = MutableInteractionSource(),
                    indication = null, onClick = { NavigationService.navigateBack() })
                .semantics {
                    contentDescription =
                        if (saveNoteApiIsSuccess) "btn_done_note_screen" else "btn_cancel_create_note"
                },
        )

        val buttonColor = if (note.isNotEmpty() && accountId.isNotEmpty()) {
            Color(0xFF212653)
        } else {
            Color(0xFF212653).copy(alpha = 0.7f)
        }
        Button(
            onClick = {
                notesViewModel.saveNote(
                    accountId = accountId!!,
                    text = note,
                )
            },

            enabled = note.isNotEmpty() && accountId.isNotEmpty() && !(saveNoteApiInProgress || saveNoteApiIsSuccess),
            contentPadding = PaddingValues(all = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, contentColor = Color.White
            ),
            modifier = Modifier
                .background(color = buttonColor, shape = RoundedCornerShape(size = 5.dp))
                .width(92.dp)
                .height(46.dp)
                .clip(shape = RoundedCornerShape(size = 5.dp))
                .semantics {
                    contentDescription = "btn_save_note"
                }


        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically

            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current).components {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }.build()

                Image(
                    painter = if (saveNoteApiInProgress) {
                        rememberAsyncImagePainter(R.drawable.loader, imageLoader)
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
                    ),
                    modifier = Modifier.semantics {
                        contentDescription =
                            if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                    }
                )
            }
        }
    }

}


