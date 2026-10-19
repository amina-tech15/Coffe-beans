package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun LoyaltyStampCard(
    currentStamps: Int,
    onAddStamp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CoffeeDarkCharcoal
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Brush.horizontalGradient(listOf(CaramelGold.copy(alpha = 0.6f), CoffeeSurfaceElevated)),
                RoundedCornerShape(20.dp)
            )
            .testTag("loyalty_stamp_card")
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = null,
                            tint = CaramelGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "8oz LOYALTY PASSPORT",
                            color = CaramelGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = if (currentStamps >= 8) "Free 8oz Brew Unlocked! ☕" else "${currentStamps} of 8 Stamps Collected",
                        color = LightTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Tap to stamp button
                Button(
                    onClick = onAddStamp,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CaramelGold,
                        contentColor = CoffeeBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("stamp_loyalty_button")
                ) {
                    Text(
                        text = if (currentStamps >= 8) "Claim Drink" else "+ Tap to Stamp",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 8 Stamp Slots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 1..8) {
                    val isStamped = i <= currentStamps
                    val isFinalReward = i == 8
                    val bgColor by animateColorAsState(
                        targetValue = when {
                            isStamped && isFinalReward -> AmberGlow
                            isStamped -> CaramelGold
                            else -> CoffeeSurfaceDark
                        },
                        label = "stampBg"
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(bgColor)
                                .border(
                                    width = if (isFinalReward) 1.5.dp else 1.dp,
                                    color = if (isFinalReward) AmberGlow else CoffeeSurfaceElevated,
                                    shape = CircleShape
                                )
                                .clickable { onAddStamp() },
                            contentAlignment = Alignment.Center
                        ) {
                            if (isStamped) {
                                Icon(
                                    imageVector = if (isFinalReward) Icons.Filled.EmojiEvents else Icons.Filled.Check,
                                    contentDescription = "Stamped",
                                    tint = CoffeeBlack,
                                    modifier = Modifier.size(16.dp)
                                )
                            } else {
                                Text(
                                    text = if (isFinalReward) "★" else "$i",
                                    color = if (isFinalReward) CaramelGold else LightTextSecondary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = if (isFinalReward) "FREE" else "8oz",
                            color = if (isStamped) CaramelGold else LightTextSecondary.copy(alpha = 0.7f),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Collect 8 stamps with any specialty brew to receive a complimentary 8oz Spanish Latte or Cortado at DC Colony branch.",
                color = LightTextSecondary,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}
