package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SeatingZone
import com.example.model.TableReservation
import com.example.ui.theme.*

@Composable
fun ReserveTableScreen(
    reservations: List<TableReservation>,
    onReserve: (String, String, SeatingZone, Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var guestName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var selectedZone by remember { mutableStateOf(SeatingZone.STUDY_WORKSTATION) }
    var partySize by remember { mutableStateOf(2) }
    var selectedTimeSlot by remember { mutableStateOf("Twilight 6:30 PM") }

    val timeSlots = listOf(
        "Morning Brew 9:30 AM",
        "Afternoon Study 2:00 PM",
        "Twilight 6:30 PM",
        "Evening Hangout 8:30 PM",
        "Late Night 10:30 PM",
        "Midnight Session 11:45 PM"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .testTag("reserve_table_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Reserve Your Spot",
                    color = LightTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Neelam Commercial, DC Colony Branch • AC Power & High-Speed WiFi Guaranteed",
                    color = LightTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Reservation Form Card
        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "1. SELECT SEATING ZONE",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SeatingZone.values().forEach { zone ->
                            val isSelected = zone == selectedZone
                            Surface(
                                color = if (isSelected) CoffeeSurfaceDark else CoffeeBlack,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedZone = zone }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedZone = zone },
                                        colors = RadioButtonDefaults.colors(selectedColor = CaramelGold)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = zone.title,
                                            color = LightTextPrimary,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = zone.description,
                                            color = LightTextSecondary,
                                            fontSize = 11.sp
                                        )
                                        Text(
                                            text = "Best for: ${zone.idealFor}",
                                            color = CaramelGold,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Party Size
                    Text(
                        text = "2. NUMBER OF GUESTS",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(1, 2, 3, 4, 6, 8).forEach { size ->
                            val isSelected = partySize == size
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) CaramelGold else CoffeeBlack)
                                    .border(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                                    .clickable { partySize = size }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (size == 1) "Solo" else "$size",
                                    color = if (isSelected) CoffeeBlack else LightTextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Time Slot
                    Text(
                        text = "3. TIME SLOT (TODAY)",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        timeSlots.chunked(2).forEach { rowSlots ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                rowSlots.forEach { slot ->
                                    val isSelected = selectedTimeSlot == slot
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(if (isSelected) CoffeeSurfaceDark else CoffeeBlack)
                                            .border(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                                            .clickable { selectedTimeSlot = slot }
                                            .padding(horizontal = 10.dp, vertical = 10.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = slot,
                                            color = if (isSelected) CaramelGold else LightTextSecondary,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Contact Details
                    Text(
                        text = "4. GUEST CONTACT",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = guestName,
                        onValueChange = { guestName = it },
                        placeholder = { Text("Your Name (e.g. Ali Ahmed)", color = LightTextSecondary, fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CoffeeBlack,
                            unfocusedContainerColor = CoffeeBlack,
                            focusedBorderColor = CaramelGold,
                            unfocusedBorderColor = CoffeeSurfaceElevated,
                            focusedTextColor = LightTextPrimary,
                            unfocusedTextColor = LightTextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reserve_name_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        placeholder = { Text("WhatsApp / Phone (03XX-XXXXXXX)", color = LightTextSecondary, fontSize = 13.sp) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CoffeeBlack,
                            unfocusedContainerColor = CoffeeBlack,
                            focusedBorderColor = CaramelGold,
                            unfocusedBorderColor = CoffeeSurfaceElevated,
                            focusedTextColor = LightTextPrimary,
                            unfocusedTextColor = LightTextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reserve_phone_input")
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Submit Reservation
                    Button(
                        onClick = {
                            onReserve(guestName, phoneNumber, selectedZone, partySize, selectedTimeSlot)
                            guestName = ""
                            phoneNumber = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CaramelGold,
                            contentColor = CoffeeBlack
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_reservation_button")
                    ) {
                        Icon(Icons.Filled.EventSeat, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Confirm Reservation at DC Colony",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Existing Bookings Section
        if (reservations.isNotEmpty()) {
            item {
                Text(
                    text = "YOUR ACTIVE BOOKINGS",
                    color = CaramelGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }

            items(reservations, key = { it.id }) { reservation ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                    border = BorderStroke(0.5.dp, CoffeeSurfaceElevated),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(MintGreenTag)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Confirmed • ${reservation.zone.title}",
                                    color = LightTextPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Guest: ${reservation.guestName} (${reservation.partySize} guests)",
                                color = LightTextSecondary,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Text(
                                text = "${reservation.dateString} at ${reservation.timeSlot} • Neelam Commercial",
                                color = CaramelGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MintGreenTag.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "TABLE SAVED",
                                color = MintGreenTag,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
