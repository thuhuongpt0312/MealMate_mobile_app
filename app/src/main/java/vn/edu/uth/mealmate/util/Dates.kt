package vn.edu.uth.mealmate.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeParseException

/** Ngày từ API dạng "YYYY-MM-DD", null nếu sai định dạng. */
fun parseApiDate(value: String): LocalDate? =
    try {
        LocalDate.parse(value)
    } catch (_: DateTimeParseException) {
        null
    }

/** T2, T3 … CN – dùng cho thanh chọn ngày ở S07. */
fun DayOfWeek.shortLabel(): String = if (this == DayOfWeek.SUNDAY) "CN" else "T${value + 1}"

/** Thứ Hai … Chủ Nhật */
fun DayOfWeek.longLabel(): String = when (this) {
    DayOfWeek.MONDAY -> "Thứ Hai"
    DayOfWeek.TUESDAY -> "Thứ Ba"
    DayOfWeek.WEDNESDAY -> "Thứ Tư"
    DayOfWeek.THURSDAY -> "Thứ Năm"
    DayOfWeek.FRIDAY -> "Thứ Sáu"
    DayOfWeek.SATURDAY -> "Thứ Bảy"
    DayOfWeek.SUNDAY -> "Chủ Nhật"
}

private fun LocalDate.dayMonth() = "%02d/%02d".format(dayOfMonth, monthValue)

/** "Thứ Ba, 12/03" */
fun formatFullDate(date: LocalDate): String = "${date.dayOfWeek.longLabel()}, ${date.dayMonth()}"

/** "11–17/03" nếu cùng tháng, "26/10–01/11" nếu khác tháng. Sai định dạng thì trả nguyên chuỗi. */
fun formatDateRange(start: String, end: String): String {
    val from = parseApiDate(start) ?: return "$start–$end"
    val to = parseApiDate(end) ?: return "$start–$end"
    return if (from.year == to.year && from.month == to.month) {
        "%02d–%s".format(from.dayOfMonth, to.dayMonth())
    } else {
        "${from.dayMonth()}–${to.dayMonth()}"
    }
}

/** Lời chào theo giờ trong ngày. */
fun greeting(hour: Int): String = when (hour) {
    in 4..10 -> "Chào buổi sáng"
    in 11..13 -> "Chào buổi trưa"
    in 14..17 -> "Chào buổi chiều"
    else -> "Chào buổi tối"
}

/** Vị trí của hôm nay trong danh sách ngày của thực đơn, không có thì chọn ngày đầu tiên. */
fun indexOfToday(dates: List<LocalDate?>, today: LocalDate = LocalDate.now()): Int =
    dates.indexOf(today).coerceAtLeast(0)
