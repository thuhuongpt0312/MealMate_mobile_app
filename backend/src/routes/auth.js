import { Router } from 'express';
import { store } from '../data/store.js';
import { requireAuth } from '../middleware/auth.js';
import { hashPassword, issueToken, publicUser, verifyPassword } from '../services/auth.js';
import { HttpError } from '../utils/http.js';
import { isEmail, requireFields } from '../utils/validate.js';

const router = Router();

// S02 · Tạo tài khoản
router.post('/register', (req, res) => {
  requireFields(req.body, ['name', 'email', 'password']);
  const { name, email, password } = req.body;
  if (!isEmail(email)) throw new HttpError(400, 'Email không hợp lệ');
  if (String(password).length < 6) throw new HttpError(400, 'Mật khẩu tối thiểu 6 ký tự');
  if (store.findUserByEmail(email)) throw new HttpError(409, 'Email đã được sử dụng');

  const user = store.createUser({ name: String(name).trim(), email, passwordHash: hashPassword(String(password)) });
  res.status(201).json({ user: publicUser(user), ...issueToken(user.id) });
});

router.post('/login', (req, res) => {
  requireFields(req.body, ['email', 'password']);
  const user = store.findUserByEmail(String(req.body.email));
  if (!user || !verifyPassword(String(req.body.password), user.passwordHash)) {
    throw new HttpError(401, 'Email hoặc mật khẩu không đúng');
  }
  if (user.status !== 'active') throw new HttpError(403, 'Tài khoản đã bị khóa');
  res.json({ user: publicUser(user), ...issueToken(user.id) });
});

router.post('/logout', requireAuth, (req, res) => {
  store.deleteSession(req.token);
  res.status(204).end();
});

router.get('/me', requireAuth, (req, res) => {
  res.json({ user: publicUser(req.user) });
});

export default router;
