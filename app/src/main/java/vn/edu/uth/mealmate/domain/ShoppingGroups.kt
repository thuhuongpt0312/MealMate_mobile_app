package vn.edu.uth.mealmate.domain

import vn.edu.uth.mealmate.data.model.FoodGroup

/** Mã nhóm từ API ("VEGETABLES"...) sang [FoodGroup], mã lạ thì xếp vào Khác. */
fun foodGroupOf(code: String): FoodGroup = FoodGroup.entries.find { it.name == code } ?: FoodGroup.OTHER

/**
 * Chia danh sách mua sắm (S09) theo nhóm thực phẩm, thứ tự nhóm như [FoodGroup].
 * Trong mỗi nhóm, món chưa mua đứng trước để dễ tick khi đi chợ. Nhóm rỗng bị bỏ.
 */
fun <T> groupForShopping(items: List<T>, groupCode: (T) -> String, isBought: (T) -> Boolean): List<Pair<FoodGroup, List<T>>> {
    val byGroup = items.groupBy { foodGroupOf(groupCode(it)) }
    return FoodGroup.entries.mapNotNull { group ->
        byGroup[group]?.sortedBy(isBought)?.let { group to it }
    }
}
