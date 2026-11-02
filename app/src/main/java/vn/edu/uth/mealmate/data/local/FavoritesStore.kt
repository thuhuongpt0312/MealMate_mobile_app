package vn.edu.uth.mealmate.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * S12 · Món yêu thích, lưu id món trên máy (backend chưa có API yêu thích).
 * Gọi [init] một lần trong MainActivity.
 */
object FavoritesStore {
    private const val PREFS_NAME = "mealmate_favorites"
    private const val KEY_IDS = "recipe_ids"

    private var prefs: SharedPreferences? = null

    private val _ids = MutableStateFlow<Set<String>>(emptySet())
    val ids: StateFlow<Set<String>> = _ids.asStateFlow()

    fun init(context: Context) {
        if (prefs != null) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).also {
            // Copy ra Set mới: Set trả về từ getStringSet không được sửa trực tiếp
            _ids.value = it.getStringSet(KEY_IDS, emptySet()).orEmpty().toSet()
        }
    }

    fun isFavorite(id: String): Boolean = id in _ids.value

    fun toggle(id: String) {
        val next = if (id in _ids.value) _ids.value - id else _ids.value + id
        _ids.value = next
        prefs?.edit { putStringSet(KEY_IDS, next) }
    }
}
