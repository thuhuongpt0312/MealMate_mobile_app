package vn.edu.uth.mealmate.util

import kotlin.math.abs

/** 650000 -> "650.000đ" */
fun formatVnd(amount: Long): String {
    val digits = abs(amount).toString().reversed().chunked(3).joinToString(".").reversed()
    return (if (amount < 0) "-" else "") + digits + "đ"
}
