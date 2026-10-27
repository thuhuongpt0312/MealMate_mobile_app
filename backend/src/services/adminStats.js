import { store } from '../data/store.js';

const DAY_MS = 86_400_000;
const today = () => new Date().toISOString().slice(0, 10);
const dayOf = (iso) => iso.slice(0, 10);

/** Số liệu cho các thẻ tổng quan trên trang chủ admin. */
export function overview() {
  const users = store.listUsers().filter((u) => u.role === 'user');
  const recipes = store.listRecipes();
  const plans = store.listMealPlans();
  const weekAgo = new Date(Date.now() - 7 * DAY_MS).toISOString();

  const countBy = (list, key) => list.reduce((acc, item) => ({ ...acc, [item[key]]: (acc[item[key]] ?? 0) + 1 }), {});
  const totalCost = plans.reduce((sum, p) => sum + p.totalCost, 0);

  return {
    users: {
      total: users.length,
      active: users.filter((u) => u.status === 'active').length,
      locked: users.filter((u) => u.status === 'locked').length,
      newLast7Days: users.filter((u) => u.createdAt >= weekAgo).length,
    },
    recipes: { total: recipes.length, byStatus: countBy(recipes, 'status') },
    mealPlans: {
      total: plans.length,
      createdToday: plans.filter((p) => dayOf(p.createdAt) === today()).length,
      averageCost: plans.length ? Math.round(totalCost / plans.length) : 0,
      overBudget: plans.filter((p) => p.budget && p.totalCost > p.budget).length,
    },
  };
}

/** Số thực đơn và người dùng mới theo từng ngày, dùng cho biểu đồ cột. */
export function dailyActivity(days) {
  const start = Date.now() - (days - 1) * DAY_MS;
  const buckets = Array.from({ length: days }, (_, i) => ({
    date: new Date(start + i * DAY_MS).toISOString().slice(0, 10),
    mealPlans: 0,
    newUsers: 0,
  }));
  const byDate = new Map(buckets.map((b) => [b.date, b]));
  for (const p of store.listMealPlans()) {
    const bucket = byDate.get(dayOf(p.createdAt));
    if (bucket) bucket.mealPlans++;
  }
  for (const u of store.listUsers()) {
    const bucket = byDate.get(dayOf(u.createdAt));
    if (bucket && u.role === 'user') bucket.newUsers++;
  }
  return buckets;
}

/** Món xuất hiện nhiều nhất trong các thực đơn đã tạo. */
export function popularRecipes(limit) {
  const counts = new Map();
  for (const plan of store.listMealPlans()) {
    for (const id of plan.days.flatMap((d) => d.meals)) counts.set(id, (counts.get(id) ?? 0) + 1);
  }
  return [...counts.entries()]
    .sort((a, b) => b[1] - a[1])
    .slice(0, limit)
    .map(([id, timesPlanned]) => ({ id, name: store.findRecipe(id)?.name ?? '(đã xóa)', timesPlanned }));
}
