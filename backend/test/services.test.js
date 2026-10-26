import assert from 'node:assert/strict';
import { describe, it } from 'node:test';
import { seedRecipes } from '../src/data/seed.js';
import { DAYS_PER_PLAN, generateWeeklyPlan } from '../src/services/mealPlanner.js';
import { buildShoppingList } from '../src/services/shoppingListBuilder.js';

// Giống app/src/test/java/vn/edu/uth/mealmate/ShoppingListBuilderTest.kt
describe('buildShoppingList', () => {
  const rice = { name: 'Gạo thơm', amount: 100, unit: 'g', group: 'PANTRY', unitPrice: 25 };
  const meal = (id) => ({ id, name: 'Cơm', slot: 'DINNER', ingredients: [rice] });

  it('gộp nguyên liệu trùng qua các bữa', () => {
    const list = buildShoppingList([meal('a'), meal('b')], 3, []);
    assert.equal(list.length, 1);
    assert.equal(list[0].amount, 600);
    assert.equal(list[0].cost, 15_000);
  });

  it('bỏ nguyên liệu đã đủ trong tủ', () => {
    assert.deepEqual(buildShoppingList([meal('a')], 3, [{ name: 'Gạo thơm', amount: 1000, unit: 'g' }]), []);
  });

  it('chỉ mua phần còn thiếu', () => {
    const list = buildShoppingList([meal('a')], 3, [{ name: 'Gạo thơm', amount: 100, unit: 'g' }]);
    assert.equal(list[0].amount, 200);
  });

  it('so khớp tên và đơn vị không phân biệt hoa thường, khoảng trắng', () => {
    const list = buildShoppingList([meal('a')], 1, [{ name: ' gạo THƠM ', amount: 100, unit: 'G' }]);
    assert.deepEqual(list, []);
  });

  it('khác đơn vị thì không trừ', () => {
    const list = buildShoppingList([meal('a')], 1, [{ name: 'Gạo thơm', amount: 1, unit: 'kg' }]);
    assert.equal(list[0].amount, 100);
  });
});

describe('generateWeeklyPlan', () => {
  it('tạo đủ 7 ngày, mỗi ngày một món cho mỗi bữa', () => {
    const plan = generateWeeklyPlan({ recipes: seedRecipes(), servings: 2, startDate: '2026-10-26' });
    assert.equal(plan.days.length, DAYS_PER_PLAN);
    assert.equal(plan.startDate, '2026-10-26');
    assert.equal(plan.endDate, '2026-11-01');
    assert.ok(plan.days.every((d) => d.meals.length === 4));
    // (22k + 35k + 42k + 18k) * 2 người * 7 ngày
    assert.equal(plan.totalCost, 117_000 * 2 * 7);
  });

  it('bỏ món chứa nguyên liệu cần tránh', () => {
    const plan = generateWeeklyPlan({ recipes: seedRecipes(), servings: 1, avoid: ['thịt heo'], startDate: '2026-10-26' });
    assert.ok(plan.days.every((d) => !d.meals.includes('bun-thit-nuong')));
    assert.equal(plan.days[0].meals.length, 3);
  });

  it('bỏ món chưa được duyệt', () => {
    const recipes = seedRecipes().map((r) => (r.id === 'com-ga-gung' ? { ...r, status: 'draft' } : r));
    const plan = generateWeeklyPlan({ recipes, servings: 1, startDate: '2026-10-26' });
    assert.ok(plan.days.every((d) => !d.meals.includes('com-ga-gung')));
  });
});
