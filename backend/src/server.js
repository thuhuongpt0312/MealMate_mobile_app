import { createApp } from './app.js';
import { config } from './config.js';
import { DEFAULT_ADMIN, ensureDefaultAdmin } from './services/auth.js';

ensureDefaultAdmin();

createApp().listen(config.port, () => {
  console.log(`MealMate backend đang chạy tại http://localhost:${config.port}`);
  console.log(`Tài khoản admin mặc định: ${DEFAULT_ADMIN.email} / ${DEFAULT_ADMIN.password}`);
});
