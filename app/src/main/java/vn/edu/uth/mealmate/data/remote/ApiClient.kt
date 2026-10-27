package vn.edu.uth.mealmate.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Emulator: 10.0.2.2. Điện thoại thật: IP LAN của máy chạy backend. Phải có dấu / ở cuối.
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    /** Gán sau khi đăng nhập, lưu thêm vào DataStore để mở lại app vẫn còn. */
    @Volatile
    var token: String? = null

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder().apply {
                token?.let { header("Authorization", "Bearer $it") }
            }.build()
            chain.proceed(request)
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val api: MealMateApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MealMateApi::class.java)
}
