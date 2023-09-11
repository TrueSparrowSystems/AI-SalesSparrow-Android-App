package com.truesparrow.sales.common_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.truesparrow.sales.R
import com.truesparrow.sales.models.Record
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.ui.theme.walkaway_gray
import com.truesparrow.sales.ui.theme.white
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.SearchAccountViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomBottomSheetContainer(
    showAddNote: Boolean = false,
    isAccountSelectionEnabled: Boolean = false,
    bottomSheetVisible: () -> Unit
) {
    val searchAccountViewModal: SearchAccountViewModel = hiltViewModel()
    var searchQuery by remember { mutableStateOf("") }
    var records by remember { mutableStateOf<List<Record>?>(null) }
    val isAccountListApiInProgress = remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }


    searchAccountViewModal.searchAccountLiveDataData.observe(
        LocalLifecycleOwner.current,

        Observer { res ->
            when (res) {
                is NetworkResponse.Success -> {
                    isAccountListApiInProgress.value = false
                    Log.i("MyApp", "accountIds: ${res.data!!.accountIds}")
                    records = res.data.accountIds.map { accountId ->
                        val accountDetails = res.data.accountMapById[accountId]
                        Record(accountId, accountDetails?.name ?: "")
                    }
//                    focusRequester.requestFocus()
                    keyboardController?.show()
                }

                is NetworkResponse.Error -> {
                    isAccountListApiInProgress.value = false
                    Log.i("MyApp", "accountIds: ${res.message}")
                }

                is NetworkResponse.Loading -> {
                    isAccountListApiInProgress.value = true
                    Log.i("MyApp", "accountIds: ${res.message}")
                }
            }
        })


    Scaffold(
        containerColor = white,
        topBar = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "img_search_magnifying_glass",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .semantics {
                                    contentDescription = "img_search_magnifying_glass"
                                    testTagsAsResourceId = true
                                    testTag = "img_search_magnifying_glass"
                                }

                        )
                        TextField(
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 25.sp,
                                letterSpacing = 0.48.sp,
                                color = walkaway_gray,
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { keyboardController?.hide() }
                            ),
                            value = searchQuery, onValueChange = { newText ->
                                searchQuery = newText
                                searchAccountViewModal.onSearchQueryChanged(searchQuery)
                            },
                            placeholder = {
                                CustomText(
                                    text = "Search Accounts",
                                    customTextStyle = TextStyle(
                                        fontFamily = customFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp,
                                        lineHeight = 25.sp,
                                        letterSpacing = 0.48.sp,
                                        color = walkaway_gray
                                    ),
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .background(Color.Transparent)
                                .semantics {
                                    contentDescription = "text_field_search_account"
                                    testTagsAsResourceId = true
                                    testTag = "text_field_search_account"
                                },
                            maxLines = 1
                        )
                    }
                }
                Divider(
                    color = Color(0.36f, 0.40f, 0.55f, 0.60f),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
    ) { it ->
        Column(modifier = Modifier.padding(it)) {


            LazyColumn {
                if (isAccountListApiInProgress.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .height(70.dp)
                                    .width(30.dp)
                                    .padding(4.dp)
                            )
                        }

                    }
                } else if (records?.isEmpty() == true) {
                    item {
                        Text(
                            text = "No Result Found",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .semantics {
                                    contentDescription = "txt_search_no_result_found"
                                    testTagsAsResourceId = true
                                    testTag = "txt_search_no_result_found"
                                },
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    records?.let { it1 ->
                        items(
                            count = it1.size,
                            itemContent = { index ->
                                val recordInfo = records!![index]
                                AccountName(
                                    name = recordInfo.name,
                                    showAddNote,
                                    accountRowTestId = "btn_search_account_name_${recordInfo.name}",
                                    addNoteButtonTestId = "btn_search_add_note_${recordInfo.name}",
                                    onAccountRowClick = {
                                        searchAccountViewModal.onAccountRowClicked(
                                            recordInfo.name,
                                            recordInfo.id,
                                            isAccountSelectionEnabled
                                        )
                                        bottomSheetVisible()
                                    },
                                    onAddNoteClick = {
                                        searchAccountViewModal.onAddNoteClicked(
                                            recordInfo.name,
                                            recordInfo.id,
                                            isAccountSelectionEnabled
                                        )
                                        bottomSheetVisible()
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
