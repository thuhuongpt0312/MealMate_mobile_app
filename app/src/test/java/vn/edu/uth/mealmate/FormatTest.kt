package vn.edu.uth.mealmate

import java.time.DayOfWeek
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import vn.edu.uth.mealmate.util.budgetPercent
import vn.edu.uth.mealmate.util.formatAmount
import vn.edu.uth.mealmate.util.formatDateRange
import vn.edu.uth.mealmate.util.formatFullDate
import vn.edu.uth.mealmate.util.formatQuantity
import vn.edu.uth.mealmate.util.formatVnd
import vn.edu.uth.mealmate.util.greeting
import vn.edu.uth.mealmate.util.indexOfToday
import vn.edu.uth.mealmate.util.parseAmount
import vn.edu.uth.mealmate.util.parseApiDate
import vn.edu.uth.mealmate.util.shortLabel

class FormatTest {

    @Test
    fun tienCoDauChamPhanCachHangNghin() {
        assertEquals("650.000đ", formatVnd(650_000))
        assertEquals("0đ", formatVnd(0))
        assertEquals("-42.000đ", formatVnd(-42_000))
    }

    @Test
    fun soLuongDungDauPhayThapPhan() {
        assertEquals("600", formatAmount(600.0))
        assertEquals("1,5", formatAmount(1.5))
        assertEquals("0,25", formatAmount(0.25))
        assertEquals("0,33", formatAmount(1.0 / 3))
        assertEquals("-0,5", formatAmount(-0.5))
        assertEquals("1,5 túi", formatQuantity(1.5, " túi "))
    }

    @Test
    fun docSoLuongNguoiDungNhap() {
        assertEquals(1.5, parseAmount("1,5")!!, 0.0)
        assertEquals(2.0, parseAmount(" 2 ")!!, 0.0)
        assertEquals(0.25, parseAmount("0.25")!!, 0.0)
        assertNull(parseAmount("abc"))
        assertNull(parseAmount(""))
        assertNull(parseAmount("NaN"))
    }

    @Test
    fun phanTramNganSach() {
        assertEquals(93, budgetPercent(650_000, 700_000))
        assertEquals(0, budgetPercent(650_000, null))
        assertEquals(0, budgetPercent(650_000, 0))
        assertEquals(120, budgetPercent(840_000, 700_000))
    }

    @Test
    fun ngayThangTiengViet() {
        assertEquals("11–17/03", formatDateRange("2026-03-11", "2026-03-17"))
        assertEquals("26/10–01/11", formatDateRange("2026-10-26", "2026-11-01"))
        assertEquals("abc–2026-03-17", formatDateRange("abc", "2026-03-17"))
        assertNull(parseApiDate("12/03/2026"))
        assertEquals("Thứ Ba, 10/03", formatFullDate(LocalDate.of(2026, 3, 10)))
        assertEquals("T2", DayOfWeek.MONDAY.shortLabel())
        assertEquals("CN", DayOfWeek.SUNDAY.shortLabel())
    }

    @Test
    fun chonHomNayTrongThucDon() {
        val start = LocalDate.of(2026, 10, 26)
        val days = (0L until 7L).map { start.plusDays(it) }
        assertEquals(2, indexOfToday(days, LocalDate.of(2026, 10, 28)))
        assertEquals(0, indexOfToday(days, LocalDate.of(2026, 12, 1)))
        assertEquals(0, indexOfToday(emptyList(), LocalDate.of(2026, 10, 28)))
    }

    @Test
    fun loiChaoTheoGio() {
        assertEquals("Chào buổi sáng", greeting(7))
        assertEquals("Chào buổi trưa", greeting(12))
        assertEquals("Chào buổi chiều", greeting(16))
        assertEquals("Chào buổi tối", greeting(21))
        assertEquals("Chào buổi tối", greeting(2))
    }
}
