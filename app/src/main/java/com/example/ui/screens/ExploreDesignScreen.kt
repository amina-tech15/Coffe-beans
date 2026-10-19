package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ArchitecturalView
import com.example.ui.theme.*

@Composable
fun ExploreDesignScreen(
    views: List<ArchitecturalView>,
    selectedViewId: String,
    isNightMode: Boolean,
    isAmbientSoundPlaying: Boolean,
    onSelectView: (String) -> Unit,
    onToggleNightMode: () -> Unit,
    onToggleAmbientSound: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToReserve: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activeView = views.find { it.id == selectedViewId } ?: views.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .testTag("explore_design_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Intro Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CaramelGold)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "ARCHITECTURAL VISUALIZATION",
                            color = CoffeeBlack,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "DC COLONY GUJRANWALA",
                        color = LightTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "8oz Coffee Concept & Spatial Design",
                    color = LightTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 30.sp
                )

                Text(
                    text = "A contemporary specialty café in Neelam Commercial designed for university students, young professionals, and specialty coffee lovers.",
                    color = LightTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // View Selection Tabs
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(views) { view ->
                    val isSelected = view.id == selectedViewId
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectView(view.id) },
                        label = {
                            Text(
                                text = view.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (view.id) {
                                    "storefront" -> Icons.Outlined.Storefront
                                    "espresso_bar" -> Icons.Outlined.CoffeeMaker
                                    "cozy_seating" -> Icons.Outlined.Chair
                                    else -> Icons.Outlined.LocalCafe
                                },
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = if (isSelected) CoffeeBlack else CaramelGold
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = CoffeeDarkCharcoal,
                            labelColor = LightTextSecondary,
                            selectedContainerColor = CaramelGold,
                            selectedLabelColor = CoffeeBlack
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = CoffeeSurfaceElevated,
                            selectedBorderColor = CaramelGold
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("view_tab_${view.id}")
                    )
                }
            }
        }

        // Interactive Render Showcase Stage
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, CoffeeSurfaceElevated, RoundedCornerShape(24.dp))
                ) {
                    Column {
                        // Image Canvas with Lighting Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                        ) {
                            Image(
                                painter = painterResource(id = activeView.drawableRes),
                                contentDescription = activeView.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Dynamic Lighting & Vignette Scrim
                            val overlayColor = if (isNightMode) {
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        CoffeeBlack.copy(alpha = 0.35f),
                                        CoffeeBlack.copy(alpha = 0.85f)
                                    )
                                )
                            } else {
                                Brush.verticalGradient(
                                    colors = listOf(
                                        CaramelGold.copy(alpha = 0.15f),
                                        Color.Transparent,
                                        CoffeeBlack.copy(alpha = 0.7f)
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(overlayColor)
                            )

                            // Top Badges on Render
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CoffeeBlack.copy(alpha = 0.75f))
                                        .border(0.5.dp, CaramelGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(AmberGlow)
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = activeView.lightingMood,
                                            color = LightTextPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CoffeeBlack.copy(alpha = 0.75f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "8K CINEMATIC",
                                        color = CaramelGold,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Bottom Caption on Render
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = activeView.category.uppercase(),
                                    color = CaramelGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = activeView.headline,
                                    color = LightTextPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 20.sp
                                )
                            }
                        }

                        // Interactive Control Strip (Lighting Mode & Ambient Audio)
                        Surface(
                            color = CoffeeSurfaceDark,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Lighting toggle
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onToggleNightMode() }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                        .testTag("toggle_lighting_button")
                                ) {
                                    Icon(
                                        imageVector = if (isNightMode) Icons.Filled.NightlightRound else Icons.Filled.WbSunny,
                                        contentDescription = "Lighting",
                                        tint = if (isNightMode) AmberGlow else CaramelGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isNightMode) "Night Glow (3000K)" else "Golden Hour (Day)",
                                        color = LightTextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                // Ambient Audio toggle
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onToggleAmbientSound() }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                        .testTag("toggle_sound_button")
                                ) {
                                    Icon(
                                        imageVector = if (isAmbientSoundPlaying) Icons.Filled.VolumeUp else Icons.Filled.VolumeOff,
                                        contentDescription = "Sound",
                                        tint = if (isAmbientSoundPlaying) MintGreenTag else LightTextSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isAmbientSoundPlaying) "Café Lo-Fi: ON" else "Ambiance Audio",
                                        color = if (isAmbientSoundPlaying) MintGreenTag else LightTextSecondary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Detailed Narrative
                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {
                            Text(
                                text = activeView.description,
                                color = LightTextPrimary,
                                fontSize = 14.sp,
                                lineHeight = 21.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Architectural & Design Highlights
                            Text(
                                text = "DESIGN & SPATIAL HIGHLIGHTS",
                                color = CaramelGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            activeView.designHighlights.forEach { highlight ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.CheckCircle,
                                        contentDescription = null,
                                        tint = CaramelGold,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(top = 2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = highlight,
                                        color = LightTextSecondary,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            // Architectural Specifications Grid
                            Text(
                                text = "COMMERCIAL SPECIFICATIONS",
                                color = CaramelGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CoffeeSurfaceDark)
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                activeView.architecturalSpecs.forEach { (specKey, specVal) ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = specKey,
                                            color = LightTextSecondary,
                                            fontSize = 12.sp
                                        )
                                        Text(
                                            text = specVal,
                                            color = LightTextPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Atmosphere Quote
                            Surface(
                                color = CoffeeBlack,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.FormatQuote,
                                        contentDescription = null,
                                        tint = CaramelGold,
                                        modifier = Modifier.size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = activeView.atmosphereNotes,
                                        color = LightTextPrimary,
                                        fontSize = 12.sp,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Color & Material Palette Breakdown
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .border(1.dp, CoffeeSurfaceElevated, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "THE SIGNATURE PALETTE",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Sophisticated Black, Warm Cream & Roasted Coffee Brown",
                        color = LightTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        PaletteSwatch(
                            color = CoffeeBlack,
                            name = "Coffee Black",
                            hex = "#141211",
                            desc = "Steel & facade",
                            modifier = Modifier.weight(1f)
                        )
                        PaletteSwatch(
                            color = CreamSurface,
                            name = "Warm Cream",
                            hex = "#F2ECE1",
                            desc = "Marble & walls",
                            textColor = DarkTextPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        PaletteSwatch(
                            color = EspressoBrown,
                            name = "Espresso",
                            hex = "#6B4226",
                            desc = "Walnut timber",
                            modifier = Modifier.weight(1f)
                        )
                        PaletteSwatch(
                            color = CaramelGold,
                            name = "Caramel Gold",
                            hex = "#D49A58",
                            desc = "Illuminated sign",
                            textColor = CoffeeBlack,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // The 8oz Philosophy Feature Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .border(1.dp, CaramelGold.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(CaramelGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Coffee,
                                contentDescription = null,
                                tint = CoffeeBlack,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "WHY 8oz? THE GOLDEN RATIO",
                                color = CaramelGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "The Purist's Standard for Milk & Espresso",
                                color = LightTextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "In standard 16oz cups, espresso is drowned in excess milk. The 8oz cup was engineered by third-wave baristas to preserve the complex chocolate and fruity notes of a double espresso while giving just enough velvety steamed milk for ideal sweetness and texture.",
                        color = LightTextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onNavigateToMenu,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CaramelGold,
                                contentColor = CoffeeBlack
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("explore_order_cta")
                        ) {
                            Icon(Icons.Filled.LocalCafe, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Taste 8oz Brews", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = onNavigateToReserve,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CreamBackground),
                            border = BorderStroke(1.dp, CaramelGold),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("explore_reserve_cta")
                        ) {
                            Icon(Icons.Filled.EventSeat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Reserve Table", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PaletteSwatch(
    color: Color,
    name: String,
    hex: String,
    desc: String,
    textColor: Color = LightTextPrimary,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .border(0.5.dp, CoffeeSurfaceElevated, RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = hex,
            color = textColor.copy(alpha = 0.85f),
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = desc,
            color = textColor.copy(alpha = 0.7f),
            fontSize = 8.sp
        )
    }
}
