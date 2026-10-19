package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoffeeData
import com.example.model.CustomerReview
import com.example.ui.components.LoyaltyStampCard
import com.example.ui.theme.*

@Composable
fun BranchInfoScreen(
    loyaltyStamps: Int,
    onAddStamp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val reviews = CoffeeData.customerReviews
    val branch = CoffeeData.branchDetails

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .testTag("branch_info_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CaramelGold)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "GUJRANWALA FLAGSHIP",
                            color = CoffeeBlack,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "8oz Coffee – DC Colony",
                    color = LightTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Neelam Commercial, DC Colony, Gujranwala, Pakistan",
                    color = LightTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Loyalty Stamp Card widget
        item {
            LoyaltyStampCard(
                currentStamps = loyaltyStamps,
                onAddStamp = onAddStamp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Branch Essentials Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "CAFÉ AMENITIES & SCHEDULE",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Hours
                    InfoRow(
                        icon = Icons.Filled.AccessTime,
                        title = "Operating Hours",
                        subtitle = "8:00 AM – 1:00 AM Midnight (Daily)",
                        tag = "OPEN NOW"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // WiFi Card with Copy Action
                    Surface(
                        color = CoffeeBlack,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Wifi, contentDescription = null, tint = CaramelGold, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text("High-Speed 500Mbps Fiber WiFi", color = LightTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text("Password: GoldenRatio8oz", color = LightTextSecondary, fontSize = 11.sp)
                                }
                            }

                            TextButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    clipboard.setPrimaryClip(ClipData.newPlainText("8oz WiFi", "GoldenRatio8oz"))
                                    Toast.makeText(context, "WiFi Password Copied!", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Text("Copy", color = CaramelGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Location Info
                    InfoRow(
                        icon = Icons.Filled.Place,
                        title = "Neelam Commercial Street Front",
                        subtitle = "Plot 14, Neelam Commercial Market, DC Colony, Gujranwala",
                        tag = "Dedicated Parking"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Power & Study
                    InfoRow(
                        icon = Icons.Filled.Power,
                        title = "Study & Remote Work Hub",
                        subtitle = "Universal multi-plug sockets & USB-C ports at every booth",
                        tag = "Laptop Friendly"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action Buttons (Directions & Call)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                Toast.makeText(context, "Navigating to Neelam Commercial, DC Colony...", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CaramelGold, contentColor = CoffeeBlack),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Navigation, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Directions", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "Calling 8oz DC Colony: +92 55 389 8800", Toast.LENGTH_SHORT).show()
                            },
                            border = BorderStroke(1.dp, CaramelGold),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CreamBackground),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Filled.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Call Café", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Student Community Perk
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DeepRoast),
                border = BorderStroke(1.dp, CaramelGold.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(CaramelGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.School, contentDescription = null, tint = CoffeeBlack, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("University Student Discount", color = LightTextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "Flash your GIFT, Punjab University, or FAST student card at the counter to get an automatic 15% discount on all espresso drinks & frappes.",
                            color = LightTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Customer Reviews Header
        item {
            Text(
                text = "COMMUNITY VOICES & REVIEWS",
                color = CaramelGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
            )
        }

        // Reviews list
        items(reviews) { review ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                border = BorderStroke(0.5.dp, CoffeeSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(review.author, color = LightTextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text(review.role, color = CaramelGold, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                        Row {
                            repeat(review.rating) {
                                Text("★", color = AmberGlow, fontSize = 14.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "\"${review.comment}\"",
                        color = LightTextSecondary,
                        fontSize = 12.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Favorite: ${review.favoriteDrink}",
                        color = LightTextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    tag: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = CaramelGold, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(title, color = LightTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(subtitle, color = LightTextSecondary, fontSize = 11.sp)
            }
        }

        if (tag != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(CaramelGold.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(tag, color = CaramelGold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
