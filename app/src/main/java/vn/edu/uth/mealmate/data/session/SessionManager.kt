package vn.edu.uth.mealmate.data.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Giữ token đăng nhập, lưu vào SharedPreferences để mở lại app vẫn còn phiên.
 * Gọi [init] một lần trong MainActivity trước khi dùng API.
 */
object SessionManager {
    private const val PREFS_NAME = "mealmate_session"
    private const val KEY_TOKEN = "token"

    private var prefs: SharedPreferences? = null

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    val isLoggedIn: Boolean
        get() = _token.value != null

    fun init(context: Context) {
        if (prefs != null) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).also {
            _token.value = it.getString(KEY_TOKEN, null)
        }
    }

    fun save(token: String) {
        _token.value = token
        prefs?.edit { putString(KEY_TOKEN, token) }
    }

    /** Đăng xuất hoặc token hết hạn (server trả 401). */
    fun clear() {
        _token.value = null
        prefs?.edit { remove(KEY_TOKEN) }
    }
}
