/**
 * Dữ liệu mẫu, giữ giống app/src/main/java/vn/edu/uth/mealmate/data/sample/SampleData.kt
 * để app Android và backend hiển thị cùng một nội dung.
 */

export const FOOD_GROUPS = {
  VEGETABLES: 'Rau củ',
  PROTEIN: 'Thịt & đạm',
  PANTRY: 'Đồ khô',
  DAIRY: 'Sữa & trứng',
  OTHER: 'Khác',
};

export const MEAL_SLOTS = {
  BREAKFAST: 'Bữa sáng',
  LUNCH: 'Bữa trưa',
  DINNER: 'Bữa tối',
  SNACK: 'Bữa phụ',
};

export const WEEKLY_BUDGET = 700_000;

const ing = (name, amount, unit, group, unitPrice) => ({ name, amount, unit, group, unitPrice });

export const seedRecipes = () => [
  {
    id: 'banh-mi-trung-bo', name: 'Bánh mì trứng bơ', slot: 'BREAKFAST', minutes: 15,
    pricePerServing: 22_000, calories: 380, status: 'published',
    ingredients: [
      ing('Bánh mì', 1, 'ổ', 'PANTRY', 6_000),
      ing('Trứng', 2, 'quả', 'DAIRY', 3_500),
      ing('Bơ', 0.5, 'quả', 'VEGETABLES', 15_000),
    ],
  },
  {
    id: 'bun-thit-nuong', name: 'Bún thịt nướng', slot: 'LUNCH', minutes: 20,
    pricePerServing: 35_000, calories: 520, status: 'published',
    ingredients: [
      ing('Bún khô', 100, 'g', 'PANTRY', 40),
      ing('Thịt heo', 120, 'g', 'PROTEIN', 130),
      ing('Rau sống', 1, 'phần', 'VEGETABLES', 3_000),
    ],
  },
  {
    id: 'com-ga-gung', name: 'Cơm gà gừng', slot: 'DINNER', minutes: 25,
    pricePerServing: 42_000, calories: 540, status: 'published',
    ingredients: [
      ing('Ức gà', 150, 'g', 'PROTEIN', 90),
      ing('Gạo thơm', 80, 'g', 'PANTRY', 25),
      ing('Rau bó xôi', 0.5, 'túi', 'VEGETABLES', 20_000),
    ],
  },
  {
    id: 'sua-chua-trai-cay', name: 'Sữa chua trái cây', slot: 'SNACK', minutes: 5,
    pricePerServing: 18_000, calories: 180, status: 'published',
    ingredients: [
      ing('Sữa chua Hy Lạp', 1, 'hũ', 'DAIRY', 12_000),
      ing('Trái cây', 1, 'phần', 'VEGETABLES', 6_000),
    ],
  },
];

export const seedPantry = () => [
  { name: 'Gạo thơm', amount: 750, unit: 'g' },
  { name: 'Trứng', amount: 8, unit: 'quả' },
  { name: 'Ức gà', amount: 450, unit: 'g' },
  { name: 'Rau bó xôi', amount: 1, unit: 'túi' },
  { name: 'Cà rốt', amount: 5, unit: 'củ' },
  { name: 'Nước tương', amount: 0.5, unit: 'chai' },
];

export const defaultPreferences = () => ({
  servings: 2,
  goal: 'Ăn uống cân bằng',
  weeklyBudget: WEEKLY_BUDGET,
  tastes: [],
  avoid: [],
});
