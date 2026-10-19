package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizerBottomSheet(
    item: MenuItem,
    onDismiss: () -> Unit,
    onAddToCart: (CartItem) -> Unit
) {
    var selectedSize by remember { mutableStateOf(if (item.is8ozSignature) CupSize.SIGNATURE_8OZ else CupSize.REGULAR_12OZ) }
    var selectedMilk by remember { mutableStateOf(MilkOption.WHOLE_MILK) }
    var selectedRoast by remember { mutableStateOf(BeanRoastOption.DC_COLONY_SIGNATURE) }
    var selectedSweetness by remember { mutableStateOf(SweetnessLevel.REGULAR) }
    var extraEspressoShot by remember { mutableStateOf(false) }
    var saltedCaramelDrizzle by remember { mutableStateOf(item.name.contains("Caramel", ignoreCase = true)) }
    var vanillaColdFoam by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }
    var specialInstructions by remember { mutableStateOf("") }

    val calculatedUnitPrice = remember(item, selectedSize, selectedMilk, extraEspressoShot, saltedCaramelDrizzle, vanillaColdFoam) {
        var price = item.basePricePkr + selectedSize.priceModifier + selectedMilk.priceModifier
        if (extraEspressoShot) price += 150
        if (saltedCaramelDrizzle) price += 90
        if (vanillaColdFoam) price += 120
        price
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CoffeeDarkCharcoal,
        tonalElevation = 16.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp)
                .testTag("customizer_sheet")
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        color = LightTextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${item.originNotes} • ${item.calories} kcal",
                        color = LightTextSecondary,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = "Close", tint = LightTextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cup Size Selection
            Text(
                text = "SELECT CUP SIZE",
                color = CaramelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CupSize.values().forEach { size ->
                    val isSelected = selectedSize == size
                    Surface(
                        color = if (isSelected) CoffeeSurfaceDark else CoffeeBlack,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedSize = size }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedSize = size },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = CaramelGold,
                                        unselectedColor = LightTextSecondary
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = size.label,
                                        color = LightTextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = size.volumeDesc,
                                        color = LightTextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Text(
                                text = if (size.priceModifier > 0) "+Rs. ${size.priceModifier}" else "Base",
                                color = if (isSelected) CaramelGold else LightTextSecondary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Bean Roast Selection
            Text(
                text = "COFFEE BEAN ROAST PROFILE",
                color = CaramelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            BeanRoastOption.values().forEach { roast ->
                val isSelected = selectedRoast == roast
                Surface(
                    color = if (isSelected) CoffeeSurfaceDark else CoffeeBlack,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedRoast = roast }
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = roast.label,
                                color = LightTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = CaramelGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Text(
                            text = roast.notes,
                            color = LightTextSecondary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Milk Choice
            Text(
                text = "MILK PREFERENCE",
                color = CaramelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            MilkOption.values().forEach { milk ->
                val isSelected = selectedMilk == milk
                Surface(
                    color = if (isSelected) CoffeeSurfaceDark else CoffeeBlack,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clickable { selectedMilk = milk }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = milk.label,
                            color = LightTextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (milk.priceModifier > 0) "+Rs. ${milk.priceModifier}" else "Included",
                            color = if (isSelected) CaramelGold else LightTextSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sweetness Level
            Text(
                text = "SWEETNESS LEVEL",
                color = CaramelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SweetnessLevel.values().forEach { level ->
                    val isSelected = selectedSweetness == level
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) CaramelGold else CoffeeBlack)
                            .border(1.dp, if (isSelected) CaramelGold else CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                            .clickable { selectedSweetness = level }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (level) {
                                SweetnessLevel.UNSWEETENED -> "0%"
                                SweetnessLevel.MILD -> "25%"
                                SweetnessLevel.REGULAR -> "50%"
                                SweetnessLevel.SWEET -> "100%"
                            },
                            color = if (isSelected) CoffeeBlack else LightTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Add-ons / Extras
            Text(
                text = "BARISTA EXTRAS",
                color = CaramelGold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CoffeeBlack)
                    .border(1.dp, CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                    .clickable { extraEspressoShot = !extraEspressoShot }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = extraEspressoShot,
                        onCheckedChange = { extraEspressoShot = it },
                        colors = CheckboxDefaults.colors(checkedColor = CaramelGold)
                    )
                    Text("Extra Double Ristretto Shot", color = LightTextPrimary, fontSize = 13.sp)
                }
                Text("+Rs. 150", color = CaramelGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CoffeeBlack)
                    .border(1.dp, CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                    .clickable { saltedCaramelDrizzle = !saltedCaramelDrizzle }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = saltedCaramelDrizzle,
                        onCheckedChange = { saltedCaramelDrizzle = it },
                        colors = CheckboxDefaults.colors(checkedColor = CaramelGold)
                    )
                    Text("Salted Caramel Drizzle", color = LightTextPrimary, fontSize = 13.sp)
                }
                Text("+Rs. 90", color = CaramelGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CoffeeBlack)
                    .border(1.dp, CoffeeSurfaceElevated, RoundedCornerShape(10.dp))
                    .clickable { vanillaColdFoam = !vanillaColdFoam }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = vanillaColdFoam,
                        onCheckedChange = { vanillaColdFoam = it },
                        colors = CheckboxDefaults.colors(checkedColor = CaramelGold)
                    )
                    Text("Vanilla Sweet Cold Foam", color = LightTextPrimary, fontSize = 13.sp)
                }
                Text("+Rs. 120", color = CaramelGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quantity & Final Add to Tray Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quantity Counter
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CoffeeSurfaceDark)
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Filled.Remove, contentDescription = "Decrease", tint = LightTextPrimary)
                    }
                    Text(
                        text = "$quantity",
                        color = LightTextPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Increase", tint = CaramelGold)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Submit Button
                Button(
                    onClick = {
                        val cartItem = CartItem(
                            id = "cart_${System.currentTimeMillis()}",
                            menuItem = item,
                            size = selectedSize,
                            milk = selectedMilk,
                            roast = selectedRoast,
                            sweetness = selectedSweetness,
                            extraEspressoShot = extraEspressoShot,
                            saltedCaramelDrizzle = saltedCaramelDrizzle,
                            vanillaColdFoam = vanillaColdFoam,
                            specialNotes = specialInstructions,
                            quantity = quantity
                        )
                        onAddToCart(cartItem)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CaramelGold,
                        contentColor = CoffeeBlack
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("confirm_add_to_tray_button")
                ) {
                    Text(
                        text = "Add to Tray • Rs. ${calculatedUnitPrice * quantity}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
