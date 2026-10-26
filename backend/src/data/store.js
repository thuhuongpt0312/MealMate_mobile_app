import { randomUUID } from 'node:crypto';
import { defaultPreferences, seedPantry, seedRecipes } from './seed.js';

/**
 * Kho dữ liệu tạm trong bộ nhớ (mất khi tắt server).
 * Các route chỉ gọi qua object `store` nên sau này thay bằng database (SQLite, MongoDB...)
 * chỉ cần viết lại file này.
 */
function createState() {
  return {
    users: [],          // { id, name, email, passwordHash, role, status, createdAt }
    sessions: new Map(), // token -> { userId, expiresAt }
    preferences: new Map(), // userId -> Preference
    pantry: new Map(),   // userId -> PantryItem[]
    recipes: seedRecipes(),
    mealPlans: [],       // { id, userId, startDate, endDate, servings, days, totalCost, createdAt }
    shoppingLists: new Map(), // mealPlanId -> ShoppingItem[]
  };
}

let state = createState();

export const newId = () => randomUUID();

export const store = {
  /** Xóa toàn bộ dữ liệu, dùng trong test. */
  reset() {
    state = createState();
  },

  // --- Người dùng ---
  listUsers: () => state.users,
  findUserById: (id) => state.users.find((u) => u.id === id),
  findUserByEmail: (email) => state.users.find((u) => u.email === email.trim().toLowerCase()),
  createUser({ name, email, passwordHash, role = 'user' }) {
    const user = {
      id: newId(),
      name,
      email: email.trim().toLowerCase(),
      passwordHash,
      role,
      status: 'active',
      createdAt: new Date().toISOString(),
    };
    state.users.push(user);
    state.preferences.set(user.id, defaultPreferences());
    state.pantry.set(user.id, seedPantry().map((item) => ({ id: newId(), ...item })));
    return user;
  },
  updateUser(id, patch) {
    const user = state.users.find((u) => u.id === id);
    if (user) Object.assign(user, patch);
    return user;
  },

  // --- Phiên đăng nhập ---
  saveSession: (token, session) => state.sessions.set(token, session),
  getSession: (token) => state.sessions.get(token),
  deleteSession: (token) => state.sessions.delete(token),

  // --- Tùy chọn cá nhân, tủ bếp ---
  getPreferences: (userId) => state.preferences.get(userId) ?? defaultPreferences(),
  setPreferences: (userId, prefs) => state.preferences.set(userId, prefs),
  getPantry: (userId) => state.pantry.get(userId) ?? [],
  setPantry: (userId, items) => state.pantry.set(userId, items),

  // --- Công thức ---
  listRecipes: () => state.recipes,
  findRecipe: (id) => state.recipes.find((r) => r.id === id),
  addRecipe(recipe) {
    state.recipes.push(recipe);
    return recipe;
  },
  updateRecipe(id, patch) {
    const recipe = state.recipes.find((r) => r.id === id);
    if (recipe) Object.assign(recipe, patch);
    return recipe;
  },
  deleteRecipe(id) {
    const before = state.recipes.length;
    state.recipes = state.recipes.filter((r) => r.id !== id);
    return state.recipes.length < before;
  },

  // --- Thực đơn tuần, danh sách mua sắm ---
  listMealPlans: (userId) => (userId ? state.mealPlans.filter((p) => p.userId === userId) : state.mealPlans),
  findMealPlan: (id) => state.mealPlans.find((p) => p.id === id),
  addMealPlan(plan) {
    state.mealPlans.push(plan);
    return plan;
  },
  getShoppingList: (planId) => state.shoppingLists.get(planId),
  setShoppingList: (planId, items) => state.shoppingLists.set(planId, items),
};
