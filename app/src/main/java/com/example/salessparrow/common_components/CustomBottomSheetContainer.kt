package com.example.salessparrow.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.salessparrow.R
import com.example.salessparrow.data.CatImage
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.port_gore
import com.example.salessparrow.viewmodals.AccountListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer() {
    val accountListViewModel: AccountListViewModel = hiltViewModel()

    var catImagesState by remember { mutableStateOf(emptyList<CatImage>()) }

    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier

                        .wrapContentWidth()
                        .padding(10.dp)
                        .heightIn(min = 1.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        CustomText(
                            text = "Search", Typography.labelSmall, color = port_gore
                        )
                    }
                }
                Divider(color = Color.Black, thickness = 1.dp)
            }
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            LaunchedEffect(true) {
                accountListViewModel.getCatImages(
                    callback = { catImages ->
                        catImages?.let {
                            catImagesState = it
                        }
                    },
                    errorCallback = { error ->
                        // Handle error
                    }
                )
            }

            LazyColumn {
                items(catImagesState) { catImage ->
                    AccountName(name = "hiii", true)
                }
            }


//            val accountNames = listOf(
//                "Google", "Microsoft", "Apple", "Amazon", "Facebook",
//                "IBM", "Oracle", "Adobe", "Intel", "Nvidia"
//            )
//            accountNames.forEach { name ->
//                AccountName(name = name, true)
//            }
        }
    }
}


