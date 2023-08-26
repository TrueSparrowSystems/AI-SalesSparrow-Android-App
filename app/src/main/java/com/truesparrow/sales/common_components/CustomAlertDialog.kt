package com.truesparrow.sales.common_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CustomAlertDialog(
    showConfirmationDialog: Boolean,
    title: String,
    message: String,
    onConfirmButtonClick: () -> Unit,
    onDismissRequest: () -> Unit
) {

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismissRequest()
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(message)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirmButtonClick()
                    }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismissRequest()
                    }) {
                    Text("Cancel")
                }
            }
        )

    }
}