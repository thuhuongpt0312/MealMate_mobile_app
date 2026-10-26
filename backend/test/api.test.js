import assert from 'node:assert/strict';
import { after, before, beforeEach, describe, it } from 'node:test';
import { createApp } from '../src/app.js';
import { store } from '../src/data/store.js';
import { DEFAULT_ADMIN, ensureDefaultAdmin } from '../src/services/auth.js';

let server;
let baseUrl;

before(() => {
  server = createApp().listen(0);
  baseUrl = `http://localhost:${server.address().port}/api`;
});

after(() => server.close());

beforeEach(() => {
  store.reset();
  ensureDefaultAdmin();
});

async function call(method, path, { body, token } = {}) {
  const res = await fetch(baseUrl + path, {
    method,
    headers: { 'Content-Type': 'application/json', ...(token && { Authorization: `Bearer ${token}` }) },
    body: body && JSON.stringify(body),
  });
  return { status: res.status, body: res.status === 204 ? null : await res.json() };
}

async function register(email = 'lan@example.com') {
  const res = await call('POST', '/auth/register', { body: { name: 'Lan', email, password: '123456' } });
  return res.body;
}

async function loginAdmin() {
  const res = await call('POST', '/auth/login', { body: { email: DEFAULT_ADMIN.email, password: DEFAULT_ADMIN.password } });
  return res.body.token;
}

describe('auth', () => {
  it('đăng ký, đăng nhập, xem thông tin, đăng xuất', async () => {
    const reg = await register();
    assert.equal(reg.user.email, 'lan@example.com');
    assert.equal(reg.user.passwordHash, undefined);

    const login = await call('POST', '/auth/login', { body: { email: 'LAN@example.com', password: '123456' } });
    assert.equal(login.status, 200);

    const me = await call('GET', '/auth/me', { token: login.body.token });
    assert.equal(me.body.user.name, 'Lan');

    assert.equal((await call('POST', '/auth/logout', { token: login.body.token })).status, 204);
    assert.equal((await call('GET', '/auth/me', { token: login.body.token })).status, 401);
  });

  it('từ chối email trùng, mật khẩu sai, thiếu trường', async () => {
    await register();
    assert.equal((await call('POST', '/auth/register', { body: { name: 'X', email: 'lan@example.com', password: '123456' } })).status, 409);
    assert.equal((await call('POST', '/auth/login', { body: { email: 'lan@example.com', password: 'sai' } })).status, 401);
    const missing = await call('POST', '/auth/register', { body: { email: 'a@b.com' } });
    assert.equal(missing.status, 400);
    assert.deepEqual(missing.body.details.missing, ['name', 'password']);
  });
});

describe('dữ liệu người dùng', () => {
  it('cập nhật tùy chọn và tủ bếp', async () => {
    const { token } = await register();
    const prefs = await call('PUT', '/me/preferences', { token, body: { servings: 3, avoid: ['Tôm'] } });
    assert.equal(prefs.body.servings, 3);
    assert.deepEqual(prefs.body.avoid, ['Tôm']);
    assert.equal((await call('PUT', '/me/preferences', { token, body: { servings: 11 } })).status, 400);

    const added = await call('POST', '/me/pantry', { token, body: { name: 'Hành tím', amount: 3, unit: 'củ' } });
    assert.equal(added.status, 201);
    assert.equal((await call('PATCH', `/me/pantry/${added.body.id}`, { token, body: { amount: 1 } })).body.amount, 1);
    assert.equal((await call('DELETE', `/me/pantry/${added.body.id}`, { token })).status, 204);
    assert.equal((await call('GET', '/me/pantry', { token })).body.total, 6);
  });

  it('cần đăng nhập', async () => {
    assert.equal((await call('GET', '/me/pantry')).status, 401);
  });
});

