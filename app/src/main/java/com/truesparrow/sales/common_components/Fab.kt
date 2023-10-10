package com.truesparrow.sales.common_components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import com.truesparrow.sales.R
import com.truesparrow.sales.services.NavigationService
import com.truesparrow.sales.util.NoRippleInteractionSource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Fab() {
    FloatingActionButton(
        onClick = {
            Log.d("Fab", "Fab clicked")
            val isAccountSelectionEnabled = true;
            NavigationService.navigateToNotesScreen("1", "Select Account", isAccountSelectionEnabled, null)
        },
        interactionSource = NoRippleInteractionSource(),
        shape = RoundedCornerShape(4.dp),
        containerColor = Color(0xFF212653),
        modifier = Modifier.semantics {
            testTagsAsResourceId = true
            testTag = "btn_create_note"
        }
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