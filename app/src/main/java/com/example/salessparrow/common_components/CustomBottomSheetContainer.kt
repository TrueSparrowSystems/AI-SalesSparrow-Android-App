package com.example.salessparrow.common_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.R
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.customFontFamily
import com.example.salessparrow.ui.theme.port_gore
import com.example.salessparrow.ui.theme.walkaway_gray
import com.example.salessparrow.ui.theme.white
import com.example.salessparrow.viewmodals.AccountListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer() {
    val accountListViewModel: AccountListViewModel = hiltViewModel()

    var catImagesState by remember { mutableStateOf(emptyList<CatImage>()) }
    var value by remember {
        mutableStateOf("")
    }

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
                            contentDescription = "Search ",
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
                            value = value,
                            onValueChange = { newText ->
                                value = newText
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
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            LaunchedEffect(true) {
                accountListViewModel.getCatImage(callback = { catImage ->
                    catImage?.let {
                        catImagesState = catImagesState + it
                    }
                }, errorCallback = { error ->
                    Log.i("CustomBottomSheetContainer", "CustomBottomSheetContainer: $error")
                    // Handle error
                })
            }

            LazyColumn {
                items(catImagesState) { catImage ->
                    val id = catImage.id
                    AccountName(name = id, true)
                }
            }
        }
    }
}

