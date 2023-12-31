package com.truesparrow.sales.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truesparrow.sales.BuildConfig
import com.truesparrow.sales.R
import com.truesparrow.sales.common_components.CustomAlertDialog
import com.truesparrow.sales.common_components.UserAvatar
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.viewmodals.AuthenticationViewModal


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen() {
    var switchCheckedState by remember { mutableStateOf(true) }
    val authenticationViewModal: AuthenticationViewModal = hiltViewModel();
    val currentUser =
        authenticationViewModal.currentUserLiveData?.observeAsState()?.value?.data?.current_user
    val openDialogForSalesForceDisconnect = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight()
    ) {
        SettingHeader()
        Spacer(modifier = Modifier.padding(top = 20.dp))

        CustomAlertDialog(
            title = "Disconnect Salesforce",
            message = "This will delete your account and all details associated with it. This is an irreversible process, are you sure you want to do this?",
            onConfirmButtonClick = {
                authenticationViewModal.disconnectSalesForce()
                openDialogForSalesForceDisconnect.value = false
            },
            onDismissRequest = {
                openDialogForSalesForceDisconnect.value = false
            },
            showConfirmationDialog = openDialogForSalesForceDisconnect.value,
            titleTestTag = "setting_screen_disconnect_salesforce_title",
            messageTestTag = "setting_screen_disconnect_salesforce_message",
            confirmButtonTestTag = "setting_screen_disconnect_salesforce_confirm_button",
            dismissButtonTestTag = "setting_screen_disconnect_salesforce_dismiss_button"
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (currentUser != null) {
                UserAvatar(
                    id = currentUser.id ?: "121",
                    firstName = currentUser.name?.split(" ")?.get(0) ?: "",
                    lastName = currentUser.name?.split(" ")?.get(1) ?: "",
                    size = 40.dp,
                    userAvatarTestId = "setting_screen_user_profile"
                )
            }
            Log.i("SettingsScreen", "SettingsScreen: ${currentUser?.name}")

            if (currentUser?.name != null) {
                Log.i("SettingsScreen", "SettingsScreen: ${currentUser?.name}")
                currentUser?.name?.let {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF545A71),
                            letterSpacing = 0.56.sp,
                        ),
                        modifier = Modifier.semantics {
                            testTagsAsResourceId = true
                            testTag = "txt_user_account_detail_user_name"
                            contentDescription = "txt_user_account_detail_user_name"
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE9E9E9),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .fillMaxWidth()
                .height(158.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
                .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(323.dp)
                    .height(120.dp)
                    .background(color = Color(0xFFF5F5F6), shape = RoundedCornerShape(size = 4.dp))
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = Color(0xFFFFFFFF),
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(start = 4.dp, top = 2.dp, end = 8.dp, bottom = 2.dp)
                        .clickable {
                            openDialogForSalesForceDisconnect.value = true
                        }
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = "btn_user_account_detail_disconnect_salesforce"
                            contentDescription = "btn_user_account_detail_disconnect_salesforce"
                        }
                ) {
                    Switch(
                        checked = switchCheckedState,
                        onCheckedChange = { switchCheckedState = true }
                    )
                    Text(
                        text = "Disconnect Salesforce",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF545A71),
                        ),
                        modifier = Modifier.semantics {
                            testTagsAsResourceId = true
                            testTag = "txt_user_account_detail_disconnect_salesforce"
                            contentDescription = "txt_user_account_detail_disconnect_salesforce"
                        }
                    )

                }
                Spacer(modifier = Modifier.padding(top = 8.dp))
                DisconnectingSalesforceText()
            }
        }

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE9E9E9),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .shadow(
                    elevation = 1.dp,
                    spotColor = Color(0x0D000000),
                    ambientColor = Color(0x0D000000)
                )
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
                .semantics {
                    testTagsAsResourceId = true
                    testTag = "btn_user_account_detail_logout"
                    contentDescription = "btn_user_account_detail_logout"
                }
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = {
                        authenticationViewModal.logout()
                    },
                )
        ) {
            Image(painterResource(id = R.drawable.sign_out), contentDescription = "sign_out")
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Text(
                text = "Log Out",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF545A71),
                ),
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                    testTag = "txt_user_account_detail_logout"
                    contentDescription = "txt_user_account_detail_logout"
                }
            )

        }


        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF545A71),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "txt_user_account_detail_app_version"
                        contentDescription = "txt_user_account_detail_app_version"
                    }
                )


                Text(
                    text = "Sales Sparrow by True Sparrow",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF545A71),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = true
                        testTag = "txt_user_account_detail_app_name"
                        contentDescription = "txt_user_account_detail_app_name"
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingHeader() {
    Image(
        painterResource(id = R.drawable.close),
        contentDescription = "close",
        modifier = Modifier
            .padding(1.dp)
            .width(32.dp)
            .height(32.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    NavigationService.navigateBack()
                }
            )
            .semantics {
                testTagsAsResourceId = true
                testTag = "btn_user_account_detail_close"
                contentDescription = "btn_user_account_detail_close"
            }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DisconnectingSalesforceText(modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff545a71),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            ) { append("Disconnecting salesforce will also ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xffdd1a77),
                    fontSize = 14.sp
                )
            ) { append("delete your account") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff545a71),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            ) { append(" and all details associated with it.") }
        },
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .semantics {
                testTagsAsResourceId = true
                testTag = "txt_user_account_detail_disconnect_salesforce_message"
                contentDescription = "txt_user_account_detail_disconnect_salesforce_message"
            }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}