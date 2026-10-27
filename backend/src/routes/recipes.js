import { Router } from 'express';
import { MEAL_SLOTS } from '../data/seed.js';
import { store } from '../data/store.js';
import { HttpError } from '../utils/http.js';

const router = Router();

/** GET /api/recipes?slot=LUNCH&q=gà&maxPrice=40000 – chỉ trả món đã duyệt (published). */
router.get('/', (req, res) => {
  const { slot, q, maxPrice } = req.query;
  if (slot && !MEAL_SLOTS[slot]) throw new HttpError(400, 'slot không hợp lệ', { allowed: Object.keys(MEAL_SLOTS) });

  const keyword = q?.toString().trim().toLowerCase();
  const recipes = store.listRecipes().filter((r) =>
    r.status === 'published'
    && (!slot || r.slot === slot)
    && (!keyword || r.name.toLowerCase().includes(keyword))
    && (!maxPrice || r.pricePerServing <= Number(maxPrice)));
  res.json({ items: recipes, total: recipes.length });
});

// S08 · Chi tiết món
router.get('/:id', (req, res) => {
  const recipe = store.findRecipe(req.params.id);
  if (!recipe || recipe.status !== 'published') throw new HttpError(404, 'Không tìm thấy món');
  res.json(recipe);
});

export default router;
