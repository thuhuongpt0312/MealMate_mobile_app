package vn.edu.uth.mealmate.data.remote

import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class UserDto(val id: String, val name: String, val email: String, val role: String, val status: String)
data class AuthResponse(val user: UserDto, val token: String, val expiresAt: String)
data class MeResponse(val user: UserDto)

data class PreferencesDto(
    val servings: Int,
    val goal: String,
    val weeklyBudget: Long,
    val tastes: List<String>,
    val avoid: List<String>,
)

data class IngredientDto(
    val name: String,
    val amount: Double,
    val unit: String,
    val group: String,
    val unitPrice: Long,
)

data class RecipeDto(
    val id: String,
    val name: String,
    val slot: String,
    val minutes: Int,
    val pricePerServing: Long,
    val calories: Int,
    val ingredients: List<IngredientDto>,
)

data class PantryItemDto(
    val id: String,
    val name: String,
    val amount: Double,
    val unit: String,
)

data class NewPantryItem(
    val name: String,
    val amount: Double,
    val unit: String,
)

data class PlanDayDto(
    val date: String,
    val meals: List<String>,
)

data class MealPlanDto(
    val id: String,
    val servings: Int,
    val startDate: String,
    val endDate: String,
    val days: List<PlanDayDto>,
    val totalCost: Long,
    val budget: Long?,
    val recipes: Map<String, RecipeDto>? = null, // chỉ có khi GET /meal-plans/{id}
)

data class CreatePlanRequest(
    val startDate: String? = null,
    val servings: Int? = null,
)

data class ShoppingItemDto(
    val id: String,
    val name: String,
    val amount: Double,
    val unit: String,
    val group: String,
    val cost: Long,
    val bought: Boolean,
)

data class ShoppingListDto(
    val items: List<ShoppingItemDto>,
    val total: Long,
    val remaining: Long,
)

data class BoughtRequest(val bought: Boolean)

data class ListResponse<T>(val items: List<T>, val total: Int)

interface MealMateApi {
    @POST("auth/register")
    suspend fun register(@Body body: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST("auth/logout")
    suspend fun logout(): retrofit2.Response<Unit>

    @GET("auth/me")
    suspend fun me(): MeResponse

    @GET("me/preferences")
    suspend fun preferences(): PreferencesDto

    @PUT("me/preferences")
    suspend fun updatePreferences(@Body body: PreferencesDto): PreferencesDto

    @GET("me/pantry")
    suspend fun pantry(): ListResponse<PantryItemDto>

    @POST("me/pantry")
    suspend fun addPantry(@Body body: NewPantryItem): PantryItemDto

    @DELETE("me/pantry/{id}")
    suspend fun deletePantry(@Path("id") id: String): retrofit2.Response<Unit>

    @GET("recipes")
    suspend fun recipes(@Query("slot") slot: String? = null): ListResponse<RecipeDto>

    @GET("recipes/{id}")
    suspend fun recipe(@Path("id") id: String): RecipeDto

    @POST("meal-plans")
    suspend fun createPlan(@Body body: CreatePlanRequest = CreatePlanRequest()): MealPlanDto

    @GET("meal-plans")
    suspend fun plans(): ListResponse<MealPlanDto>

    @GET("meal-plans/{id}")
    suspend fun plan(@Path("id") id: String): MealPlanDto

    @GET("meal-plans/{id}/shopping-list")
    suspend fun shoppingList(@Path("id") planId: String): ShoppingListDto

    @PATCH("meal-plans/{id}/shopping-list/{itemId}")
    suspend fun setBought(
        @Path("id") planId: String,
        @Path("itemId") itemId: String,
        @Body body: BoughtRequest,
    ): ShoppingItemDto
}
