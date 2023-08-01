package com.example.salessparrow.common_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.salessparrow.ui.theme.Typography
import com.example.salessparrow.ui.theme.eastBay
import com.example.salessparrow.ui.theme.port_gore
import com.example.salessparrow.ui.theme.whisper
import com.example.salessparrow.ui.theme.white
import com.example.salessparrow.R


object AccountCardData {
    val accountName = "Dunlo"
    val website = "smagic.com"
    val contactName = "Gustavo Lipshutz"
    val contactTitle = "CTO"
    val presentation =
        "Presentation on how we would prepare and plan a migration from PHP to Ruby. Get the number of team members and detailed estimates."
}

@Composable
fun AccountCard() {
    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth()
            .border(1.dp, whisper, RoundedCornerShape(4.dp))
            .shadow(2.dp, shape = RoundedCornerShape(4.dp), clip = true),
        colors = CardDefaults.cardColors(
            containerColor = white,
        ),
    ) {
        Column(Modifier.padding(14.dp)) {
            CustomText(
                text = "ACCOUNT", Typography.titleSmall, color = eastBay.copy(alpha = 0.7f)

            )
            Spacer(modifier = Modifier.height(8.dp))

            CustomText(
                text = AccountCardData.accountName, Typography.titleMedium, color = port_gore
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.link_icon),
                    contentDescription = null,
                )
                CustomText(
                    text = AccountCardData.website, Typography.labelSmall, color = port_gore
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            CustomText(
                text = "CONTACT", Typography.titleSmall, color = eastBay.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomText(
                text = AccountCardData.contactName, Typography.titleMedium, color = port_gore,
            )
            CustomText(
                text = AccountCardData.contactTitle, Typography.labelSmall, color = port_gore
            )
        }
    }
}

@Composable
@Preview
fun PreviewAccountCard() {
    AccountCard()
}





