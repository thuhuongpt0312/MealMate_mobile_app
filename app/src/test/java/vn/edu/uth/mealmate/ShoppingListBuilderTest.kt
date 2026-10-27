package vn.edu.uth.mealmate

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import vn.edu.uth.mealmate.data.model.FoodGroup
import vn.edu.uth.mealmate.data.model.Ingredient
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.model.MealSlot
import vn.edu.uth.mealmate.data.model.PantryItem
import vn.edu.uth.mealmate.domain.ShoppingListBuilder

class ShoppingListBuilderTest {
    private val rice = Ingredient("Gạo thơm", 100.0, "g", FoodGroup.PANTRY, 25)
    private fun meal(id: String) = Meal(id, "Cơm", MealSlot.DINNER, 20, 30_000, 500, listOf(rice))

    @Test
    fun mergesSameIngredientAcrossMeals() {
        val list = ShoppingListBuilder.build(listOf(meal("a"), meal("b")), 3, emptyList())
        assertEquals(1, list.size)
        assertEquals(600.0, list[0].amount, 0.001)
        assertEquals(15_000L, list[0].cost)
    }

    @Test
    fun skipsIngredientsFullyInPantry() {
        val list = ShoppingListBuilder.build(listOf(meal("a")), 3, listOf(PantryItem("Gạo thơm", 1000.0, "g")))
        assertTrue(list.isEmpty())
    }

    @Test
    fun buysOnlyMissingAmount() {
        val list = ShoppingListBuilder.build(listOf(meal("a")), 3, listOf(PantryItem("Gạo thơm", 100.0, "g")))
        assertEquals(200.0, list[0].amount, 0.001)
    }
}
