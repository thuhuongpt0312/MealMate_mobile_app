package vn.edu.uth.mealmate.domain

import vn.edu.uth.mealmate.data.model.MealSlot

/** Bữa sắp tới theo giờ hiện tại, dùng cho thẻ "Thực đơn hôm nay" ở Trang chủ. */
fun mealSlotForHour(hour: Int): MealSlot = when (hour) {
    in 0..9 -> MealSlot.BREAKFAST
    in 10..13 -> MealSlot.LUNCH
    in 14..15 -> MealSlot.SNACK
    else -> MealSlot.DINNER
}

/** Tên để chào: bỏ họ ("Nguyễn Minh Anh" -> "Minh Anh"), tên một chữ thì giữ nguyên. */
fun displayName(fullName: String): String {
    val words = fullName.split(' ', '\t').filter { it.isNotBlank() }
    return if (words.size > 1) words.drop(1).joinToString(" ") else words.firstOrNull().orEmpty()
}
