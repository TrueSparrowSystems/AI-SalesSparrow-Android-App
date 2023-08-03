package com.example.salessparrow.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.salessparrow.R
import com.example.salessparrow.common_components.CustomButton
import com.example.salessparrow.common_components.CustomText
import com.example.salessparrow.common_components.CustomTextWithImage
import com.example.salessparrow.common_components.HorizontalBar
import com.example.salessparrow.common_components.TermsAndConditionComponent
import com.example.salessparrow.viewmodals.AuthenticationViewModal


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LogInScreen() {
    val context = LocalContext.current;
    val authenticationViewModal: AuthenticationViewModal = hiltViewModel();

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F1F2)
    ) {
        Box(
            modifier = Modifier
                .width(348.dp)
                .height(386.dp)
                .padding(top = 40.dp, bottom = 40.dp, start = 20.dp, end = 20.dp),
            contentAlignment = Alignment.Center,

            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x1A000000),
                        ambientColor = Color(0x1A000000)
                    )
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
                    .padding(top = 40.dp, bottom = 40.dp, start = 20.dp, end = 20.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.sales_sparrow_logo),
                    contentDescription = "Sales Sparrow Logo",
                    modifier = Modifier
                        .width(160.dp)
                        .height(80.dp)
                )
                Text(
                    text = "Your Salesforce app with AI powered recommendations", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        color = Color(0xFF2A2E4F),
                        textAlign = TextAlign.Center,
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomTextWithImage(
                        imageId = R.drawable.notes,
                        imageContentDescription = "notes",
                        text = "Notes",
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2E4F),
                            textAlign = TextAlign.Center,
                        )
                    )
                    CustomTextWithImage(
                        imageId = R.drawable.tasks,
                        imageContentDescription = "tasks",
                        text = "Task",
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2E4F),
                            textAlign = TextAlign.Center,
                        )
                    )
                    CustomTextWithImage(
                        imageId = R.drawable.events,
                        imageContentDescription = "events",
                        text = "Events",
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2E4F),
                            textAlign = TextAlign.Center,
                        )
                    )
                    CustomTextWithImage(
                        imageId = R.drawable.oppurtunities,
                        imageContentDescription = "opportunities",
                        text = "Opportunities",
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.nunito_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2A2E4F),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                HorizontalBar()

                CustomText(
                    text = "Create Account",
                    customTextStyle = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF2A2E4F),
                        textAlign = TextAlign.Center,
                    )
                )


                CustomButton(
                    buttonText = "Continue with Salesforce",
                    buttonTextStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.64.sp
                    ),
                    onClick = {
                        authenticationViewModal.connectWithSalesForce(context);
                    },
                    imageId = R.drawable.salesforce_connect,
                    imageContentDescription = "salesforce_logo",
                    imageModifier = Modifier
                        .width(25.dp)
                        .height(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .semantics {
                            testTagsAsResourceId = true;
                            testTag = "salesforce_button"
                        },
                    isLoadingProgressBar = false,
                    buttonShape = RoundedCornerShape(size = 5.dp),
                )
            }

            TermsAndConditionComponent(
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}