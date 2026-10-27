import { randomBytes, scryptSync, timingSafeEqual } from 'node:crypto';
import { config } from '../config.js';
import { store } from '../data/store.js';

export function hashPassword(password) {
  const salt = randomBytes(16).toString('hex');
  const hash = scryptSync(password, salt, 64).toString('hex');
  return `${salt}:${hash}`;
}

export function verifyPassword(password, stored) {
  const [salt, hash] = stored.split(':');
  const actual = scryptSync(password, salt, 64);
  return timingSafeEqual(actual, Buffer.from(hash, 'hex'));
}

/** Tạo token ngẫu nhiên lưu trong bộ nhớ. Sau này có thể đổi sang JWT. */
export function issueToken(userId) {
  const token = randomBytes(32).toString('hex');
  const expiresAt = Date.now() + config.tokenTtlHours * 3600 * 1000;
  store.saveSession(token, { userId, expiresAt });
  return { token, expiresAt: new Date(expiresAt).toISOString() };
}

export function resolveToken(token) {
  const session = store.getSession(token);
  if (!session) return null;
  if (session.expiresAt < Date.now()) {
    store.deleteSession(token);
    return null;
  }
  return store.findUserById(session.userId) ?? null;
}

/** Bỏ passwordHash trước khi trả về client. */
export function publicUser(user) {
  const { passwordHash, ...rest } = user;
  return rest;
}

/** Tài khoản admin mặc định để đăng nhập trang quản trị khi chạy local. */
export const DEFAULT_ADMIN = { name: 'Quản trị viên', email: 'admin@mealmate.local', password: 'admin123' };

export function ensureDefaultAdmin() {
  if (store.findUserByEmail(DEFAULT_ADMIN.email)) return;
  store.createUser({
    name: DEFAULT_ADMIN.name,
    email: DEFAULT_ADMIN.email,
    passwordHash: hashPassword(DEFAULT_ADMIN.password),
    role: 'admin',
  });
}
