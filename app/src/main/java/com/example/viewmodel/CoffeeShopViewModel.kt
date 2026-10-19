package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CoffeeData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class AppTab(val title: String) {
    DESIGN("Design"),
    MENU("Menu"),
    TRAY("Tray"),
    RESERVE("Reserve"),
    BRANCH("DC Colony")
}

enum class OrderStatus {
    IDLE,
    GRINDING_BEANS,
    EXTRACTING_ESPRESSO,
    STEAMING_MICROFOAM,
    READY_AT_COUNTER
}

data class OrderConfirmation(
    val orderNumber: String,
    val items: List<CartItem>,
    val subtotal: Int,
    val discount: Int,
    val total: Int,
    val diningMode: String,
    val timestamp: String,
    val estimatedMinutes: Int = 8
)

data class CoffeeShopUiState(
    val currentTab: AppTab = AppTab.DESIGN,
    val architecturalViews: List<ArchitecturalView> = CoffeeData.architecturalShowcase,
    val selectedArchitecturalViewId: String = "storefront",
    val isNightLightingMode: Boolean = true,
    val isAmbientSoundPlaying: Boolean = false,

    // Menu State
    val menuItems: List<MenuItem> = CoffeeData.menuItems,
    val selectedCategory: MenuCategory = MenuCategory.SIGNATURE_8OZ,
    val searchQuery: String = "",
    val activeCustomizingItem: MenuItem? = null,

    // Cart State
    val cartItems: List<CartItem> = listOf(
        // Default with sample items to immediately delight the user
        CartItem(
            id = "cart_sample_1",
            menuItem = CoffeeData.menuItems.first { it.id == "sig_8oz_spanish_latte" },
            size = CupSize.SIGNATURE_8OZ,
            milk = MilkOption.WHOLE_MILK,
            roast = BeanRoastOption.DC_COLONY_SIGNATURE,
            sweetness = SweetnessLevel.REGULAR,
            quantity = 1
        ),
        CartItem(
            id = "cart_sample_2",
            menuItem = CoffeeData.menuItems.first { it.id == "frappe_signature_caramel" },
            size = CupSize.REGULAR_12OZ,
            milk = MilkOption.WHOLE_MILK,
            saltedCaramelDrizzle = true,
            quantity = 1
        )
    ),
    val isStudentDiscountApplied: Boolean = true,
    val diningMode: String = "Dine-in at Neelam Commercial",

    // Checkout & Status
    val activeOrderStatus: OrderStatus = OrderStatus.IDLE,
    val latestOrderConfirmation: OrderConfirmation? = null,

    // Loyalty
    val loyaltyStamps: Int = 6, // 6/8 to immediately give that satisfying progression

    // Reservations
    val reservations: List<TableReservation> = listOf(
        TableReservation(
            id = "res_101",
            guestName = "Coffee Enthusiast",
            contactNumber = "0300-1234567",
            zone = SeatingZone.WINDOW_HIGH_TOP,
            partySize = 2,
            dateString = "Today",
            timeSlot = "8:30 PM",
            specialOccasion = "Evening Catchup"
        )
    ),
    val snackbarMessage: String? = null
)

class CoffeeShopViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CoffeeShopUiState())
    val uiState: StateFlow<CoffeeShopUiState> = _uiState.asStateFlow()

    fun selectTab(tab: AppTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectArchitecturalView(viewId: String) {
        _uiState.update { it.copy(selectedArchitecturalViewId = viewId) }
    }

    fun toggleLightingMode() {
        _uiState.update { it.copy(isNightLightingMode = !it.isNightLightingMode) }
    }

    fun toggleAmbientSound() {
        _uiState.update { it.copy(isAmbientSoundPlaying = !it.isAmbientSoundPlaying) }
    }

    fun setCategory(category: MenuCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun openCustomizer(item: MenuItem) {
        _uiState.update { it.copy(activeCustomizingItem = item) }
    }

    fun closeCustomizer() {
        _uiState.update { it.copy(activeCustomizingItem = null) }
    }

    fun addToCart(cartItem: CartItem) {
        _uiState.update { state ->
            val updated = state.cartItems + cartItem
            state.copy(
                cartItems = updated,
                activeCustomizingItem = null,
                snackbarMessage = "Added ${cartItem.menuItem.name} (${cartItem.size.label}) to tray"
            )
        }
    }

    fun updateCartItemQuantity(cartItemId: String, delta: Int) {
        _uiState.update { state ->
            val updated = state.cartItems.mapNotNull { item ->
                if (item.id == cartItemId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else {
                    item
                }
            }
            state.copy(cartItems = updated)
        }
    }

    fun removeCartItem(cartItemId: String) {
        _uiState.update { state ->
            state.copy(
                cartItems = state.cartItems.filterNot { it.id == cartItemId },
                snackbarMessage = "Item removed from tray"
            )
        }
    }

    fun toggleStudentDiscount() {
        _uiState.update { it.copy(isStudentDiscountApplied = !it.isStudentDiscountApplied) }
    }

    fun setDiningMode(mode: String) {
        _uiState.update { it.copy(diningMode = mode) }
    }

    fun placeOrder() {
        val currentState = _uiState.value
        if (currentState.cartItems.isEmpty()) return

        val subtotal = currentState.cartItems.sumOf { it.calculateTotalPrice() }
        val discount = if (currentState.isStudentDiscountApplied) (subtotal * 0.15).toInt() else 0
        val total = subtotal - discount
        val orderNum = "8OZ-${(100..999).random()}"
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())

        val confirmation = OrderConfirmation(
            orderNumber = orderNum,
            items = currentState.cartItems,
            subtotal = subtotal,
            discount = discount,
            total = total,
            diningMode = currentState.diningMode,
            timestamp = timeFormat,
            estimatedMinutes = 8
        )

        // Add loyalty stamp
        val newStamps = (currentState.loyaltyStamps + 1).coerceAtMost(8)

        _uiState.update {
            it.copy(
                cartItems = emptyList(),
                latestOrderConfirmation = confirmation,
                activeOrderStatus = OrderStatus.GRINDING_BEANS,
                loyaltyStamps = newStamps,
                snackbarMessage = "Order $orderNum placed! Barista has begun brewing."
            )
        }

        // Simulate live barista brew steps
        viewModelScope.launch {
            delay(2500)
            _uiState.update { it.copy(activeOrderStatus = OrderStatus.EXTRACTING_ESPRESSO) }
            delay(2500)
            _uiState.update { it.copy(activeOrderStatus = OrderStatus.STEAMING_MICROFOAM) }
            delay(2500)
            _uiState.update { it.copy(activeOrderStatus = OrderStatus.READY_AT_COUNTER) }
        }
    }

    fun resetOrderState() {
        _uiState.update { it.copy(activeOrderStatus = OrderStatus.IDLE) }
    }

    fun addStamp() {
        _uiState.update { state ->
            val next = if (state.loyaltyStamps >= 8) 1 else state.loyaltyStamps + 1
            val msg = if (next == 8) "🎉 Congratulations! You unlocked a Free 8oz Cortado or Spanish Latte!"
            else "Loyalty Stamp #$next recorded! ${8 - next} stamps to your free drink."
            state.copy(loyaltyStamps = next, snackbarMessage = msg)
        }
    }

    fun createReservation(
        name: String,
        phone: String,
        zone: SeatingZone,
        partySize: Int,
        timeSlot: String
    ) {
        val newRes = TableReservation(
            id = "RES-${(1000..9999).random()}",
            guestName = name.ifBlank { "Guest" },
            contactNumber = phone.ifBlank { "0300-0000000" },
            zone = zone,
            partySize = partySize,
            dateString = "Today",
            timeSlot = timeSlot,
            specialOccasion = "Table at Neelam Commercial"
        )
        _uiState.update {
            it.copy(
                reservations = listOf(newRes) + it.reservations,
                snackbarMessage = "Table reserved at DC Colony branch! We're saving your spot."
            )
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
