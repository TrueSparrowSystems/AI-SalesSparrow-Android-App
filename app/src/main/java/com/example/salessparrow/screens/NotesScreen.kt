package com.example.salessparrow.screens

import android.os.Build
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.salessparrow.R
import com.example.salessparrow.common_components.AccountListBottomSheet
import com.example.salessparrow.common_components.CustomToast
import com.example.salessparrow.common_components.EditableTextField
import com.example.salessparrow.common_components.ToastState
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.ui.theme.customFontFamily
import com.example.salessparrow.util.NetworkResponse
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
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val snackbarState = remember { SnackbarHostState() }

    val notesViewModel: NotesViewModel = hiltViewModel()
    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }

    notesViewModel.notesLiveData.observe(LocalLifecycleOwner.current) { response ->
        when (response) {
            is NetworkResponse.Success -> {
                saveNoteApiInProgress = false;
                saveNoteApiIsSuccess = true
                Log.d("NotesScreen", "Success")

            }

            is NetworkResponse.Error -> {
                saveNoteApiInProgress = false;
                Log.d("NotesScreen", "Error : ${response.message}")
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
    ) {
        SnackbarHost(modifier = Modifier.align(Alignment.TopStart), hostState = snackbarState) {
            CustomToast(
                toastState = ToastState.SUCCESS,
                message = "Note is saved to your Salesforce Account"
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
            snackbarState
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
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "et_create_note"
                    testTagsAsResourceId = true
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
            modifier = Modifier.clickable(onClick = { NavigationService.navigateBack() }),
        )

        Button(
            onClick = {
                notesViewModel.saveNote(
                    accountId = accountId!!,
                    text = note,
                )
            },

            enabled = !(saveNoteApiInProgress || note.isEmpty() || accountName!!.isEmpty()),
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
                    )
                )
            }
        }
    }

}


