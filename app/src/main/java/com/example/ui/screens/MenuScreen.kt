package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MenuCategory
import com.example.model.MenuItem
import com.example.ui.theme.*

@Composable
fun MenuScreen(
    menuItems: List<MenuItem>,
    selectedCategory: MenuCategory,
    searchQuery: String,
    onCategorySelected: (MenuCategory) -> Unit,
    onSearchChanged: (String) -> Unit,
    onItemClick: (MenuItem) -> Unit,
    onQuickAdd: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredItems = menuItems.filter { item ->
        val matchesCategory = item.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                item.name.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true) ||
                (item.tag?.contains(searchQuery, ignoreCase = true) == true)
        if (searchQuery.isNotBlank()) matchesSearch else matchesCategory
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CoffeeBlack)
            .testTag("menu_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Menu Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Specialty Coffee & Kitchen",
                    color = LightTextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "Handcrafted at DC Colony branch with 100% Arabica beans",
                    color = LightTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChanged,
                    placeholder = { Text("Search Spanish Latte, Caramel Frappe, Croissants...", color = LightTextSecondary, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search", tint = CaramelGold)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchChanged("") }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear", tint = LightTextSecondary)
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CoffeeDarkCharcoal,
                        unfocusedContainerColor = CoffeeDarkCharcoal,
                        focusedBorderColor = CaramelGold,
                        unfocusedBorderColor = CoffeeSurfaceElevated,
                        focusedTextColor = LightTextPrimary,
                        unfocusedTextColor = LightTextPrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("menu_search_input")
                )
            }
        }

        // Student Perk Banner
        item {
            Surface(
                color = DeepRoast,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, CaramelGold.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.School,
                        contentDescription = null,
                        tint = CaramelGold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "GUJRANWALA STUDENTS PERK: 15% OFF",
                            color = CaramelGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "GIFT, Punjab University & FAST students enjoy 15% off all espresso brews with student ID.",
                            color = LightTextPrimary,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Category Horizontal Scroll Tabs
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(MenuCategory.values()) { category ->
                    val isSelected = category == selectedCategory && searchQuery.isBlank()
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            onSearchChanged("")
                            onCategorySelected(category)
                        },
                        label = {
                            Text(
                                text = category.displayName,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
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
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.testTag("category_chip_${category.name}")
                    )
                }
            }
        }

        // Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isNotBlank()) "Search Results (${filteredItems.size})" else selectedCategory.displayName,
                    color = LightTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = "${filteredItems.size} items",
                    color = LightTextSecondary,
                    fontSize = 12.sp
                )
            }
        }

        // Menu Items List
        items(filteredItems, key = { it.id }) { item ->
            MenuItemCard(
                item = item,
                onClick = { onItemClick(item) },
                onQuickAdd = { onQuickAdd(item) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
private fun MenuItemCard(
    item: MenuItem,
    onClick: () -> Unit,
    onQuickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CoffeeDarkCharcoal),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, CoffeeSurfaceElevated, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("menu_item_${item.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Item Image thumbnail
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(CoffeeSurfaceDark)
            ) {
                if (item.imageRes != null) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Coffee,
                            contentDescription = null,
                            tint = CaramelGold,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                if (item.is8ozSignature) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(bottomEnd = 8.dp))
                            .background(CaramelGold)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "8oz",
                            color = CoffeeBlack,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (item.tag != null) {
                    Text(
                        text = item.tag.uppercase(),
                        color = CaramelGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Text(
                    text = item.name,
                    color = LightTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = item.description,
                    color = LightTextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 3.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Rs. ${item.basePricePkr}",
                            color = CaramelGold,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${item.calories} kcal • ★ ${item.rating}",
                            color = LightTextSecondary,
                            fontSize = 10.sp
                        )
                    }

                    // Customize Button
                    Button(
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CoffeeSurfaceDark,
                            contentColor = CaramelGold
                        ),
                        border = BorderStroke(1.dp, CaramelGold.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = "Customize",
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Customize",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
