package com.truesparrow.sales.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.AccountCard
import com.truesparrow.sales.common_components.AccountListBottomSheet
import com.truesparrow.sales.common_components.CustomHeader
import com.truesparrow.sales.common_components.Fab
import com.truesparrow.sales.models.AccountCardData
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.ui.theme.lucky_point
import com.truesparrow.sales.util.NetworkResponse
import com.truesparrow.sales.util.Screens
import com.truesparrow.sales.viewmodals.AuthenticationViewModal
import com.truesparrow.sales.viewmodals.HomeScreenViewModal

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {
    val authenticationViewModal: AuthenticationViewModal = hiltViewModel()
    val homeScreenViewModal: HomeScreenViewModal = hiltViewModel()

    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    val listState = rememberLazyListState()

    if (bottomSheetVisible) {
        AccountListBottomSheet(toggleBottomSheet, true)
    }

    val currentUser = authenticationViewModal.currentUserLiveData?.observeAsState()?.value
    var accountCardList by remember {
        mutableStateOf<List<AccountCardData>?>(
            null
        )
    }

    var accountFeedLoading by remember {
        mutableStateOf(false)
    }

    homeScreenViewModal.accountFeedLiveData?.observeAsState()?.value?.let {
        when (it) {
            is NetworkResponse.Success -> {
                accountFeedLoading = false
                accountCardList = it.data?.account_ids?.map { accountId ->
                    var account_contact_associations_map_by_id = it.data.account_contact_associations_map_by_id?.get(accountId)
                    AccountCardData(
                        id = accountId,
                        name = it.data.account_map_by_id?.get(accountId)?.name ?: "",
                        website = it.data.account_map_by_id?.get(accountId)?.additional_fields?.website
                            ?: "",
                        contactName = it.data.contact_map_by_id?.get(account_contact_associations_map_by_id?.contact_ids?.get(0))?.name ?: ""
                    )

                }!!

                Log.i("HomeScreen", "Loading")
            }

            is NetworkResponse.Error -> {
                accountFeedLoading = false
                Log.i("HomeScreen", "Success")
            }

            is NetworkResponse.Loading -> {
                accountFeedLoading = true
                Log.i("HomeScreen", "Error")
            }
        }
    }


    Log.i("HomeScreen", "Current User: $currentUser")


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF1F1F2)),
        contentColor = Color(0xFFF1F1F2),
        floatingActionButton = {
            Fab()
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        topBar = {
            CustomHeader(
                isLeftButtonPresent = true,
                leftIcon = R.drawable.buildings,
                leftButtonText = "Accounts",
                leftButtonAction = { /* Add your action here */ },
                isLeftTextButton = true,
                leftButtonShape = CircleShape,
                leftButtonTextStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    color = lucky_point,
                    letterSpacing = 0.64.sp
                ),
                leftIconModifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .semantics {
                        contentDescription = "img_home_screen_account_icon"
                        testTag = "img_home_screen_account_icon"
                        testTagsAsResourceId = true
                    },
                leftTextModifier = Modifier
                    .semantics {
                        contentDescription = "txt_account_details_title"
                        testTag = "txt_account_details_title"
                        testTagsAsResourceId = true
                    },
                isLeftButtonEnabled = false,
                rightButtonTestId = "btn_search_account",
                isRightButtonPresent = true,
                rightIcon = R.drawable.search_icon,
                rightButtonModifier = Modifier
                    .semantics {
                        contentDescription = "btn_search_account"
                        testTag = "btn_search_account"
                        testTagsAsResourceId = true
                    },
                rightButtonText = "",
                rightButtonAction = toggleBottomSheet,
                isRightTextButton = true,
                rightButtonShape = CircleShape,
                rightIconModifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .semantics {
                        contentDescription = "btn_search_account"
                        testTag = "btn_search_account"
                        testTagsAsResourceId = true
                    },
                isRightButtonEnabled = true,
                shouldShowAvatarComponent = true,
                userAvatarModifier = Modifier
                    .semantics {
                        contentDescription = "txt_user_account_icon"
                        testTag = "txt_user_account_icon"
                        testTagsAsResourceId = true
                    },
                userName = currentUser?.data?.current_user?.name ?: "John ve",
                userId = currentUser?.data?.current_user?.name ?: "121",
            )
        },
        content = { innerPadding ->
            if (accountFeedLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFF1F1F2)),
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF212653),
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(color = Color(0xFFF1F1F2)),
                ) {
                    if (accountCardList != null) {
                        for (index in 0 until accountCardList?.size!!) {
                            val item = accountCardList?.get(index)
                            item {
                                AccountCard(
                                    accountName = item?.name ?: "",
                                    website = item?.website ?: "",
                                    onAccountCardClick = {
                                        NavigationService.navigateTo(Screens.AccountDetailsScreen.route)
                                    },
                                    contactName = item?.contactName ?: ""
                                )
                            }
                        }
                    }
                }
            }


        },

        bottomBar = {
            Box {
                BottomAppBar(
                    containerColor = Color(0xFFF1F1F2),
                    modifier = Modifier
                        .shadow(
                            elevation = 7.dp,
                            spotColor = Color(0x0F000000),
                            ambientColor = Color(0x0F000000)
                        )
                        .border(
                            BorderStroke(
                                width = 0.dp,
                                color = Color(0x33000000),
                            )
                        )
                        .fillMaxWidth()
                        .height(60.dp),
                    content = {},
                    contentColor = Color(0xFFF1F1F2),
                )
            }

        }

    )
}



