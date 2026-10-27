import { HttpError } from './http.js';

/** Báo lỗi 400 nếu body thiếu trường bắt buộc. */
export function requireFields(body, fields) {
  const missing = fields.filter((f) => body?.[f] === undefined || body[f] === null || body[f] === '');
  if (missing.length) throw new HttpError(400, 'Thiếu thông tin bắt buộc', { missing });
}

export function requirePositiveNumber(value, field) {
  if (typeof value !== 'number' || !Number.isFinite(value) || value <= 0) {
    throw new HttpError(400, `${field} phải là số dương`);
  }
}

export const isEmail = (value) => typeof value === 'string' && /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value.trim());
