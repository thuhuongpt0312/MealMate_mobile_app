package vn.edu.uth.mealmate.data.repository

import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import vn.edu.uth.mealmate.data.model.Meal
import vn.edu.uth.mealmate.data.remote.ApiClient
import vn.edu.uth.mealmate.data.remote.BoughtRequest
import vn.edu.uth.mealmate.data.remote.CreatePlanRequest
import vn.edu.uth.mealmate.data.remote.LoginRequest
import vn.edu.uth.mealmate.data.remote.MealMateApi
import vn.edu.uth.mealmate.data.remote.MealPlanDto
import vn.edu.uth.mealmate.data.remote.NewPantryItem
import vn.edu.uth.mealmate.data.remote.PantryItemDto
import vn.edu.uth.mealmate.data.remote.PreferencesDto
import vn.edu.uth.mealmate.data.remote.RegisterRequest
import vn.edu.uth.mealmate.data.remote.ShoppingItemDto
import vn.edu.uth.mealmate.data.remote.ShoppingListDto
import vn.edu.uth.mealmate.data.remote.UserDto
import vn.edu.uth.mealmate.data.remote.toDomain
import vn.edu.uth.mealmate.data.remote.toUserErrorMessage
import vn.edu.uth.mealmate.data.session.SessionManager

/**
 * Nơi duy nhất ViewModel lấy dữ liệu từ backend. Mọi hàm trả [Result],
 * lỗi đã được đổi sang câu thông báo tiếng Việt (xem toUserErrorMessage).
 */
class MealMateRepository private constructor(private val api: MealMateApi = ApiClient.api) {

    private val _currentUser = MutableStateFlow<UserDto?>(null)
    val currentUser: StateFlow<UserDto?> = _currentUser.asStateFlow()

    /**
     * Thực đơn đang xem ở S07/S09. null = thực đơn mới nhất.
     * Chọn một tuần ở Lịch sử (S12b) thì Thực đơn và Mua sắm cùng chuyển sang tuần đó.
     */
    private val _activePlanId = MutableStateFlow<String?>(null)
    val activePlanId: StateFlow<String?> = _activePlanId.asStateFlow()

    val isLoggedIn: Boolean
        get() = SessionManager.isLoggedIn

    fun selectPlan(id: String?) {
        _activePlanId.value = id
    }

    // --- Tài khoản ---

    suspend fun login(email: String, password: String): Result<UserDto> = call {
        val res = login(LoginRequest(email = email, password = password))
        onSignedIn(res.token, res.user)
    }

    suspend fun register(name: String, email: String, password: String): Result<UserDto> = call {
        val res = register(RegisterRequest(name = name, email = email, password = password))
        onSignedIn(res.token, res.user)
    }

    /** Luôn xóa phiên ở máy, kể cả khi gọi server thất bại. */
    suspend fun logout() {
        call { logout() }
        SessionManager.clear()
        _currentUser.value = null
        _activePlanId.value = null
    }

    suspend fun getMe(): Result<UserDto> = call { me().user.also { _currentUser.value = it } }

    suspend fun getPreferences(): Result<PreferencesDto> = call { preferences() }

    suspend fun updatePreferences(prefs: PreferencesDto): Result<PreferencesDto> = call { updatePreferences(prefs) }

    // --- Tủ bếp ---

    suspend fun getPantry(): Result<List<PantryItemDto>> = call { pantry().items }

    suspend fun addPantryItem(name: String, amount: Double, unit: String): Result<PantryItemDto> =
        call { addPantry(NewPantryItem(name = name, amount = amount, unit = unit)) }

    suspend fun deletePantryItem(id: String): Result<Unit> = call { deletePantry(id).requireSuccess() }

    // --- Công thức ---

    suspend fun getRecipes(slot: String? = null): Result<List<Meal>> = call { recipes(slot).items.map { it.toDomain() } }

    suspend fun getRecipe(id: String): Result<Meal> = call { recipe(id).toDomain() }

    // --- Thực đơn, mua sắm ---

    suspend fun createPlan(days: Int? = null, servings: Int? = null, startDate: String? = null): Result<MealPlanDto> =
        call { createPlan(CreatePlanRequest(startDate = startDate, servings = servings, days = days)) }
            .onSuccess { _activePlanId.value = it.id }

    suspend fun getPlans(): Result<List<MealPlanDto>> = call { plans().items }

    suspend fun getPlan(id: String): Result<MealPlanDto> = call { plan(id) }

    /** Thực đơn đang chọn, không có thì lấy thực đơn mới nhất. null nếu người dùng chưa tạo thực đơn nào. */
    suspend fun getActivePlan(): Result<MealPlanDto?> = call {
        val id = _activePlanId.value ?: plans().items.firstOrNull()?.id
        id?.let { plan(it) }
    }

    suspend fun getShoppingList(planId: String): Result<ShoppingListDto> = call { shoppingList(planId) }

    suspend fun setBought(planId: String, itemId: String, bought: Boolean): Result<ShoppingItemDto> =
        call { setBought(planId, itemId, BoughtRequest(bought)) }

    private fun onSignedIn(token: String, user: UserDto): UserDto {
        SessionManager.save(token)
        _currentUser.value = user
        _activePlanId.value = null
        return user
    }

    private suspend fun <T> call(block: suspend MealMateApi.() -> T): Result<T> =
        try {
            Result.success(api.block())
        } catch (e: CancellationException) {
            throw e // để coroutine bị hủy đúng cách khi rời màn hình
        } catch (e: Throwable) {
            Result.failure(ApiException(e.toUserErrorMessage(), e))
        }

    private fun retrofit2.Response<*>.requireSuccess() {
        if (!isSuccessful) throw retrofit2.HttpException(this)
    }

    companion object {
        val instance: MealMateRepository by lazy { MealMateRepository() }
    }
}

/** Lỗi gọi API, [message] là câu tiếng Việt hiển thị được cho người dùng. */
class ApiException(message: String, cause: Throwable? = null) : Exception(message, cause)
