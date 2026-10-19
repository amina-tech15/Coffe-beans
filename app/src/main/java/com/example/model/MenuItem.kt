package com.example.model

import androidx.annotation.DrawableRes

enum class MenuCategory(val displayName: String, val iconName: String) {
    SIGNATURE_8OZ("8oz Specials", "Star"),
    FRAPPES_CHILLED("Frappes & Iced", "LocalDrink"),
    HOT_ESPRESSO("Espresso & Brews", "Coffee"),
    ARTISAN_BAKERY("Bakery & Pastries", "Cake"),
    SAVORY_BITES("Gourmet Savory", "LunchDining")
}

enum class CupSize(val label: String, val volumeDesc: String, val priceModifier: Int) {
    SIGNATURE_8OZ("8oz (Signature)", "Golden Ratio Brew", 0),
    REGULAR_12OZ("12oz (Regular)", "Balanced Double", 120),
    GRANDE_16OZ("16oz (Grande)", "Iced Extravaganza", 220)
}

enum class MilkOption(val label: String, val priceModifier: Int) {
    WHOLE_MILK("Full Cream Dairy", 0),
    OAT_MILK("Oat Milk (Barista Blend)", 150),
    ALMOND_MILK("Almond Milk", 150),
    SKIMMED_MILK("Skimmed Low Fat", 0)
}

enum class BeanRoastOption(val label: String, val notes: String) {
    DC_COLONY_SIGNATURE("DC Colony Dark Blend", "Dark chocolate, roasted pecan & caramel notes"),
    ETHIOPIAN_YIRGACHEFFE("Ethiopian Single Origin", "Light floral, jasmine & citrus brightness")
}

enum class SweetnessLevel(val label: String) {
    UNSWEETENED("0% Pure / No Sugar"),
    MILD("25% Subtle Hint"),
    REGULAR("50% Barista Standard"),
    SWEET("100% Rich & Indulgent")
}

data class MenuItem(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val basePricePkr: Int,
    val description: String,
    val tag: String? = null,
    @DrawableRes val imageRes: Int? = null,
    val is8ozSignature: Boolean = false,
    val calories: Int = 180,
    val rating: Float = 4.9f,
    val originNotes: String = "Specialty Grade Arabica 100%"
)

data class CartItem(
    val id: String,
    val menuItem: MenuItem,
    val size: CupSize = CupSize.SIGNATURE_8OZ,
    val milk: MilkOption = MilkOption.WHOLE_MILK,
    val roast: BeanRoastOption = BeanRoastOption.DC_COLONY_SIGNATURE,
    val sweetness: SweetnessLevel = SweetnessLevel.REGULAR,
    val extraEspressoShot: Boolean = false,
    val saltedCaramelDrizzle: Boolean = false,
    val vanillaColdFoam: Boolean = false,
    val specialNotes: String = "",
    val quantity: Int = 1
) {
    fun calculateUnitPrice(): Int {
        var price = menuItem.basePricePkr + size.priceModifier + milk.priceModifier
        if (extraEspressoShot) price += 150
        if (saltedCaramelDrizzle) price += 90
        if (vanillaColdFoam) price += 120
        return price
    }

    fun calculateTotalPrice(): Int = calculateUnitPrice() * quantity
}
