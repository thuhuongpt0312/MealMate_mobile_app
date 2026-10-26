import { Router } from 'express';
import { newId, store } from '../data/store.js';
import { requireAuth } from '../middleware/auth.js';
import { HttpError } from '../utils/http.js';
import { requireFields, requirePositiveNumber } from '../utils/validate.js';

/** Dữ liệu riêng của người đang đăng nhập: tùy chọn (S03, S16) và tủ bếp (S10). */
const router = Router();
router.use(requireAuth);

// --- Tùy chọn cá nhân ---
router.get('/preferences', (req, res) => {
  res.json(store.getPreferences(req.user.id));
});

router.put('/preferences', (req, res) => {
  const current = store.getPreferences(req.user.id);
  const { servings, goal, weeklyBudget, tastes, avoid } = req.body ?? {};
  if (servings !== undefined && (!Number.isInteger(servings) || servings < 1 || servings > 10)) {
    throw new HttpError(400, 'Số người phải từ 1 đến 10');
  }
  if (weeklyBudget !== undefined) requirePositiveNumber(weeklyBudget, 'weeklyBudget');
  if (tastes !== undefined && !Array.isArray(tastes)) throw new HttpError(400, 'tastes phải là mảng');
  if (avoid !== undefined && !Array.isArray(avoid)) throw new HttpError(400, 'avoid phải là mảng');

  const next = {
    ...current,
    ...(servings !== undefined && { servings }),
    ...(goal !== undefined && { goal: String(goal) }),
    ...(weeklyBudget !== undefined && { weeklyBudget }),
    ...(tastes !== undefined && { tastes: tastes.map(String) }),
    ...(avoid !== undefined && { avoid: avoid.map(String) }),
  };
  store.setPreferences(req.user.id, next);
  res.json(next);
});

// --- Tủ bếp ---
router.get('/pantry', (req, res) => {
  const items = store.getPantry(req.user.id);
  res.json({ items, total: items.length });
});

router.post('/pantry', (req, res) => {
  requireFields(req.body, ['name', 'amount', 'unit']);
  requirePositiveNumber(req.body.amount, 'amount');
  const item = { id: newId(), name: String(req.body.name).trim(), amount: req.body.amount, unit: String(req.body.unit).trim() };
  store.setPantry(req.user.id, [...store.getPantry(req.user.id), item]);
  res.status(201).json(item);
});

router.patch('/pantry/:id', (req, res) => {
  const items = store.getPantry(req.user.id);
  const item = items.find((i) => i.id === req.params.id);
  if (!item) throw new HttpError(404, 'Không tìm thấy nguyên liệu trong tủ');
  if (req.body?.amount !== undefined) requirePositiveNumber(req.body.amount, 'amount');
  Object.assign(item, {
    ...(req.body?.name !== undefined && { name: String(req.body.name).trim() }),
    ...(req.body?.amount !== undefined && { amount: req.body.amount }),
    ...(req.body?.unit !== undefined && { unit: String(req.body.unit).trim() }),
  });
  res.json(item);
});

router.delete('/pantry/:id', (req, res) => {
  const items = store.getPantry(req.user.id);
  if (!items.some((i) => i.id === req.params.id)) throw new HttpError(404, 'Không tìm thấy nguyên liệu trong tủ');
  store.setPantry(req.user.id, items.filter((i) => i.id !== req.params.id));
  res.status(204).end();
});

export default router;
