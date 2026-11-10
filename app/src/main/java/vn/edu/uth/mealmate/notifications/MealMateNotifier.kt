package vn.edu.uth.mealmate.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import vn.edu.uth.mealmate.R

/**
 * S13b · Thông báo đẩy hệ thống (NotificationChannel & NotificationCompat).
 */
object MealMateNotifier {
    const val CHANNEL_MEAL = "channel_meal_reminder"
    const val CHANNEL_SHOPPING = "channel_shopping_reminder"
    const val CHANNEL_BUDGET = "channel_budget_alert"

    fun initChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val mealChannel = NotificationChannel(
                CHANNEL_MEAL,
                "Nhắc bữa ăn",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Nhắc nhở giờ nấu nướng và chuẩn bị món ăn"
            }

            val shoppingChannel = NotificationChannel(
                CHANNEL_SHOPPING,
                "Nhắc đi chợ",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Nhắc nhở các món cần mua cho thực đơn"
            }

            val budgetChannel = NotificationChannel(
                CHANNEL_BUDGET,
                "Cảnh báo ngân sách",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                description = "Cảnh báo khi chi tiêu sắp hoặc vượt ngưỡng ngân sách"
            }

            manager.createNotificationChannels(listOf(mealChannel, shoppingChannel, budgetChannel))
        }
    }

    fun showMealReminder(context: Context, mealName: String, minutes: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_MEAL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Đến giờ chuẩn bị bữa ăn")
            .setContentText("$mealName · $minutes phút để nấu")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        manager.notify(101, notification)
    }

    fun showShoppingReminder(context: Context, remainingItems: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_SHOPPING)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Nhắc đi chợ")
            .setContentText("Bạn còn $remainingItems món chưa mua cho thực đơn tuần này.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        manager.notify(102, notification)
    }
}
