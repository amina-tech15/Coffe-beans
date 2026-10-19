package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.TopBrandBar
import com.example.ui.screens.*
import com.example.ui.theme.EightOzCoffeeTheme
import com.example.ui.theme.CaramelGold
import com.example.ui.theme.CoffeeBlack
import com.example.ui.theme.CoffeeDarkCharcoal
import com.example.ui.theme.LightTextSecondary
import com.example.viewmodel.AppTab
import com.example.viewmodel.CoffeeShopViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CoffeeShopViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EightOzCoffeeTheme(darkTheme = true) {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                // Display snackbar messages
                LaunchedEffect(state.snackbarMessage) {
                    state.snackbarMessage?.let { msg ->
                        snackbarHostState.showSnackbar(msg)
                        viewModel.clearSnackbar()
                    }
                }

                // Handle back button: if not on Design, return to Design
                BackHandler(enabled = state.currentTab != AppTab.DESIGN) {
                    viewModel.selectTab(AppTab.DESIGN)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBrandBar(
                            cartCount = state.cartItems.sumOf { it.quantity },
                            onCartClick = { viewModel.selectTab(AppTab.TRAY) },
                            onLocationClick = { viewModel.selectTab(AppTab.BRANCH) }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = CoffeeDarkCharcoal,
                            tonalElevation = 8.dp,
                            windowInsets = WindowInsets.navigationBars,
                            modifier = Modifier.testTag("bottom_nav_bar")
                        ) {
                            NavigationBarItem(
                                selected = state.currentTab == AppTab.DESIGN,
                                onClick = { viewModel.selectTab(AppTab.DESIGN) },
                                icon = {
                                    Icon(
                                        imageVector = if (state.currentTab == AppTab.DESIGN) Icons.Filled.Apartment else Icons.Outlined.Apartment,
                                        contentDescription = "Design & Space"
                                    )
                                },
                                label = { Text("Design", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CoffeeBlack,
                                    selectedTextColor = CaramelGold,
                                    indicatorColor = CaramelGold,
                                    unselectedIconColor = LightTextSecondary,
                                    unselectedTextColor = LightTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_design")
                            )

                            NavigationBarItem(
                                selected = state.currentTab == AppTab.MENU,
                                onClick = { viewModel.selectTab(AppTab.MENU) },
                                icon = {
                                    Icon(
                                        imageVector = if (state.currentTab == AppTab.MENU) Icons.Filled.LocalCafe else Icons.Outlined.LocalCafe,
                                        contentDescription = "Menu"
                                    )
                                },
                                label = { Text("Menu", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CoffeeBlack,
                                    selectedTextColor = CaramelGold,
                                    indicatorColor = CaramelGold,
                                    unselectedIconColor = LightTextSecondary,
                                    unselectedTextColor = LightTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_menu")
                            )

                            NavigationBarItem(
                                selected = state.currentTab == AppTab.TRAY,
                                onClick = { viewModel.selectTab(AppTab.TRAY) },
                                icon = {
                                    val totalCount = state.cartItems.sumOf { it.quantity }
                                    BadgedBox(
                                        badge = {
                                            if (totalCount > 0) {
                                                Badge(containerColor = CaramelGold, contentColor = CoffeeBlack) {
                                                    Text("$totalCount", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (state.currentTab == AppTab.TRAY) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag,
                                            contentDescription = "Tray"
                                        )
                                    }
                                },
                                label = { Text("Tray", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CoffeeBlack,
                                    selectedTextColor = CaramelGold,
                                    indicatorColor = CaramelGold,
                                    unselectedIconColor = LightTextSecondary,
                                    unselectedTextColor = LightTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_tray")
                            )

                            NavigationBarItem(
                                selected = state.currentTab == AppTab.RESERVE,
                                onClick = { viewModel.selectTab(AppTab.RESERVE) },
                                icon = {
                                    Icon(
                                        imageVector = if (state.currentTab == AppTab.RESERVE) Icons.Filled.EventSeat else Icons.Outlined.EventSeat,
                                        contentDescription = "Reserve"
                                    )
                                },
                                label = { Text("Reserve", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CoffeeBlack,
                                    selectedTextColor = CaramelGold,
                                    indicatorColor = CaramelGold,
                                    unselectedIconColor = LightTextSecondary,
                                    unselectedTextColor = LightTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_reserve")
                            )

                            NavigationBarItem(
                                selected = state.currentTab == AppTab.BRANCH,
                                onClick = { viewModel.selectTab(AppTab.BRANCH) },
                                icon = {
                                    Icon(
                                        imageVector = if (state.currentTab == AppTab.BRANCH) Icons.Filled.LocationOn else Icons.Outlined.LocationOn,
                                        contentDescription = "DC Colony"
                                    )
                                },
                                label = { Text("DC Colony", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = CoffeeBlack,
                                    selectedTextColor = CaramelGold,
                                    indicatorColor = CaramelGold,
                                    unselectedIconColor = LightTextSecondary,
                                    unselectedTextColor = LightTextSecondary
                                ),
                                modifier = Modifier.testTag("nav_item_branch")
                            )
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    containerColor = CoffeeBlack
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Crossfade(targetState = state.currentTab, label = "TabCrossfade") { tab ->
                            when (tab) {
                                AppTab.DESIGN -> ExploreDesignScreen(
                                    views = state.architecturalViews,
                                    selectedViewId = state.selectedArchitecturalViewId,
                                    isNightMode = state.isNightLightingMode,
                                    isAmbientSoundPlaying = state.isAmbientSoundPlaying,
                                    onSelectView = viewModel::selectArchitecturalView,
                                    onToggleNightMode = viewModel::toggleLightingMode,
                                    onToggleAmbientSound = viewModel::toggleAmbientSound,
                                    onNavigateToMenu = { viewModel.selectTab(AppTab.MENU) },
                                    onNavigateToReserve = { viewModel.selectTab(AppTab.RESERVE) }
                                )

                                AppTab.MENU -> MenuScreen(
                                    menuItems = state.menuItems,
                                    selectedCategory = state.selectedCategory,
                                    searchQuery = state.searchQuery,
                                    onCategorySelected = viewModel::setCategory,
                                    onSearchChanged = viewModel::setSearchQuery,
                                    onItemClick = viewModel::openCustomizer,
                                    onQuickAdd = viewModel::openCustomizer
                                )

                                AppTab.TRAY -> CartScreen(
                                    cartItems = state.cartItems,
                                    isStudentDiscountApplied = state.isStudentDiscountApplied,
                                    diningMode = state.diningMode,
                                    activeOrderStatus = state.activeOrderStatus,
                                    latestOrderConfirmation = state.latestOrderConfirmation,
                                    onQuantityChange = viewModel::updateCartItemQuantity,
                                    onRemoveItem = viewModel::removeCartItem,
                                    onToggleStudentDiscount = viewModel::toggleStudentDiscount,
                                    onDiningModeChange = viewModel::setDiningMode,
                                    onPlaceOrder = viewModel::placeOrder,
                                    onResetOrder = viewModel::resetOrderState,
                                    onExploreMenu = { viewModel.selectTab(AppTab.MENU) }
                                )

                                AppTab.RESERVE -> ReserveTableScreen(
                                    reservations = state.reservations,
                                    onReserve = viewModel::createReservation
                                )

                                AppTab.BRANCH -> BranchInfoScreen(
                                    loyaltyStamps = state.loyaltyStamps,
                                    onAddStamp = viewModel::addStamp
                                )
                            }
                        }

                        // Customizer Bottom Sheet
                        state.activeCustomizingItem?.let { item ->
                            CustomizerBottomSheet(
                                item = item,
                                onDismiss = viewModel::closeCustomizer,
                                onAddToCart = viewModel::addToCart
                            )
                        }
                    }
                }
            }
        }
    }
}
