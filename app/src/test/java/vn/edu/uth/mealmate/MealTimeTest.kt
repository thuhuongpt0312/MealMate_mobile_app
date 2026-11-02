package vn.edu.uth.mealmate

import org.junit.Assert.assertEquals
import org.junit.Test
import vn.edu.uth.mealmate.data.model.MealSlot
import vn.edu.uth.mealmate.domain.displayName
import vn.edu.uth.mealmate.domain.mealSlotForHour

class MealTimeTest {

    @Test
    fun chonBuaTheoGio() {
        assertEquals(MealSlot.BREAKFAST, mealSlotForHour(6))
        assertEquals(MealSlot.LUNCH, mealSlotForHour(11))
        assertEquals(MealSlot.SNACK, mealSlotForHour(15))
        assertEquals(MealSlot.DINNER, mealSlotForHour(19))
        assertEquals(MealSlot.DINNER, mealSlotForHour(23))
    }

    @Test
    fun tenGoiBoHo() {
        assertEquals("Minh Anh", displayName("Nguyễn Minh Anh"))
        assertEquals("Lan", displayName("  Lan "))
        assertEquals("Thị Hương", displayName("Phạm   Thị Hương"))
        assertEquals("", displayName(""))
    }
}
