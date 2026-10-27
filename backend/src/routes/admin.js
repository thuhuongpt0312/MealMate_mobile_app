import { Router } from 'express';
import { FOOD_GROUPS, MEAL_SLOTS } from '../data/seed.js';
import { store } from '../data/store.js';
import { requireAdmin } from '../middleware/auth.js';
import { dailyActivity, overview, popularRecipes } from '../services/adminStats.js';
import { publicUser } from '../services/auth.js';
import { HttpError } from '../utils/http.js';
import { requireFields, requirePositiveNumber } from '../utils/validate.js';

/**
 * API cho trang quản trị (admin dashboard). Tất cả route cần token của tài khoản role = admin.
 * Các route có ghi "PLACEHOLDER" mới trả dữ liệu mẫu / 501 để nhóm làm giao diện trước.
 */
const router = Router();
router.use(requireAdmin);

const RECIPE_STATUSES = ['draft', 'published', 'hidden'];

/** GET placeholder: trả 200 + dữ liệu mẫu để web admin dựng giao diện trước. */
const placeholder = (note, data) => ({ placeholder: true, note, data });

/** POST/PUT placeholder: chưa làm, trả 501 kèm mô tả body dự kiến. */
const notImplemented = (feature, expectedBody) => (req, res) => {
  res.status(501).json({ placeholder: true, error: `${feature} chưa được hỗ trợ`, expectedBody });
};

function paginate(list, query) {
  const page = Math.max(1, Number.parseInt(query.page, 10) || 1);
  const limit = Math.min(100, Math.max(1, Number.parseInt(query.limit, 10) || 20));
  return { items: list.slice((page - 1) * limit, page * limit), page, limit, total: list.length };
}

// ---------- Tổng quan ----------
router.get('/dashboard/overview', (req, res) => {
  res.json(overview());
});

router.get('/dashboard/activity', (req, res) => {
  const days = Math.min(90, Math.max(1, Number.parseInt(req.query.days, 10) || 7));
  res.json({ days, items: dailyActivity(days) });
});

router.get('/dashboard/popular-recipes', (req, res) => {
  const limit = Math.min(50, Math.max(1, Number.parseInt(req.query.limit, 10) || 5));
  res.json({ items: popularRecipes(limit) });
});

// ---------- Người dùng ----------
router.get('/users', (req, res) => {
  const { q, status, role } = req.query;
  const keyword = q?.toString().trim().toLowerCase();
  const users = store.listUsers()
    .filter((u) => (!status || u.status === status)
      && (!role || u.role === role)
      && (!keyword || u.name.toLowerCase().includes(keyword) || u.email.includes(keyword)))
    .map(publicUser);
  res.json(paginate(users, req.query));
});

router.get('/users/:id', (req, res) => {
  const user = store.findUserById(req.params.id);
  if (!user) throw new HttpError(404, 'Không tìm thấy người dùng');
  const plans = store.listMealPlans(user.id);
  res.json({
    ...publicUser(user),
    preferences: store.getPreferences(user.id),
    mealPlanCount: plans.length,
    lastPlanAt: plans.map((p) => p.createdAt).sort().at(-1) ?? null,
  });
});

// Khóa / mở khóa tài khoản, đổi quyền
router.patch('/users/:id', (req, res) => {
  const user = store.findUserById(req.params.id);
  if (!user) throw new HttpError(404, 'Không tìm thấy người dùng');
  const { status, role } = req.body ?? {};
  if (status !== undefined && !['active', 'locked'].includes(status)) throw new HttpError(400, 'status phải là active hoặc locked');
  if (role !== undefined && !['user', 'admin'].includes(role)) throw new HttpError(400, 'role phải là user hoặc admin');
  if (user.id === req.user.id && (status === 'locked' || role === 'user')) {
    throw new HttpError(400, 'Không thể tự khóa hoặc tự hạ quyền tài khoản đang đăng nhập');
  }
  store.updateUser(user.id, { ...(status && { status }), ...(role && { role }) });
  res.json(publicUser(user));
});

// ---------- Công thức món ăn ----------
function validateRecipe(body, { partial }) {
  if (!partial) requireFields(body, ['id', 'name', 'slot', 'minutes', 'pricePerServing', 'calories', 'ingredients']);
  if (body.id !== undefined && !/^[a-z0-9-]+$/.test(body.id)) throw new HttpError(400, 'id chỉ gồm chữ thường, số và dấu -');
  if (body.slot !== undefined && !MEAL_SLOTS[body.slot]) throw new HttpError(400, 'slot không hợp lệ', { allowed: Object.keys(MEAL_SLOTS) });
  for (const field of ['minutes', 'pricePerServing', 'calories']) {
    if (body[field] !== undefined) requirePositiveNumber(body[field], field);
  }
  if (body.status !== undefined && !RECIPE_STATUSES.includes(body.status)) {
    throw new HttpError(400, 'status không hợp lệ', { allowed: RECIPE_STATUSES });
  }
  if (body.ingredients !== undefined) {
    if (!Array.isArray(body.ingredients) || !body.ingredients.length) throw new HttpError(400, 'ingredients phải là mảng có ít nhất 1 phần tử');
    body.ingredients.forEach((ing, i) => {
      requireFields(ing, ['name', 'amount', 'unit', 'group', 'unitPrice']);
      requirePositiveNumber(ing.amount, `ingredients[${i}].amount`);
      if (!FOOD_GROUPS[ing.group]) throw new HttpError(400, `ingredients[${i}].group không hợp lệ`, { allowed: Object.keys(FOOD_GROUPS) });
    });
  }
}

