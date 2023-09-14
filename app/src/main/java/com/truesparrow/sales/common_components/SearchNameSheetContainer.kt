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
import com.truesparrow.sales.R
import com.truesparrow.sales.models.Record
import com.truesparrow.sales.ui.theme.customFontFamily
import com.truesparrow.sales.ui.theme.walkaway_gray
import com.truesparrow.sales.ui.theme.white
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.Observer
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.viewmodals.GlobalStateViewModel
import com.truesparrow.sales.viewmodals.SearchCrmUserNameViewModal

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchNameSheetContainer(
    bottomSheetVisible: () -> Unit,
    accountId: String,
    accountName: String,
    id : String,
    onUpdateUserName : (userId : String, userName: String) -> Unit
) {
    val searchCrmUserNameViewModal: SearchCrmUserNameViewModal = hiltViewModel()
    var searchQuery by remember { mutableStateOf("") }
    var records by remember { mutableStateOf<List<Record>?>(null) }
    val isCrmUserApiInProgress = remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }


    searchCrmUserNameViewModal.searchAccountLiveDataData.observe(
        LocalLifecycleOwner.current,
        Observer { res ->
            when (res) {
                is NetworkResponse.Success -> {
                    isCrmUserApiInProgress.value = false
                    Log.i("MyApp", "crm_org_user_ids: ${res.data!!.crmOrganizationUserIds}")

                    records = res.data.crmOrganizationUserIds.map { crmOrganizationUserId ->
                        val userDetails = res.data.crmOrganizationUserMapById[crmOrganizationUserId]
                        Record(crmOrganizationUserId, userDetails?.name ?: "")
                    }
//                    focusRequester.requestFocus()
                    keyboardController?.show()
                }

                is NetworkResponse.Error -> {
                    isCrmUserApiInProgress.value = false
                    Log.i("MyApp", "accountIds: ${res.message}")
                }

                is NetworkResponse.Loading -> {
                    isCrmUserApiInProgress.value = true
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
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "crm_user_search_box"
                        }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Search",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                                .semantics {
                                    testTagsAsResourceId = true
                                    testTag = "txt_field_search_user"
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
                                searchCrmUserNameViewModal.onSearchQueryChanged(searchQuery)
                            },
                            placeholder = {
                                CustomText(
                                    text = "Search Users",
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
                                    testTagsAsResourceId = true
                                    testTag = "text_field_search_account"
                                    contentDescription = "txt_search_user_field"
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
                if (isCrmUserApiInProgress.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
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
                                    testTagsAsResourceId = true
                                    testTag = "txt_search_no_result_found"
                                    contentDescription = "txt_search_no_result_found"
                                },
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    records?.let { it1 ->
                        items(
                            count = it1.size,
                            itemContent = { index ->
                                val recordInfo = records!![index]
                                SearchUserName(
                                    firstName = recordInfo.name,
                                    lastName = recordInfo.name,
                                    crmUserId = recordInfo.id,
                                    id = id,
                                    searchNameTestId = "btn_search_user_user_name_${recordInfo.name}",
                                    onAccountRowClick = { crmUserId: String, crmUserName : String ->

//                                            searchCrmUserNameViewModal.onAccountRowClicked(
//                                                accountId = accountId,
//                                                accountName = accountName!!,
//                                            )
                                        onUpdateUserName(crmUserId, crmUserName)
                                        bottomSheetVisible()
                                    },
                                )
                            }
                        )
                    }

                }
            }
        }
    }
}

