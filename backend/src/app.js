import express from 'express';
import { config } from './config.js';
import { errorHandler, notFound } from './middleware/errorHandler.js';
import adminRoutes from './routes/admin.js';
import authRoutes from './routes/auth.js';
import healthRoutes from './routes/health.js';
import mealPlanRoutes from './routes/mealPlans.js';
import meRoutes from './routes/me.js';
import recipeRoutes from './routes/recipes.js';

export function createApp() {
  const app = express();
  app.use(express.json());

  // CORS đơn giản cho web admin chạy ở cổng khác
  app.use((req, res, next) => {
    res.set('Access-Control-Allow-Origin', config.corsOrigin);
    res.set('Access-Control-Allow-Headers', 'Content-Type, Authorization');
    res.set('Access-Control-Allow-Methods', 'GET, POST, PUT, PATCH, DELETE, OPTIONS');
    if (req.method === 'OPTIONS') return res.sendStatus(204);
    next();
  });

  app.get('/', (req, res) => {
    res.json({
      status: 'ok',
      service: 'mealmate-backend',
      message: 'MealMate Backend API đang chạy bình thường',
      health: '/api/health',
    });
  });
  app.get('/health', (req, res) => res.redirect('/api/health'));

  app.use('/api/health', healthRoutes);
  app.use('/api/auth', authRoutes);
  app.use('/api/me', meRoutes);
  app.use('/api/recipes', recipeRoutes);
  app.use('/api/meal-plans', mealPlanRoutes);
  app.use('/api/admin', adminRoutes);

  app.use(notFound);
  app.use(errorHandler);
  return app;
}
