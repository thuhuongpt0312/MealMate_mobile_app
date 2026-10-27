// web-admin/src/api.js
// Tham khảo từ tài liệu hướng dẫn kết nối API MealMate

export const API_URL = 'http://localhost:3000/api';

export class ApiError extends Error {
  constructor(status, message, details) {
    super(message);
    this.status = status;
    this.details = details;
  }
}

export async function api(path, { method = 'GET', body } = {}) {
  const token = localStorage.getItem('token');
  const res = await fetch(API_URL + path, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token && { Authorization: `Bearer ${token}` }),
    },
    body: body && JSON.stringify(body),
  });

  if (res.status === 204) return null;
  const data = await res.json();
  if (!res.ok) {
    if (res.status === 401 && path !== '/auth/login') {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    throw new ApiError(res.status, data.error, data.details);
  }
  return data;
}

export async function login(email, password) {
  const data = await api('/auth/login', {
    method: 'POST',
    body: { email, password },
  });
  if (data.user.role !== 'admin') {
    throw new ApiError(403, 'Tài khoản không có quyền quản trị');
  }
  localStorage.setItem('token', data.token);
  return data.user;
}

export async function logout() {
  await api('/auth/logout', { method: 'POST' }).catch(() => {});
  localStorage.removeItem('token');
}

export const admin = {
  overview: () => api('/admin/dashboard/overview'),
  activity: (days = 7) => api(`/admin/dashboard/activity?days=${days}`),
  popularRecipes: (limit = 5) => api(`/admin/dashboard/popular-recipes?limit=${limit}`),
  users: (params = {}) => api(`/admin/users?${new URLSearchParams(params)}`),
  user: (id) => api(`/admin/users/${id}`),
  lockUser: (id) => api(`/admin/users/${id}`, { method: 'PATCH', body: { status: 'locked' } }),
  unlockUser: (id) => api(`/admin/users/${id}`, { method: 'PATCH', body: { status: 'active' } }),
  recipes: (params = {}) => api(`/admin/recipes?${new URLSearchParams(params)}`),
  createRecipe: (recipe) => api('/admin/recipes', { method: 'POST', body: recipe }),
  updateRecipe: (id, patch) => api(`/admin/recipes/${id}`, { method: 'PUT', body: patch }),
  setRecipeStatus: (id, status) => api(`/admin/recipes/${id}/status`, { method: 'PATCH', body: { status } }),
  deleteRecipe: (id) => api(`/admin/recipes/${id}`, { method: 'DELETE' }),
  mealPlans: (params = {}) => api(`/admin/meal-plans?${new URLSearchParams(params)}`),
  costReport: (period = 'month') => api(`/admin/reports/cost?period=${period}`),
  settings: () => api('/admin/settings'),
};
