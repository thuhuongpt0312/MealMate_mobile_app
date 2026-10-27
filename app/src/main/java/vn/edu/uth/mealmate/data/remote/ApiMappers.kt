package vn.edu.uth.mealmate.data.remote

import org.json.JSONObject
import retrofit2.HttpException
import vn.edu.uth.mealmate.data.model.FoodGroup
import vn.edu.uth.mealmate.data.model.Ingredient
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.model.MealSlot
import vn.edu.uth.mealmate.data.model.PantryItem
import vn.edu.uth.mealmate.data.model.ShoppingItem
import java.io.IOException

fun IngredientDto.toDomain(): Ingredient = Ingredient(
    name = name,
    amount = amount,
    unit = unit,
    group = runCatching { FoodGroup.valueOf(group) }.getOrDefault(FoodGroup.OTHER),
    unitPrice = unitPrice,
)

fun RecipeDto.toDomain(): Meal = Meal(
    id = id,
    name = name,
    slot = runCatching { MealSlot.valueOf(slot) }.getOrDefault(MealSlot.LUNCH),
    minutes = minutes,
    pricePerServing = pricePerServing,
    calories = calories,
    ingredients = ingredients.map { it.toDomain() },
)

fun PantryItemDto.toDomain(): PantryItem = PantryItem(
    name = name,
    amount = amount,
    unit = unit,
)

fun ShoppingItemDto.toDomain(): ShoppingItem = ShoppingItem(
    name = name,
    amount = amount,
    unit = unit,
    group = runCatching { FoodGroup.valueOf(group) }.getOrDefault(FoodGroup.OTHER),
    cost = cost,
    bought = bought,
)

/**
 * Xử lý lỗi trả về từ API backend theo quy ước:
 * - 400 / 401 / 403 / 404 / 409 / 422: JSON { "error": "thông báo tiếng Việt", "details": ... }
 * - IOException: Lỗi mạng hoặc server chưa bật
 */
fun Throwable.toUserErrorMessage(): String {
    return when (this) {
        is HttpException -> {
            try {
                val body = response()?.errorBody()?.string()
                if (!body.isNullOrBlank()) {
                    val json = JSONObject(body)
                    json.optString("error", "Lỗi phản hồi từ máy chủ (${code()})")
                } else {
                    "Lỗi phản hồi từ máy chủ (${code()})"
                }
            } catch (_: Exception) {
                "Lỗi phản hồi từ máy chủ (${code()})"
            }
        }
        is IOException -> "Không thể kết nối đến máy chủ. Vui lòng kiểm tra server đang chạy ở http://10.0.2.2:3000."
        else -> message ?: "Đã xảy ra lỗi không xác định"
    }
}
