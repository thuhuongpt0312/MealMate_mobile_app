# 02 – Danh sách màn hình (Figma ↔ Code)

Trạng thái: ⬜ chưa làm · 🟨 đang làm · 🟩 xong · ✅ đã đối chiếu với Figma

| ID | Màn hình | File code | Route | Phụ trách | Trạng thái |
|---|---|---|---|---|---|
| S01 | Làm quen | `ui/screens/onboarding/OnboardingScreen.kt` | `onboarding` | A | ✅ |
| S02 | Tạo tài khoản | `ui/screens/auth/RegisterScreen.kt` | `register` | A | ✅ |
| S03 | Vài điều về bạn | `ui/screens/setup/SetupScreen.kt` | `setup` | A | ✅ |
| S04 | Trang chủ | `ui/screens/home/HomeScreen.kt` | `home` | B | ✅ |
| S05 | Tạo thực đơn | `ui/screens/plan/CreatePlanScreen.kt` | `create_plan` | B | ✅ |
| S06 | AI đang tạo | `ui/screens/plan/GeneratingScreen.kt` | `generating` | B | ✅ |
| S07 | Thực đơn tuần | `ui/screens/mealplan/MealPlanScreen.kt` | `meal_plan` | B | ✅ |
| S08 | Chi tiết món | `ui/screens/mealplan/MealDetailScreen.kt` | `meal_detail/{mealId}` | B | ✅ |
| S09 | Mua sắm | `ui/screens/shopping/ShoppingScreen.kt` | `shopping` | C | ✅ |
| S10 | Tủ bếp | `ui/screens/pantry/PantryScreen.kt` | `pantry` | C | ✅ |
| S11 | Dinh dưỡng | `ui/screens/nutrition/NutritionScreen.kt` | `nutrition` | C | ✅ |
| S12 | Yêu thích | `ui/screens/favorites/FavoritesScreen.kt` | `favorites` | B | ✅ |
| S12b | Lịch sử | `ui/screens/history/HistoryScreen.kt` | `history` | C | ✅ |
| S13 | Thông báo | `ui/screens/notifications/NotificationsScreen.kt` | `notifications` | A | ✅ |
| S14 | Cài đặt thông báo | `ui/screens/notifications/NotificationSettingsScreen.kt` | `notification_settings` | A | ✅ |
| S13b | Mẫu thông báo (màn hình khóa, banner) | `ui/screens/notifications/NotificationMockupScreen.kt` & `notifications/MealMateNotifier.kt` | `notification_mockup` & thông báo hệ thống | A | ✅ |
| S15 | Thống kê chi phí (tuần, tháng, 3 tháng, năm, trống, đang tải) | `ui/screens/stats/CostStatsScreen.kt` | `cost_stats` | C | ✅ |
| S16 | Cá nhân | `ui/screens/profile/ProfileScreen.kt` | `profile` | A | ✅ |
| HT | Nguồn hình ảnh | `ui/screens/profile/ImageCreditsScreen.kt` | `image_credits` | A | ✅ |

**Thanh điều hướng đáy** (Trang chủ · Thực đơn · Mua sắm · Lịch sử · Cá nhân): `navigation/MealMateApp.kt`, người phụ trách: B.

**Tài nguyên hình ảnh Figma (res/drawable)**:
- `img_s01_mamcom.webp` / `meal_table.webp`: Mâm cơm Việt Nam (S01 Onboarding).
- `img_s04_comgagung.webp` / `ginger_chicken_rice.webp`: Cơm gà gừng (S04 Trang chủ, S08 Chi tiết món).
- `img_s07_phoga.webp` / `chicken_pho.webp`: Phở gà (S07 Thực đơn tuần).
- `img_s07_bunthitnuong.webp` / `grilled_pork_noodles.webp`: Bún thịt nướng (S02 Banner đăng ký, S07, S08, S12).
- `img_s12_comgiadinh.webp` / `family_meal.webp`: Cơm gia đình (S12 Yêu thích, S12b Lịch sử).
- `img_s15_rauxanh.webp` / `fresh_greens.webp`: Rau xanh (S10 Tủ bếp, S11 Dinh dưỡng, S15 Thống kê).
- Được quản lý tập trung và ánh xạ tự động qua `vn.edu.uth.mealmate.util.MealImages`.

**Nối API** (backend ở `backend/`): S02, S03, S04, S05–S10, S12b, S16 đọc/ghi dữ liệu thật qua `MealMateRepository`.
S12 Yêu thích lưu id món trên máy (`data/local/FavoritesStore.kt`).
S13b hỗ trợ cả màn hình xem trước giao diện thông báo khóa và khởi tạo `NotificationChannel` hệ thống qua `MealMateNotifier`.
S15 hỗ trợ đầy đủ 6 trạng thái thiết kế Figma: Tuần, Tháng, 3 tháng, Năm, Trạng thái trống (Empty) và Đang tải (Skeleton), kèm phân tích chi tiêu theo nhóm thực phẩm.
