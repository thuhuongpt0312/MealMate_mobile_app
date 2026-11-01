package vn.edu.uth.mealmate

import org.junit.Assert.assertEquals
import org.junit.Test
import vn.edu.uth.mealmate.data.model.FoodGroup
import vn.edu.uth.mealmate.domain.foodGroupOf
import vn.edu.uth.mealmate.domain.groupForShopping

class ShoppingGroupsTest {

    private data class Item(val name: String, val group: String, val bought: Boolean = false)

    private fun group(items: List<Item>) = groupForShopping(items, { it.group }, { it.bought })

    @Test
    fun nhomTheoThuTuFoodGroupVaBoNhomRong() {
        val result = group(listOf(Item("Trứng", "DAIRY"), Item("Cà rốt", "VEGETABLES"), Item("Ức gà", "PROTEIN")))
        assertEquals(listOf(FoodGroup.VEGETABLES, FoodGroup.PROTEIN, FoodGroup.DAIRY), result.map { it.first })
    }

    @Test
    fun monChuaMuaDungTruoc() {
        val result = group(listOf(Item("Cà rốt", "VEGETABLES", bought = true), Item("Hành lá", "VEGETABLES")))
        assertEquals(listOf("Hành lá", "Cà rốt"), result.single().second.map { it.name })
    }

    @Test
    fun maNhomLaXepVaoKhac() {
        assertEquals(FoodGroup.OTHER, foodGroupOf("SEAFOOD"))
        assertEquals(FoodGroup.OTHER, group(listOf(Item("Tôm", "SEAFOOD"))).single().first)
    }
}
