package com.example.salessparrow.common_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.R
import com.example.salessparrow.data.Record
import com.example.salessparrow.data.RecordInfo
import com.example.salessparrow.ui.theme.customFontFamily
import com.example.salessparrow.ui.theme.walkaway_gray
import com.example.salessparrow.ui.theme.white
import com.example.salessparrow.viewmodals.SearchAccountViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer() {
    val searchAccountViewModal: SearchAccountViewModel = hiltViewModel()
    var searchQuery by remember { mutableStateOf("") }
    var filteredRecords by remember { mutableStateOf<List<Record>?>(null) }
    var records by remember { mutableStateOf<List<Record>?>(null) }


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
                            contentDescription = "Search",
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                        TextField(
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                lineHeight = 25.sp,
                                letterSpacing = 0.48.sp,
                                color = walkaway_gray
                            ),
                            value = searchQuery, onValueChange = { newText ->
                                searchQuery = newText
                                filteredRecords = searchAccount(records, searchQuery)
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
                            modifier = Modifier.background(Color.Transparent),
                            maxLines = 1
                        )
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            LaunchedEffect(true) {
                searchAccountViewModal.getAccountList(callback = { accountsDataWrapper ->
                    records = accountsDataWrapper?.compositeResponse?.firstOrNull()?.body?.records
                    filteredRecords =
                        accountsDataWrapper?.compositeResponse?.firstOrNull()?.body?.records
                }, errorCallback = { error ->
                    Log.i("error", "error: $error")
                })
            }
            val filteredRecordInfoList = filteredRecords?.filter {
                it.Name.contains(searchQuery, ignoreCase = true)
            }?.map { record ->
                RecordInfo(name = record.Name, attributes = record.attributes)
            } ?: emptyList()


            LazyColumn {
                if (filteredRecordInfoList.isEmpty()) {
                    item {
                        Text(
                            text = "No results found",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    items(filteredRecordInfoList) { recordInfo ->
                        AccountName(name = recordInfo.name, true, attributes = recordInfo.attributes)
                    }
                }
            }
        }
    }
}

fun searchAccount(records: List<Record>?, searchValue: String): List<Record>? {
    return records?.filter { it.Name.contains(searchValue, ignoreCase = true) }
}

