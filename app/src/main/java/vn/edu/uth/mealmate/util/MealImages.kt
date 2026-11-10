package vn.edu.uth.mealmate.util

import androidx.annotation.DrawableRes
import vn.edu.uth.mealmate.R

/**
 * Ánh xạ id món ăn và màn hình tới các tài nguyên hình ảnh trong res/drawable
 * Đồng bộ với kiểm kê bản quyền hình ảnh (ImageCreditsScreen.kt) và thiết kế Figma.
 */
object MealImages {
    @DrawableRes
    val MamCom: Int = R.drawable.img_s01_mamcom          // S01 Làm quen / Onboarding

    @DrawableRes
    val ComGaGung: Int = R.drawable.img_s04_comgagung     // S04 Trang chủ / Cơm gà gừng

    @DrawableRes
    val PhoGa: Int = R.drawable.img_s07_phoga             // S07 Thực đơn tuần / Phở gà / Bánh mì

    @DrawableRes
    val BunThitNuong: Int = R.drawable.img_s07_bunthitnuong // S07, S08 Bún thịt nướng

    @DrawableRes
    val ComGiaDinh: Int = R.drawable.img_s12_comgiadinh   // S12 Yêu thích, S12b Lịch sử

    @DrawableRes
    val RauXanh: Int = R.drawable.img_s15_rauxanh         // S10 Tủ bếp, S11 Dinh dưỡng, S15 Thống kê

    /**
     * Lấy drawable món ăn tương ứng theo mealId
     */
    @DrawableRes
    fun forMeal(mealId: String?): Int {
        if (mealId == null) return ComGiaDinh
        val id = mealId.lowercase()
        return when {
            id.contains("com-ga") || id.contains("ga-gung") || id.contains("cơm gà") -> ComGaGung
            id.contains("bun-thit") || id.contains("thit-nuong") || id.contains("bún thịt") -> BunThitNuong
            id.contains("banh-mi") || id.contains("pho-ga") || id.contains("phở") -> PhoGa
            id.contains("sua-chua") || id.contains("trai-cay") -> RauXanh
            else -> ComGiaDinh
        }
    }
}
