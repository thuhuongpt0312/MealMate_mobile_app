import { resolveToken } from '../services/auth.js';
import { HttpError } from '../utils/http.js';

/** Đọc header "Authorization: Bearer <token>" và gắn req.user. */
export function requireAuth(req, res, next) {
  const [scheme, token] = (req.get('Authorization') ?? '').split(' ');
  const user = scheme === 'Bearer' && token ? resolveToken(token) : null;
  if (!user) return next(new HttpError(401, 'Chưa đăng nhập hoặc phiên đã hết hạn'));
  if (user.status !== 'active') return next(new HttpError(403, 'Tài khoản đã bị khóa'));
  req.user = user;
  req.token = token;
  next();
}

export function requireAdmin(req, res, next) {
  requireAuth(req, res, (err) => {
    if (err) return next(err);
    if (req.user.role !== 'admin') return next(new HttpError(403, 'Chỉ quản trị viên mới được truy cập'));
    next();
  });
}
