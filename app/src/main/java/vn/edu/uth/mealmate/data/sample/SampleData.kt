package vn.edu.uth.mealmate.data.sample

import vn.edu.uth.mealmate.data.model.FoodGroup.DAIRY
import vn.edu.uth.mealmate.data.model.FoodGroup.PANTRY
import vn.edu.uth.mealmate.data.model.FoodGroup.PROTEIN
import vn.edu.uth.mealmate.data.model.FoodGroup.VEGETABLES
import vn.edu.uth.mealmate.data.model.Ingredient
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.model.MealSlot
import vn.edu.uth.mealmate.data.model.PantryItem

/** Dữ liệu mẫu theo Figma. Thay bằng Room/Repository khi làm xong phần lưu trữ. */
object SampleData {
    const val WEEKLY_BUDGET = 700_000L

    val meals = listOf(
        Meal(
            "banh-mi-trung-bo", "Bánh mì trứng bơ", MealSlot.BREAKFAST, 15, 22_000, 380,
            listOf(
                Ingredient("Bánh mì", 1.0, "ổ", PANTRY, 6_000),
                Ingredient("Trứng", 2.0, "quả", DAIRY, 3_500),
                Ingredient("Bơ", 0.5, "quả", VEGETABLES, 15_000),
            ),
        ),
        Meal(
            "bun-thit-nuong", "Bún thịt nướng", MealSlot.LUNCH, 20, 35_000, 520,
            listOf(
                Ingredient("Bún khô", 100.0, "g", PANTRY, 40),
                Ingredient("Thịt heo", 120.0, "g", PROTEIN, 130),
                Ingredient("Rau sống", 1.0, "phần", VEGETABLES, 3_000),
            ),
        ),
        Meal(
            "com-ga-gung", "Cơm gà gừng", MealSlot.DINNER, 25, 42_000, 540,
            listOf(
                Ingredient("Ức gà", 150.0, "g", PROTEIN, 90),
                Ingredient("Gạo thơm", 80.0, "g", PANTRY, 25),
                Ingredient("Rau bó xôi", 0.5, "túi", VEGETABLES, 20_000),
            ),
        ),
        Meal(
            "sua-chua-trai-cay", "Sữa chua trái cây", MealSlot.SNACK, 5, 18_000, 180,
            listOf(
                Ingredient("Sữa chua Hy Lạp", 1.0, "hũ", DAIRY, 12_000),
                Ingredient("Trái cây", 1.0, "phần", VEGETABLES, 6_000),
            ),
        ),
    )

    val pantry = listOf(
        PantryItem("Gạo thơm", 750.0, "g"),
        PantryItem("Trứng", 8.0, "quả"),
        PantryItem("Ức gà", 450.0, "g"),
        PantryItem("Rau bó xôi", 1.0, "túi"),
        PantryItem("Cà rốt", 5.0, "củ"),
        PantryItem("Nước tương", 0.5, "chai"),
    )
}
