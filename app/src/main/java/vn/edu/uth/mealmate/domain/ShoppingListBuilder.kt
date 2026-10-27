package vn.edu.uth.mealmate.domain

import kotlin.math.roundToLong
import vn.edu.uth.mealmate.data.model.Ingredient
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.model.PantryItem
import vn.edu.uth.mealmate.data.model.ShoppingItem

/**
 * Tính năng cốt lõi: tạo danh sách mua sắm từ thực đơn và trừ nguyên liệu đã có trong tủ bếp.
 * Hàm thuần (không phụ thuộc Android) nên dễ viết unit test.
 */
object ShoppingListBuilder {

    fun build(meals: List<Meal>, servings: Int, pantry: List<PantryItem>): List<ShoppingItem> {
        // 1) Gộp nguyên liệu trùng tên + đơn vị qua tất cả các bữa
        val need = linkedMapOf<String, Pair<Ingredient, Double>>()
        for (meal in meals) {
            for (ing in meal.ingredients) {
                val key = keyOf(ing.name, ing.unit)
                val total = (need[key]?.second ?: 0.0) + ing.amount * servings
                need[key] = Pair(ing, total)
            }
        }

        // 2) Trừ lượng đang có trong tủ, chỉ giữ phần còn thiếu
        val have = pantry.associate { keyOf(it.name, it.unit) to it.amount }
        val result = mutableListOf<ShoppingItem>()
        for ((key, value) in need) {
            val ing = value.first
            val missing = value.second - (have[key] ?: 0.0)
            if (missing > 0.0) {
                result += ShoppingItem(
                    name = ing.name,
                    amount = missing,
                    unit = ing.unit,
                    group = ing.group,
                    cost = (missing * ing.unitPrice).roundToLong(),
                )
            }
        }
        return result
    }

    private fun keyOf(name: String, unit: String) = name.trim().lowercase() + "|" + unit.trim().lowercase()
}
