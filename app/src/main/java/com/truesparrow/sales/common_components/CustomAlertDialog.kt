package com.truesparrow.sales.common_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomAlertDialog(
    showConfirmationDialog: Boolean,
    title: String,
    message: String,
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
    titleTestTag: String = "",
    messageTestTag: String = "",
    confirmButtonTestTag: String = "",
    dismissButtonTestTag: String = ""
) {

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            title = {
                Text(
                    text = title,
                    modifier = Modifier.semantics {
                        testTag = titleTestTag
                        contentDescription = titleTestTag
                        testTagsAsResourceId = true

                    })
            },
            text = {
                Text(
                    message,
                    modifier = Modifier.semantics {
                        testTag = messageTestTag
                        contentDescription = messageTestTag
                        testTagsAsResourceId = true
                    })
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmButtonClick()
                    },
                    modifier = Modifier.semantics {
                        testTag = confirmButtonTestTag
                        contentDescription = confirmButtonTestTag
                        testTagsAsResourceId = true
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier.semantics {
                        testTag = dismissButtonTestTag
                        contentDescription = dismissButtonTestTag
                        testTagsAsResourceId = true
                    }
                ) {
                    Text("Cancel")
                }
            }
        )

    }
}