package com.example.salessparrow.common_components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
    val linkUrl = "https://chat.openai.com/"
}

@Composable
fun AccountCard() {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = white,
        ),
        shape = RoundedCornerShape(size = 4.dp),
        border = BorderStroke(width = 1.dp, color = whisper)
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
                Text(
                    text = AccountCardData.website,
                    color = port_gore,
                    style = Typography.labelMedium,
                    modifier = Modifier.clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(AccountCardData.linkUrl))
                        ContextCompat.startActivity(context, intent, null)
                    }
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





