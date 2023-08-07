package com.example.salessparrow.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.salessparrow.R
import com.example.salessparrow.common_components.AccountCard
import com.example.salessparrow.common_components.AccountListBottomSheet
import com.example.salessparrow.common_components.CustomHeader
import com.example.salessparrow.services.NavigationService
import com.example.salessparrow.util.Screens

@Composable
fun HomeScreen() {
    var bottomSheetVisible by remember { mutableStateOf(false) }

    val toggleBottomSheet: () -> Unit = {
        bottomSheetVisible = !bottomSheetVisible
    }

    val listState = rememberLazyListState()

    if (bottomSheetVisible) {
        AccountListBottomSheet( toggleBottomSheet )
    }


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
                isRightButtonPresent = true,
                rightIcon = R.drawable.search_icon,
                rightButtonText = "",
                rightButtonAction = toggleBottomSheet,
                isRightTextButton = true,
                rightButtonShape = CircleShape
            )
        },
        content = { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Color(0xFFF1F1F2)),
            ) {
                for (index in 0 until 100) {
                    item {
                        AccountCard()
                    }
                }
            }
        },

        bottomBar = {
            Box {
                BottomAppBar(
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
                        .height(60.dp)
                        .background(color = Color(0xFFF1F1F2)),
                    content = {},
                    contentColor = Color(0xFFF1F1F2),
                )
            }

        }

    )
}

@Composable
fun Fab() {
    FloatingActionButton(
        onClick = { NavigationService.navigateTo(Screens.NotesScreen.route) },
        shape = RoundedCornerShape(4.dp),
        containerColor = Color(0xFF212653)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Add Notes",
            tint = Color.White,
            modifier = Modifier
                .padding(1.dp)
                .width(18.dp)
                .height(18.dp)
        )
    }

}
