package vn.edu.uth.mealmate.util

import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.roundToLong

/** 650000 -> "650.000đ" */
fun formatVnd(amount: Long): String {
    val digits = abs(amount).toString().reversed().chunked(3).joinToString(".").reversed()
    return (if (amount < 0) "-" else "") + digits + "đ"
}

/** Số lượng nguyên liệu kiểu Việt Nam: 600.0 -> "600", 1.25 -> "1,25", 0.5 -> "0,5". Làm tròn 2 chữ số. */
fun formatAmount(amount: Double): String {
    val hundredths = (amount * 100).roundToLong()
    val whole = hundredths / 100
    val fraction = abs(hundredths % 100)
    if (fraction == 0L) return whole.toString()
    val sign = if (hundredths < 0 && whole == 0L) "-" else ""
    return sign + whole + "," + fraction.toString().padStart(2, '0').trimEnd('0')
}

/** Đọc số lượng người dùng nhập, chấp nhận cả dấu phẩy: "1,5" -> 1.5. Không phải số thì trả null. */
fun parseAmount(text: String): Double? = text.trim().replace(',', '.').toDoubleOrNull()?.takeIf { it.isFinite() }

/** "600 g", "1,5 túi" */
fun formatQuantity(amount: Double, unit: String): String = "${formatAmount(amount)} ${unit.trim()}".trim()

/** Phần trăm đã dùng so với ngân sách, 0 nếu chưa đặt ngân sách. */
fun budgetPercent(spent: Long, budget: Long?): Int =
    if (budget == null || budget <= 0) 0 else (spent * 100.0 / budget).roundToInt()
