package vn.edu.uth.mealmate.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.model.PantryItem
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.BoughtRequest
import vn.edu.uth.mealmate.data.remote.CreatePlanRequest
import vn.edu.uth.mealmate.data.remote.LoginRequest
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.NewPantryItem
import vn.edu.uth.mealmate.data.remote.PreferencesDto
import vn.edu.uth.mealmate.data.remote.RegisterRequest
import vn.edu.uth.mealmate.data.remote.ShoppingItemDto
import vn.edu.uth.mealmate.data.remote.ShoppingListDto
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.remote.toDomain
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage

class MealMateRepository private constructor() {

    private val api = ApiClient.api

    private val _currentUser = MutableStateFlow<UserDto?>(null)
    val currentUser: StateFlow<UserDto?> = _currentUser.asStateFlow()

    val isLoggedIn: Boolean
        get() = ApiClient.token != null

    suspend fun login(email: String, password: String): Result<UserDto> {
        return try {
            val response = api.login(LoginRequest(email = email, password = password))
            ApiClient.token = response.token
            _currentUser.value = response.user
            Result.success(response.user)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<UserDto> {
        return try {
            val response = api.register(RegisterRequest(name = name, email = email, password = password))
            ApiClient.token = response.token
            _currentUser.value = response.user
            Result.success(response.user)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            runCatching { api.logout() }
            ApiClient.token = null
            _currentUser.value = null
            Result.success(Unit)
        } catch (e: Throwable) {
            ApiClient.token = null
            _currentUser.value = null
            Result.success(Unit)
        }
    }

    suspend fun getMe(): Result<UserDto> {
        return try {
            val res = api.me()
            _currentUser.value = res.user
            Result.success(res.user)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getPreferences(): Result<PreferencesDto> {
        return try {
            val res = api.preferences()
            Result.success(res)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun updatePreferences(prefs: PreferencesDto): Result<PreferencesDto> {
        return try {
            val res = api.updatePreferences(prefs)
            Result.success(res)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getPantry(): Result<List<PantryItem>> {
        return try {
            val res = api.pantry()
            val items = res.items.map { it.toDomain() }
            Result.success(items)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun addPantryItem(name: String, amount: Double, unit: String): Result<PantryItem> {
        return try {
            val res = api.addPantry(NewPantryItem(name = name, amount = amount, unit = unit))
            Result.success(res.toDomain())
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun deletePantryItem(id: String): Result<Unit> {
        return try {
            api.deletePantry(id)
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getRecipes(slot: String? = null): Result<List<Meal>> {
        return try {
            val res = api.recipes(slot)
            Result.success(res.items.map { it.toDomain() })
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getRecipe(id: String): Result<Meal> {
        return try {
            val res = api.recipe(id)
            Result.success(res.toDomain())
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun createPlan(startDate: String? = null, servings: Int? = null): Result<MealPlanDto> {
        return try {
            val plan = api.createPlan(CreatePlanRequest(startDate = startDate, servings = servings))
            Result.success(plan)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getPlans(): Result<List<MealPlanDto>> {
        return try {
            val res = api.plans()
            Result.success(res.items)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getPlan(id: String): Result<MealPlanDto> {
        return try {
            val res = api.plan(id)
            Result.success(res)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun getShoppingList(planId: String): Result<ShoppingListDto> {
        return try {
            val res = api.shoppingList(planId)
            Result.success(res)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    suspend fun setBought(planId: String, itemId: String, bought: Boolean): Result<ShoppingItemDto> {
        return try {
            val res = api.setBought(planId, itemId, BoughtRequest(bought))
            Result.success(res)
        } catch (e: Throwable) {
            Result.failure(Exception(e.toUserErrorMessage(), e))
        }
    }

    companion object {
        val instance: MealMateRepository by lazy { MealMateRepository() }
    }
}
