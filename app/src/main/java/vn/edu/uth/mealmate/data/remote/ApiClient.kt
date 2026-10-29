package vn.edu.uth.mealmate.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.edu.uth.mealmate.BuildConfig
import vn.edu.uth.mealmate.data.session.SessionManager

object ApiClient {
    /** Lấy từ BuildConfig (xem app/build.gradle.kts), luôn có dấu / ở cuối. */
    val BASE_URL: String = BuildConfig.API_BASE_URL

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = SessionManager.token.value
            val request = chain.request().newBuilder().apply {
                token?.let { header("Authorization", "Bearer $it") }
            }.build()
            val response = chain.proceed(request)
            // Token hết hạn hoặc server khởi động lại (dữ liệu in-memory) → xóa phiên để app quay về đăng nhập
            if (response.code == 401 && token != null && token == SessionManager.token.value) {
                SessionManager.clear()
            }
            response
        }
        .apply {
            // Chỉ log request ở bản debug để không lộ token/mật khẩu trong logcat của bản release
            if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        .build()

    val api: MealMateApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MealMateApi::class.java)
}
