package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
import com.example.ui.theme.*
import com.example.viewmodel.OrderConfirmation
import com.example.viewmodel.OrderStatus

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    isStudentDiscountApplied: Boolean,
    diningMode: String,
    activeOrderStatus: OrderStatus,
    latestOrderConfirmation: OrderConfirmation?,
    onQuantityChange: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit,
    onToggleStudentDiscount: () -> Unit,
    onDiningModeChange: (String) -> Unit,
    onPlaceOrder: () -> Unit,
    onResetOrder: () -> Unit,
    onExploreMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (activeOrderStatus != OrderStatus.IDLE && latestOrderConfirmation != null) {
        OrderTrackingView(
            confirmation = latestOrderConfirmation,
            status = activeOrderStatus,
            onDone = onResetOrder,
            modifier = modifier
        )
        return
    }

    if (cartItems.isEmpty()) {
        EmptyTrayView(
            onExploreMenu = onExploreMenu,
            modifier = modifier
        )
        return
    }

    val subtotal = cartItems.sumOf { it.calculateTotalPrice() }
    val studentDiscount = if (isStudentDiscountApplied) (subtotal * 0.15).toInt() else 0
    val total = subtotal - studentDiscount

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .testTag("cart_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Tray Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Your Coffee Tray",
                    color = LightTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "${cartItems.sumOf { it.quantity }} items ready for barista preparation",
                    color = LightTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Dining Mode Selector
        item {
            Surface(
                color = CoffeeDarkCharcoal,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val isDineIn = diningMode.contains("Dine-in", ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isDineIn) CaramelGold else Color.Transparent)
                            .clickable { onDiningModeChange("Dine-in at Neelam Commercial") }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Storefront,
                                contentDescription = null,
                                tint = if (isDineIn) CoffeeBlack else LightTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Dine-in Table",
                                color = if (isDineIn) CoffeeBlack else LightTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (!isDineIn) CaramelGold else Color.Transparent)
                            .clickable { onDiningModeChange("Quick Counter Takeaway") }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.ShoppingBag,
                                contentDescription = null,
                                tint = if (!isDineIn) CoffeeBlack else LightTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Takeaway Cup",
                                color = if (!isDineIn) CoffeeBlack else LightTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Cart Items
        items(cartItems, key = { it.id }) { item ->
            CartItemRow(
                item = item,
                onQuantityChange = { delta -> onQuantityChange(item.id, delta) },
                onRemove = { onRemoveItem(item.id) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Student Discount Toggle Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                border = BorderStroke(1.dp, if (isStudentDiscountApplied) CaramelGold else CoffeeSurfaceElevated),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.School,
                            contentDescription = null,
                            tint = CaramelGold,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "University Student ID (15% OFF)",
                                color = LightTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "GIFT, Punjab University, FAST or any campus ID",
                                color = LightTextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = isStudentDiscountApplied,
                        onCheckedChange = { onToggleStudentDiscount() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CoffeeBlack,
                            checkedTrackColor = CaramelGold,
                            uncheckedThumbColor = LightTextSecondary,
                            uncheckedTrackColor = CoffeeSurfaceDark
                        ),
                        modifier = Modifier.testTag("student_discount_switch")
                    )
                }
            }
        }

        // Bill Summary Breakdown
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
                        text = "ORDER SUMMARY",
                        color = CaramelGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", color = LightTextSecondary, fontSize = 13.sp)
                        Text("Rs. $subtotal", color = LightTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }

                    if (isStudentDiscountApplied && studentDiscount > 0) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Student Discount (15%)", color = MintGreenTag, fontSize = 13.sp)
                            Text("- Rs. $studentDiscount", color = MintGreenTag, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("GST & Taxes (Included)", color = LightTextSecondary, fontSize = 13.sp)
                        Text("Rs. 0", color = LightTextPrimary, fontSize = 13.sp)
                    }

                    Divider(
                        color = CoffeeSurfaceElevated,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Total Payable", color = LightTextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("Cash / Card / Raast at counter", color = LightTextSecondary, fontSize = 11.sp)
                        }
                        Text(
                            text = "Rs. $total",
                            color = CaramelGold,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Checkout Button
                    Button(
                        onClick = onPlaceOrder,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CaramelGold,
                            contentColor = CoffeeBlack
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("place_order_button")
                    ) {
                        Icon(Icons.Filled.LocalCafe, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Brew & Send to Barista • Rs. $total",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
        border = BorderStroke(0.5.dp, CoffeeSurfaceElevated),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.menuItem.name,
                        color = LightTextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${item.size.label} • ${item.milk.label}",
                        color = CaramelGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${item.roast.label} • ${item.sweetness.label}",
                        color = LightTextSecondary,
                        fontSize = 11.sp
                    )

                    val extras = mutableListOf<String>()
                    if (item.extraEspressoShot) extras.add("Extra Shot")
                    if (item.saltedCaramelDrizzle) extras.add("Caramel Drizzle")
                    if (item.vanillaColdFoam) extras.add("Vanilla Cold Foam")
                    if (extras.isNotEmpty()) {
                        Text(
                            text = "+ " + extras.joinToString(", "),
                            color = AmberGlow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Filled.DeleteOutline, contentDescription = "Remove", tint = LightTextSecondary, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rs. ${item.calculateTotalPrice()}",
                    color = LightTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                // Quantity Modifier
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CoffeeSurfaceDark)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    IconButton(
                        onClick = { onQuantityChange(-1) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Filled.Remove, contentDescription = "Less", tint = LightTextPrimary, modifier = Modifier.size(14.dp))
                    }
                    Text(
                        text = "${item.quantity}",
                        color = LightTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = { onQuantityChange(1) },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "More", tint = CaramelGold, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTrayView(
    onExploreMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(CoffeeDarkCharcoal),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingBag,
                    contentDescription = null,
                    tint = CaramelGold,
                    modifier = Modifier.size(38.dp)
                )
            }

            Text(
                text = "Your Coffee Tray is Empty",
                color = LightTextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Explore our 8oz Spanish Latte, Iced Caramel Frappe, or Fresh Bakery to start your order.",
                color = LightTextSecondary,
                fontSize = 13.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = onExploreMenu,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CaramelGold,
                    contentColor = CoffeeBlack
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.LocalCafe, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Browse Menu", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun OrderTrackingView(
    confirmation: OrderConfirmation,
    status: OrderStatus,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .padding(16.dp),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(if (status == OrderStatus.READY_AT_COUNTER) MintGreenTag else CaramelGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (status == OrderStatus.READY_AT_COUNTER) Icons.Filled.Check else Icons.Filled.Coffee,
                        contentDescription = null,
                        tint = CoffeeBlack,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (status == OrderStatus.READY_AT_COUNTER) "Ready at 8oz Counter!" else "Brewing in Progress",
                    color = LightTextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Text(
                    text = "Order #${confirmation.orderNumber} • ${confirmation.diningMode}",
                    color = CaramelGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Barista Status Steps
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                    border = BorderStroke(1.dp, CoffeeSurfaceElevated),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "LIVE BARISTA PIPELINE",
                            color = CaramelGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        BrewStepRow(
                            stepNumber = "1",
                            title = "Grinding Specialty Arabica Beans",
                            subtitle = "Precision dose & distribution tamp",
                            isActive = status == OrderStatus.GRINDING_BEANS,
                            isDone = status.ordinal > OrderStatus.GRINDING_BEANS.ordinal
                        )

                        BrewStepRow(
                            stepNumber = "2",
                            title = "9-Bar Ristretto Espresso Extraction",
                            subtitle = "Rich crema & aromatic extraction",
                            isActive = status == OrderStatus.EXTRACTING_ESPRESSO,
                            isDone = status.ordinal > OrderStatus.EXTRACTING_ESPRESSO.ordinal
                        )

                        BrewStepRow(
                            stepNumber = "3",
                            title = "Steaming Microfoam & Caramel Swirl",
                            subtitle = "Velvety texture & latte art finish",
                            isActive = status == OrderStatus.STEAMING_MICROFOAM,
                            isDone = status.ordinal > OrderStatus.STEAMING_MICROFOAM.ordinal
                        )

                        BrewStepRow(
                            stepNumber = "4",
                            title = "Ready at Neelam Commercial Counter",
                            subtitle = "Collect your drink with Order #${confirmation.orderNumber}",
                            isActive = status == OrderStatus.READY_AT_COUNTER,
                            isDone = status == OrderStatus.READY_AT_COUNTER,
                            isLast = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Order Receipt Breakdown
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "RECEIPT SUMMARY",
                            color = CaramelGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        confirmation.items.forEach { cartItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${cartItem.quantity}x ${cartItem.menuItem.name} (${cartItem.size.label})",
                                    color = LightTextPrimary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Rs. ${cartItem.calculateTotalPrice()}",
                                    color = LightTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Divider(color = CoffeeSurfaceElevated, modifier = Modifier.padding(vertical = 8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Paid", color = LightTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Rs. ${confirmation.total}", color = CaramelGold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDone,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CaramelGold,
                        contentColor = CoffeeBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Order Another Brew", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun BrewStepRow(
    stepNumber: String,
    title: String,
    subtitle: String,
    isActive: Boolean,
    isDone: Boolean,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isDone -> MintGreenTag
                            isActive -> CaramelGold
                            else -> CoffeeSurfaceDark
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = CoffeeBlack, modifier = Modifier.size(16.dp))
                } else {
                    Text(
                        text = stepNumber,
                        color = if (isActive) CoffeeBlack else LightTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(30.dp)
                        .background(if (isDone) MintGreenTag else CoffeeSurfaceElevated)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.padding(bottom = if (isLast) 0.dp else 12.dp)) {
            Text(
                text = title,
                color = if (isActive || isDone) LightTextPrimary else LightTextSecondary,
                fontSize = 13.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
            )
            Text(
                text = subtitle,
                color = if (isActive) CaramelGold else LightTextSecondary.copy(alpha = 0.7f),
                fontSize = 11.sp
            )
        }
    }
}
