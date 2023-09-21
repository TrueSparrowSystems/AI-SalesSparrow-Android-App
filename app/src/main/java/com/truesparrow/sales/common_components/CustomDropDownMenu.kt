package com.truesparrow.sales.common_components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.truesparrow.sales.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteMenuClick: () -> Unit,
    onEditMenuClick: () -> Unit,
    deleteMenuTestTag: String? = "",
    editMenuTestTag: String? = ""
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.background(
            color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
        )
    ) {
        DropdownMenuItem(
            onClick = {
                onDismissRequest()
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(
                            color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
                        ).clickable {
                            onEditMenuClick()
                        }.semantics {
                            testTag = editMenuTestTag!!
                            testTagsAsResourceId = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "delete_tasks",
                            contentScale = ContentScale.None
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Edit", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF545A71),
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(
                            color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
                        ).clickable {
                            onDeleteMenuClick()
                        }.semantics {
                            testTag = deleteMenuTestTag!!
                            testTagsAsResourceId = true
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "delete_tasks",
                            contentScale = ContentScale.None
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Delete", style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF545A71),
                            )
                        )
                    }
                }

            }


        )
    }
//    DropdownMenuItem(onClick = {
//        onDismissRequest()
//        onDeleteMenuClick()
//    },
//        modifier = Modifier.semantics {
//            testTag = deleteMenuTestTag!!
//            testTagsAsResourceId = true
//        },
//        content = {
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.background(
//                    color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 5.dp)
//                )
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.delete),
//                    contentDescription = "delete_tasks",
//                    contentScale = ContentScale.None
//                )
//                Spacer(modifier = Modifier.width(5.dp))
//                Text(
//                    text = "Delete", style = TextStyle(
//                        fontSize = 16.sp,
//                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
//                        fontWeight = FontWeight(600),
//                        color = Color(0xFF545A71),
//                    )
//                )
//            }
//
//        }
//    )
}
