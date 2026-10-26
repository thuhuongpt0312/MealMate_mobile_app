import { MEAL_SLOTS } from '../data/seed.js';

export const DAYS_PER_PLAN = 7;

/**
 * Tạo thực đơn 7 ngày (S05–S07). Đây là bản giả lập "AI": xoay vòng các món theo từng bữa,
 * bỏ món chứa nguyên liệu người dùng muốn tránh. Khi có API AI thật chỉ cần thay hàm này.
 */
export function generateWeeklyPlan({ recipes, servings, avoid = [], startDate }) {
  const avoidWords = avoid.map((a) => a.trim().toLowerCase()).filter(Boolean);
  const allowed = recipes.filter((r) => r.status === 'published' && !containsAvoided(r, avoidWords));

  const bySlot = Object.keys(MEAL_SLOTS).map((slot) => allowed.filter((r) => r.slot === slot));
  const start = new Date(`${startDate}T00:00:00Z`);

  const days = [];
  for (let i = 0; i < DAYS_PER_PLAN; i++) {
    const date = new Date(start.getTime() + i * 86_400_000).toISOString().slice(0, 10);
    const meals = bySlot.filter((list) => list.length).map((list) => list[i % list.length].id);
    days.push({ date, meals });
  }

  const priceOf = new Map(allowed.map((r) => [r.id, r.pricePerServing]));
  const totalCost = days.flatMap((d) => d.meals).reduce((sum, id) => sum + priceOf.get(id) * servings, 0);
  return { startDate: days[0].date, endDate: days[days.length - 1].date, days, totalCost };
}

function containsAvoided(recipe, avoidWords) {
  if (!avoidWords.length) return false;
  const texts = [recipe.name, ...recipe.ingredients.map((i) => i.name)].map((t) => t.toLowerCase());
  return avoidWords.some((w) => texts.some((t) => t.includes(w)));
}