const pickRecipe = (body) => {
  const fields = ['name', 'slot', 'minutes', 'pricePerServing', 'calories', 'ingredients', 'status'];
  return Object.fromEntries(fields.filter((f) => body[f] !== undefined).map((f) => [f, body[f]]));
};

router.get('/recipes', (req, res) => {
  const { status, slot, q } = req.query;
  const keyword = q?.toString().trim().toLowerCase();
  const recipes = store.listRecipes().filter((r) => (!status || r.status === status)
    && (!slot || r.slot === slot)
    && (!keyword || r.name.toLowerCase().includes(keyword)));
  res.json(paginate(recipes, req.query));
});

router.post('/recipes', (req, res) => {
  const body = req.body ?? {};
  validateRecipe(body, { partial: false });
  if (store.findRecipe(body.id)) throw new HttpError(409, 'id món đã tồn tại');
  const recipe = store.addRecipe({ id: body.id, status: 'draft', ...pickRecipe(body) });
  res.status(201).json(recipe);
});

router.put('/recipes/:id', (req, res) => {
  if (!store.findRecipe(req.params.id)) throw new HttpError(404, 'Không tìm thấy món');
  const body = req.body ?? {};
  validateRecipe({ ...body, id: undefined }, { partial: true });
  res.json(store.updateRecipe(req.params.id, pickRecipe(body)));
});

// Duyệt / ẩn món
router.patch('/recipes/:id/status', (req, res) => {
  if (!store.findRecipe(req.params.id)) throw new HttpError(404, 'Không tìm thấy món');
  if (!RECIPE_STATUSES.includes(req.body?.status)) throw new HttpError(400, 'status không hợp lệ', { allowed: RECIPE_STATUSES });
  res.json(store.updateRecipe(req.params.id, { status: req.body.status }));
});

router.delete('/recipes/:id', (req, res) => {
  if (!store.deleteRecipe(req.params.id)) throw new HttpError(404, 'Không tìm thấy món');
  res.status(204).end();
});

// PLACEHOLDER: nhập món hàng loạt từ file CSV/Excel
router.post('/recipes/import', notImplemented('Nhập món từ file', { file: 'multipart/form-data (.csv, .xlsx)' }));

// ---------- Thực đơn ----------
router.get('/meal-plans', (req, res) => {
  const plans = store.listMealPlans()
    .filter((p) => !req.query.userId || p.userId === req.query.userId)
    .sort((a, b) => b.createdAt.localeCompare(a.createdAt))
    .map(({ days, ...summary }) => ({ ...summary, userName: store.findUserById(summary.userId)?.name ?? null }));
  res.json(paginate(plans, req.query));
});

// ---------- Báo cáo (PLACEHOLDER) ----------
router.get('/reports/cost', (req, res) => {
  const period = req.query.period ?? 'month';
  res.json(placeholder('Báo cáo chi phí theo kỳ – sẽ tính từ lịch sử mua sắm khi có dữ liệu thật', {
    period,
    totalSpent: 2_450_000,
    averagePerPlan: 612_500,
    byGroup: Object.keys(FOOD_GROUPS).map((group, i) => ({ group, label: FOOD_GROUPS[group], amount: [820_000, 910_000, 310_000, 290_000, 120_000][i] })),
  }));
});

router.get('/reports/retention', (req, res) => {
  res.json(placeholder('Tỉ lệ người dùng quay lại – cần lưu lịch sử đăng nhập', {
    day1: 0.62,
    day7: 0.41,
    day30: 0.23,
  }));
});

router.get('/reports/export', notImplemented('Xuất báo cáo', { query: 'type=cost|users&format=csv|xlsx' }));

// ---------- Thông báo hàng loạt (PLACEHOLDER) ----------
router.get('/notifications', (req, res) => {
  res.json(placeholder('Lịch sử thông báo đã gửi – cần tích hợp Firebase Cloud Messaging', [
    { id: 'demo-1', title: 'Đã đến giờ đi chợ!', target: 'all', sentAt: '2026-10-20T08:00:00.000Z', delivered: 128 },
    { id: 'demo-2', title: 'Món mới: Canh chua cá lóc', target: 'all', sentAt: '2026-10-24T17:30:00.000Z', delivered: 131 },
  ]));
});

router.post('/notifications', notImplemented('Gửi thông báo hàng loạt', {
  title: 'string',
  body: 'string',
  target: 'all | userIds[]',
  scheduledAt: 'ISO date (tùy chọn)',
}));

// ---------- Cài đặt hệ thống (PLACEHOLDER) ----------
router.get('/settings', (req, res) => {
  res.json(placeholder('Cài đặt hệ thống – chưa lưu được, đang trả giá trị mặc định', {
    defaultWeeklyBudget: 700_000,
    maxServings: 10,
    aiProvider: 'mock',
    maintenanceMode: false,
  }));
});

router.put('/settings', notImplemented('Cập nhật cài đặt hệ thống', {
  defaultWeeklyBudget: 'number',
  maxServings: 'number',
  aiProvider: 'mock | openai | gemini',
  maintenanceMode: 'boolean',
}));

// ---------- Nhật ký thao tác (PLACEHOLDER) ----------
router.get('/audit-logs', (req, res) => {
  res.json(placeholder('Nhật ký thao tác của admin – chưa ghi log', []));
});

export default router;
