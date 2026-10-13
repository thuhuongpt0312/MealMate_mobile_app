package vn.edu.uth.mealmate.data.model

enum class FoodGroup(val label: String) {
    VEGETABLES("Rau củ"),
    PROTEIN("Thịt & đạm"),
    PANTRY("Đồ khô"),
    DAIRY("Sữa & trứng"),
    OTHER("Khác"),
}

enum class MealSlot(val label: String) {
    BREAKFAST("Bữa sáng"),
    LUNCH("Bữa trưa"),
    DINNER("Bữa tối"),
    SNACK("Bữa phụ"),
}

/** Nguyên liệu cho 1 phần ăn. unitPrice = giá (đồng) cho 1 đơn vị [unit]. */
data class Ingredient(
    val name: String,
    val amount: Double,
    val unit: String,
    val group: FoodGroup,
    val unitPrice: Long,
)

data class Meal(
    val id: String,
    val name: String,
    val slot: MealSlot,
    val minutes: Int,
    val pricePerServing: Long,
    val calories: Int,
    val ingredients: List<Ingredient>,
)

/** Nguyên liệu đang có trong tủ bếp (S10). */
data class PantryItem(val name: String, val amount: Double, val unit: String)

/** Một dòng trong danh sách mua sắm (S09). */
data class ShoppingItem(
    val name: String,
    val amount: Double,
    val unit: String,
    val group: FoodGroup,
    val cost: Long,
    val bought: Boolean = false,
)
