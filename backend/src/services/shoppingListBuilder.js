/**
 * Bản JS của app/src/main/java/vn/edu/uth/mealmate/domain/ShoppingListBuilder.kt.
 * Sửa logic ở một bên thì nhớ sửa bên còn lại cho giống.
 *
 * 1) Gộp nguyên liệu trùng tên + đơn vị qua tất cả các bữa.
 * 2) Trừ lượng đang có trong tủ, chỉ giữ phần còn thiếu và tính tiền.
 */
export function buildShoppingList(meals, servings, pantry) {
  const need = new Map();
  for (const meal of meals) {
    for (const ing of meal.ingredients) {
      const key = keyOf(ing.name, ing.unit);
      const total = (need.get(key)?.total ?? 0) + ing.amount * servings;
      need.set(key, { ing, total });
    }
  }

  const have = new Map(pantry.map((p) => [keyOf(p.name, p.unit), p.amount]));
  const result = [];
  for (const [key, { ing, total }] of need) {
    const missing = total - (have.get(key) ?? 0);
    if (missing > 0) {
      result.push({
        name: ing.name,
        amount: missing,
        unit: ing.unit,
        group: ing.group,
        cost: Math.round(missing * ing.unitPrice),
        bought: false,
      });
    }
  }
  return result;
}

const keyOf = (name, unit) => `${name.trim().toLowerCase()}|${unit.trim().toLowerCase()}`;
