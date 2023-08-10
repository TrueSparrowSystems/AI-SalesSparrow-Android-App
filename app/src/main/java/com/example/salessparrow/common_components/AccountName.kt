package com.example.salessparrow.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.eastBay_70
import com.example.salessparrow.ui.theme.ruby
import com.example.salessparrow.ui.theme.walkaway_gray

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AccountName(
    name: String,
    showAddNote: Boolean = false,
    accountRowTestId: String,
    addNoteButtonTestId: String,
    onAccountRowClick: () -> Unit,
    onAddNoteClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(start = 20.dp, top = 11.dp, end = 10.dp, bottom = 11.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .semantics {
                    testTagsAsResourceId = true
                    testTag = accountRowTestId
                }
                .clickable(onClick = onAccountRowClick)

        ) {
            CustomText(
                text = name, Typography.labelMedium, color = walkaway_gray
            )
            Spacer(Modifier.weight(1f))
            if (showAddNote) {
                CustomText(
                    text = "Add Note",
                    Typography.labelSmall,
                    color = ruby,
                    modifier = Modifier
                        .semantics {
                            testTagsAsResourceId = true
                            testTag = addNoteButtonTestId
                        }
                        .clickable(
                            onClick = onAddNoteClick
                        )
                )
            }

        }


    }

    Divider(
        color = eastBay_70,
        thickness = 0.5.dp,
        modifier = Modifier
            .height(1.dp)
    )
}