describe('thực đơn và mua sắm', () => {
  it('tạo thực đơn, sinh danh sách mua sắm đã trừ tủ bếp, tick đã mua', async () => {
    const { token } = await register();
    const plan = await call('POST', '/meal-plans', { token, body: { startDate: '2026-10-26' } });
    assert.equal(plan.status, 201);
    assert.equal(plan.body.days.length, 7);

    const list = await call('GET', `/meal-plans/${plan.body.id}/shopping-list`, { token });
    const eggs = list.body.items.find((i) => i.name === 'Trứng');
    // 2 quả * 2 người * 7 ngày - 8 quả trong tủ
    assert.equal(eggs.amount, 20);
    assert.equal(list.body.remaining, list.body.total);

    const ticked = await call('PATCH', `/meal-plans/${plan.body.id}/shopping-list/${eggs.id}`, { token, body: { bought: true } });
    assert.equal(ticked.body.bought, true);
    const updated = await call('GET', `/meal-plans/${plan.body.id}/shopping-list`, { token });
    assert.equal(updated.body.remaining, updated.body.total - eggs.cost);

    const history = await call('GET', '/meal-plans', { token });
    assert.equal(history.body.total, 1);
  });

  it('không xem được thực đơn của người khác', async () => {
    const a = await register('a@example.com');
    const b = await register('b@example.com');
    const plan = await call('POST', '/meal-plans', { token: a.token, body: {} });
    assert.equal((await call('GET', `/meal-plans/${plan.body.id}`, { token: b.token })).status, 404);
  });
});

describe('admin', () => {
  it('chặn người dùng thường', async () => {
    const { token } = await register();
    assert.equal((await call('GET', '/admin/dashboard/overview', { token })).status, 403);
    assert.equal((await call('GET', '/admin/dashboard/overview')).status, 401);
  });

  it('tổng quan đếm đúng người dùng và thực đơn', async () => {
    const { token } = await register();
    await call('POST', '/meal-plans', { token, body: {} });
    const admin = await loginAdmin();
    const overview = await call('GET', '/admin/dashboard/overview', { token: admin });
    assert.equal(overview.body.users.total, 1);
    assert.equal(overview.body.mealPlans.total, 1);
    assert.equal(overview.body.recipes.byStatus.published, 4);
  });

  it('khóa người dùng thì không đăng nhập được, không tự khóa mình', async () => {
    const { user } = await register();
    const admin = await loginAdmin();
    assert.equal((await call('PATCH', `/admin/users/${user.id}`, { token: admin, body: { status: 'locked' } })).body.status, 'locked');
    assert.equal((await call('POST', '/auth/login', { body: { email: user.email, password: '123456' } })).status, 403);

    const me = (await call('GET', '/auth/me', { token: admin })).body.user;
    assert.equal((await call('PATCH', `/admin/users/${me.id}`, { token: admin, body: { status: 'locked' } })).status, 400);
  });

  it('tạo món ở trạng thái nháp, duyệt rồi mới hiện cho app', async () => {
    const admin = await loginAdmin();
    const recipe = {
      id: 'canh-chua-ca', name: 'Canh chua cá lóc', slot: 'LUNCH', minutes: 30, pricePerServing: 38_000, calories: 320,
      ingredients: [{ name: 'Cá lóc', amount: 150, unit: 'g', group: 'PROTEIN', unitPrice: 120 }],
    };
    assert.equal((await call('POST', '/admin/recipes', { token: admin, body: recipe })).body.status, 'draft');
    assert.equal((await call('POST', '/admin/recipes', { token: admin, body: recipe })).status, 409);
    assert.equal((await call('GET', '/recipes/canh-chua-ca')).status, 404);

    await call('PATCH', '/admin/recipes/canh-chua-ca/status', { token: admin, body: { status: 'published' } });
    assert.equal((await call('GET', '/recipes/canh-chua-ca')).status, 200);

    assert.equal((await call('DELETE', '/admin/recipes/canh-chua-ca', { token: admin })).status, 204);
  });

  it('route placeholder trả dữ liệu mẫu hoặc 501', async () => {
    const admin = await loginAdmin();
    const settings = await call('GET', '/admin/settings', { token: admin });
    assert.equal(settings.body.placeholder, true);
    assert.equal((await call('POST', '/admin/notifications', { token: admin, body: {} })).status, 501);
  });
});
