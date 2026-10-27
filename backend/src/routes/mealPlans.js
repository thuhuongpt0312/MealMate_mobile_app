import { Router } from 'express';
import { FOOD_GROUPS } from '../data/seed.js';
import { newId, store } from '../data/store.js';
import { requireAuth } from '../middleware/auth.js';
import { generateWeeklyPlan } from '../services/mealPlanner.js';
import { buildShoppingList } from '../services/shoppingListBuilder.js';
import { HttpError } from '../utils/http.js';
import { requireFields, requirePositiveNumber } from '../utils/validate.js';

const router = Router();
router.use(requireAuth);

function findOwnPlan(req) {
  const plan = store.findMealPlan(req.params.id);
  if (!plan || plan.userId !== req.user.id) throw new HttpError(404, 'Không tìm thấy thực đơn');
  return plan;
}

function findShoppingItem(req) {
  const plan = findOwnPlan(req);
  const items = store.getShoppingList(plan.id) ?? [];
  const item = items.find((i) => i.id === req.params.itemId);
  if (!item) throw new HttpError(404, 'Không tìm thấy món trong danh sách mua sắm');
  return { plan, items, item };
}

const shoppingSummary = (items) => ({
  items,
  total: items.reduce((sum, i) => sum + i.cost, 0),
  remaining: items.filter((i) => !i.bought).reduce((sum, i) => sum + i.cost, 0),
});

// S05 → S06 → S07 · Tạo thực đơn tuần và danh sách mua sắm đi kèm
router.post('/', (req, res) => {
  const prefs = store.getPreferences(req.user.id);
  const servings = req.body?.servings ?? prefs.servings;
  if (!Number.isInteger(servings) || servings < 1 || servings > 10) throw new HttpError(400, 'Số người phải từ 1 đến 10');
  const startDate = req.body?.startDate ?? new Date().toISOString().slice(0, 10);
  if (!/^\d{4}-\d{2}-\d{2}$/.test(startDate) || Number.isNaN(Date.parse(startDate))) {
    throw new HttpError(400, 'startDate phải có dạng YYYY-MM-DD');
  }

  const generated = generateWeeklyPlan({ recipes: store.listRecipes(), servings, avoid: prefs.avoid, startDate });
  if (!generated.days[0].meals.length) throw new HttpError(422, 'Không còn món phù hợp với danh sách món cần tránh');

  const plan = store.addMealPlan({
    id: newId(),
    userId: req.user.id,
    servings,
    ...generated,
    budget: prefs.weeklyBudget,
    createdAt: new Date().toISOString(),
  });

  const meals = plan.days.flatMap((d) => d.meals).map((id) => store.findRecipe(id));
  const items = buildShoppingList(meals, servings, store.getPantry(req.user.id)).map((i) => ({ id: newId(), ...i }));
  store.setShoppingList(plan.id, items);

  res.status(201).json(plan);
});

// S12b · Lịch sử thực đơn, mới nhất trước
router.get('/', (req, res) => {
  const plans = [...store.listMealPlans(req.user.id)].sort((a, b) => b.createdAt.localeCompare(a.createdAt));
  res.json({ items: plans, total: plans.length });
});

router.get('/:id', (req, res) => {
  const plan = findOwnPlan(req);
  const recipes = Object.fromEntries(plan.days.flatMap((d) => d.meals).map((id) => [id, store.findRecipe(id)]));
  res.json({ ...plan, recipes });
});

// S09 · Danh sách mua sắm
router.get('/:id/shopping-list', (req, res) => {
  const plan = findOwnPlan(req);
  res.json(shoppingSummary(store.getShoppingList(plan.id) ?? []));
});

router.post('/:id/shopping-list', (req, res) => {
  const plan = findOwnPlan(req);
  requireFields(req.body, ['name', 'amount', 'unit']);
  requirePositiveNumber(req.body.amount, 'amount');
  const group = req.body.group ?? 'OTHER';
  if (!FOOD_GROUPS[group]) throw new HttpError(400, 'group không hợp lệ', { allowed: Object.keys(FOOD_GROUPS) });

  const item = {
    id: newId(),
    name: String(req.body.name).trim(),
    amount: req.body.amount,
    unit: String(req.body.unit).trim(),
    group,
    cost: Math.max(0, Math.round(Number(req.body.cost ?? 0))),
    bought: false,
  };
  store.setShoppingList(plan.id, [...(store.getShoppingList(plan.id) ?? []), item]);
  res.status(201).json(item);
});

// Tick "đã mua"
router.patch('/:id/shopping-list/:itemId', (req, res) => {
  const { item } = findShoppingItem(req);
  if (typeof req.body?.bought !== 'boolean') throw new HttpError(400, 'bought phải là true/false');
  item.bought = req.body.bought;
  res.json(item);
});

router.delete('/:id/shopping-list/:itemId', (req, res) => {
  const { plan, items, item } = findShoppingItem(req);
  store.setShoppingList(plan.id, items.filter((i) => i !== item));
  res.status(204).end();
});

export default router;
