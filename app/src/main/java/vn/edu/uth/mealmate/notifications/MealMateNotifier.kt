package vn.edu.uth.mealmate.notifications

/**
 * S13b – Thông báo đẩy (màn hình khóa / banner). Phụ trách: A.
 * TODO:
 *  - Tạo NotificationChannel (nhắc bữa ăn, nhắc đi chợ, ngân sách, tổng kết).
 *  - Xin quyền POST_NOTIFICATIONS (Android 13+).
 *  - Đặt lịch bằng WorkManager hoặc AlarmManager theo cài đặt ở S14.
 *  - Tôn trọng "Giờ yên lặng" 22:00 – 06:30.
 */
object MealMateNotifier
