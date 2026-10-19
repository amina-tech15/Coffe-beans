package com.example

import com.example.data.CoffeeData
import com.example.model.*
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testArchitecturalViewsLoaded() {
        val views = CoffeeData.architecturalShowcase
        assertEquals(4, views.size)
        assertTrue(views.any { it.id == "storefront" })
        assertTrue(views.any { it.id == "espresso_bar" })
        assertTrue(views.any { it.id == "cozy_seating" })
        assertTrue(views.any { it.id == "signature_drinks" })
    }

    @Test
    fun testCartItemPriceCalculation() {
        val spanishLatte = CoffeeData.menuItems.first { it.id == "sig_8oz_spanish_latte" }
        val cartItem = CartItem(
            id = "test_cart_1",
            menuItem = spanishLatte,
            size = CupSize.SIGNATURE_8OZ,
            milk = MilkOption.WHOLE_MILK,
            roast = BeanRoastOption.DC_COLONY_SIGNATURE,
            extraEspressoShot = true, // +150
            saltedCaramelDrizzle = true, // +90
            quantity = 2
        )
        // Base 780 + 150 + 90 = 1020 per item * 2 = 2040
        assertEquals(1020, cartItem.calculateUnitPrice())
        assertEquals(2040, cartItem.calculateTotalPrice())
    }

    @Test
    fun testStudentDiscountComputation() {
        val subtotal = 1000
        val discount = (subtotal * 0.15).toInt()
        val total = subtotal - discount
        assertEquals(150, discount)
        assertEquals(850, total)
    }

    @Test
    fun testDCColonyBranchDetails() {
        val branch = CoffeeData.branchDetails
        assertTrue(branch.containsKey("Location"))
        assertTrue(branch["Location"]!!.contains("Neelam Commercial"))
        assertTrue(branch["Location"]!!.contains("DC Colony"))
        assertTrue(branch["Location"]!!.contains("Gujranwala"))
    }
}
