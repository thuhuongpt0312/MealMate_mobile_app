import { createApp } from './app.js';
import { config } from './config.js';

createApp().listen(config.port, () => {
  console.log(`MealMate backend đang chạy tại http://localhost:${config.port}`);
});
