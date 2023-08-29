package com.truesparrow.sales.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.*;
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.truesparrow.sales.common_components.TaskSuggestionCard
import com.truesparrow.sales.common_components.ToastType
import com.truesparrow.sales.models.TaskSuggestions
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.NoRippleInteractionSource
import com.truesparrow.sales.viewmodals.GlobalStateViewModel
import com.truesparrow.sales.viewmodals.NotesViewModel
import java.security.MessageDigest
import java.util.UUID

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NotesScreen(
    accountName: String? = null,
    accountId: String? = null,
    isAccountSelectionEnabled: Boolean = false,
    id: String,
    viewModel: GlobalStateViewModel,
) {
    var note by remember { mutableStateOf("") }

    val crmUserId = viewModel.getCrmUserIdById(id)?.value ?: ""
    val crmUserName = viewModel.getCrmUserNameById(id)?.value ?: "Select"


    val notesViewModel: NotesViewModel = hiltViewModel()
    var saveNoteApiInProgress by remember { mutableStateOf(false) }
    var saveNoteApiIsSuccess by remember { mutableStateOf(false) }
    var recommendedPopup by remember { mutableStateOf(false) }


    val getCrmActionsResponse by notesViewModel.getCrmActionsLiveData.observeAsState()
    var getCrmActionLoading by remember { mutableStateOf(false) }
    var tasks by remember { mutableStateOf(listOf<TaskSuggestions?>(null)) }
    val scrollState = rememberScrollState()

    val saveNoteRespose by notesViewModel.notesLiveData.observeAsState()

    getCrmActionsResponse?.let {
        when (it) {
            is NetworkResponse.Success -> {
                getCrmActionLoading = false
                tasks = (it.data?.add_task_suggestions?.map { task ->
                    var index = 0;
                    val id = "${task.description}${task.due_date ?: ""}${index++}"
                    TaskSuggestions(
                        description = task.description, due_date = task.due_date ?: "", id = id
                    )

                } ?: listOf<TaskSuggestions?>(null))

                Log.d("NotesScreen", "Success")

            }

            is NetworkResponse.Error -> {
                getCrmActionLoading = false

                Log.d("NotesScreen", "Error")
            }

            is NetworkResponse.Loading -> {
                getCrmActionLoading = true
                Log.d("NotesScreen", "Loading")
            }
        }
    }

    saveNoteRespose?.let { response ->
        when (response) {
            is NetworkResponse.Success -> {
                saveNoteApiInProgress = false;
                saveNoteApiIsSuccess = true
                CustomToast(
                    message = "Note is saved to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Success
                )

                LaunchedEffect(true) {
                    notesViewModel.getCrmActions(note);
                }
            }

            is NetworkResponse.Error -> {
                saveNoteApiInProgress = false;
                CustomToast(
                    message = response.message
                        ?: "Failed to save the note to your Salesforce Account",
                    duration = Toast.LENGTH_SHORT,
                    type = ToastType.Error
                )

            }

            is NetworkResponse.Loading -> {
                saveNoteApiInProgress = true;
                Log.d("NotesScreen", "Loading")
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(vertical = 30.dp, horizontal = 16.dp)
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        Header(
            note = note,
            accountName = accountName,
            accountId = accountId!!,
            saveNoteApiInProgress = saveNoteApiInProgress,
            saveNoteApiIsSuccess = saveNoteApiIsSuccess,
        )
        NotesHeader(
            accountName = accountName, isAccountSelectionEnabled = isAccountSelectionEnabled
        )

        EditableTextField(note = note,
            onValueChange = {
                note = it
            },
            placeholderText = "Add A Note",
            readOnly = saveNoteApiIsSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "et_create_note"
                    testTag = "et_create_note"
                    testTagsAsResourceId = true
                })


        Log.i("NotesScreen re-com 0", "NotesScreen: ${getCrmActionLoading} $tasks")

        if (getCrmActionLoading) {
            RecommendedSectionHeader(
                heading = "Getting recommendations",
                onPlusClicked = {
                    recommendedPopup = true
                },
                shouldShowPlusIcon = false,
                crmUserId = crmUserId!!,
                crmUserName = crmUserName!!,
                accountId = accountId!!
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .dashedBorder(1.dp, 5.dp, Color(0x80545A71))
                    .fillMaxWidth()
                    .height(122.dp)
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF212653), strokeWidth = 2.dp, modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Please wait, we're checking to recommend tasks or events for you.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF444A62),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.56.sp,
                    )
                )
            }
        } else if (tasks.isEmpty()) {
            EmptyScreen(
                emptyText = "You are all set, no recommendation for now!",
                shouldShowIcon = true,
                height = 95.dp
            )
        } else {
            Log.i("NotesScreen re com", "NotesScreen: ${tasks.size} $tasks")
            if ( tasks[0] !== null) {
                RecommendedSectionHeader(
                    heading = "We have some recommendations ",
                    onPlusClicked = {
                        recommendedPopup = true
                    },
                    shouldShowPlusIcon = true,
                    crmUserName = crmUserName!!,
                    crmUserId = crmUserId!!,
                    accountId = accountId!!
                )

                Spacer(modifier = Modifier.height(30.dp))
                tasks.forEach { task ->
                    task?.let { it ->

                        val taskDesc = viewModel.getTaskDescById(it.id)?.value ?: ""
                        val userId = viewModel.getCrmUserIdById(it.id)?.value ?: ""
                        val userName = viewModel.getCrmUserNameById(it.id)?.value ?: ""
                        val dDate = viewModel.getDueDateById(it.id)?.value ?: ""

                        viewModel.setValuesById(
                            id = it.id, taskDesc = if (taskDesc.isEmpty()) {
                                it.description
                            } else {
                                taskDesc
                            }, crmUserId = if (userId.isEmpty()) {
                                crmUserId
                            } else {
                                userId
                            }, crmUserName = if (userName.isEmpty()) {
                                crmUserName
                            } else {
                                userName
                            }, dueDate = if (dDate.isEmpty()) {
                                it.due_date ?: ""
                            } else {
                                dDate
                            }
                        )

                        Column(
                            modifier = Modifier
                                .dashedBorder(1.dp, 5.dp, Color(0x80545A71))
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            TaskSuggestionCard(id = it.id,
                                accountId = accountId!!,
                                accountName = accountName!!,
                                globalStateViewModel = viewModel,
                                shouldShowOptions = false,
                                onDeleteTaskClick = {},
                                onCancelTaskClick = {taskId ->
                                    Log.i("NotesScreen", "NotesScreen: $taskId")
                                    Log.i("NotesScreen", "NotesScreen: ${tasks.size} $tasks")
                                    val updatedTasks = tasks.filter { it?.id != taskId }
                                    tasks = updatedTasks
                                    Log.i("NotesScreen", "NotesScreen: ${tasks.size} $tasks")
                                    viewModel.DeleteTaskById(taskId)
                                },

                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecommendedSectionHeader(
    heading: String,
    crmUserName: String,
    crmUserId: String,
    accountId: String,
    onPlusClicked: () -> Unit,
    shouldShowPlusIcon: Boolean,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextWithImage(
            imageId = R.drawable.sparkle,
            imageContentDescription = "buildings",
            text = heading,
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
        if (shouldShowPlusIcon) {
            Image(painter = painterResource(id = R.drawable.add_icon),
                contentDescription = "add_notes",
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .semantics {
                        contentDescription = "add_notes"
                        testTag = "add_notes"
                        testTagsAsResourceId = true
                    }
                    .clickable(
                        interactionSource = MutableInteractionSource(), indication = null
                    ) {
                        onPlusClicked()
                        NavigationService.navigateTo("task_screen/${accountId}/1")
                    })
        }

    }

}


@OptIn(ExperimentalComposeUiApi::class)
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
            Button(onClick = {
                if (isAccountSelectionEnabled) {
                    toggleBottomSheet()
                }
            },
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                interactionSource = NoRippleInteractionSource(),
                modifier = Modifier.semantics {
                    contentDescription = "btn_select_account"
                    testTag = "btn_select_account"
                    testTagsAsResourceId = true
                }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = accountName ?: "Select Account",
                        color = Color(0xffdd1a77),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                        ),
                        modifier = Modifier.semantics {
                            contentDescription =
                                if (accountName.isNullOrBlank()) "txt_create_note_select_account" else  "txt_create_note_selected_account"
                            testTag =   if (accountName.isNullOrBlank()) "txt_create_note_select_account" else  "txt_create_note_selected_account"
                            testTagsAsResourceId = true
                        })
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
                    indication = null,
                    onClick = { NavigationService.navigateBack() })
                .semantics {
                    testTagsAsResourceId = true
                    testTag =  if (saveNoteApiIsSuccess) "btn_done_create_note" else "btn_cancel_create_note"
                    contentDescription =
                        if (saveNoteApiIsSuccess) "btn_done_create_note" else "btn_cancel_create_note"
                },
        )

        val buttonColor = if (note.isNotEmpty() && accountId.isNotEmpty()) {
            Color(0xFF212653)
        } else {
            Color(0xFF212653).copy(alpha = 0.7f)
        }
        Button(onClick = {
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
                .background(
                    color = buttonColor, shape = RoundedCornerShape(size = 5.dp)
                )
                .width(92.dp)
                .height(46.dp)
                .clip(shape = RoundedCornerShape(size = 5.dp))
                .semantics {
                    testTagsAsResourceId = true
                    testTag = "btn_save_note"
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
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "cloud"
                        }
                )
                Text(text = if (saveNoteApiInProgress) {
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
                ), modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                    testTag = if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                    contentDescription =
                        if (saveNoteApiIsSuccess) "txt_create_note_saved" else "txt_create_note_save"
                })
            }
        }
    }

}
