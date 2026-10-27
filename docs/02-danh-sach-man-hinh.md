# 02 – Danh sách màn hình (Figma ↔ Code)

Trạng thái: ⬜ chưa làm · 🟨 đang làm · 🟩 xong · ✅ đã đối chiếu với Figma

| ID | Màn hình | File code | Route | Phụ trách | Trạng thái |
|---|---|---|---|---|---|
| S01 | Làm quen | `ui/screens/onboarding/OnboardingScreen.kt` | `onboarding` | A | ⬜ |
| S02 | Tạo tài khoản | `ui/screens/auth/RegisterScreen.kt` | `register` | A | ⬜ |
| S03 | Vài điều về bạn | `ui/screens/setup/SetupScreen.kt` | `setup` | A | ⬜ |
| S04 | Trang chủ | `ui/screens/home/HomeScreen.kt` | `home` | B | ⬜ |
| S05 | Tạo thực đơn | `ui/screens/plan/CreatePlanScreen.kt` | `create_plan` | B | ⬜ |
| S06 | AI đang tạo | `ui/screens/plan/GeneratingScreen.kt` | `generating` | B | ⬜ |
| S07 | Thực đơn tuần | `ui/screens/mealplan/MealPlanScreen.kt` | `meal_plan` | B | ⬜ |
| S08 | Chi tiết món | `ui/screens/mealplan/MealDetailScreen.kt` | `meal_detail/{mealId}` | B | ⬜ |
| S09 | Mua sắm | `ui/screens/shopping/ShoppingScreen.kt` | `shopping` | C | ⬜ |
| S10 | Tủ bếp | `ui/screens/pantry/PantryScreen.kt` | `pantry` | C | ⬜ |
| S11 | Dinh dưỡng | `ui/screens/nutrition/NutritionScreen.kt` | `nutrition` | C | ⬜ |
| S12 | Yêu thích | `ui/screens/favorites/FavoritesScreen.kt` | `favorites` | B | ⬜ |
| S12b | Lịch sử | `ui/screens/history/HistoryScreen.kt` | `history` | C | ⬜ |
| S13 | Thông báo | `ui/screens/notifications/NotificationsScreen.kt` | `notifications` | A | ⬜ |
| S14 | Cài đặt thông báo | `ui/screens/notifications/NotificationSettingsScreen.kt` | `notification_settings` | A | ⬜ |
| S13b | Mẫu thông báo (màn hình khóa, banner, snackbar) | `notifications/MealMateNotifier.kt` | (thông báo hệ thống) | A | ⬜ |
| S15 | Thống kê chi phí | `ui/screens/stats/CostStatsScreen.kt` | `cost_stats` | C | ⬜ |
| S16 | Cá nhân | `ui/screens/profile/ProfileScreen.kt` | `profile` | A | ⬜ |
| HT | Nguồn hình ảnh | `ui/screens/profile/ImageCreditsScreen.kt` | `image_credits` | A | ⬜ |

**Thanh điều hướng đáy** (Trang chủ · Thực đơn · Mua sắm · Lịch sử · Cá nhân): `navigation/MealMateApp.kt`, người phụ trách: B.

**Ghi chú**
- S01 có 3 slide, S03 có 3 bước, S05 có 4 bước (cùng một màn hình, đổi nội dung theo bước).
- S15 có 6 trạng thái: tuần, tháng, 3 tháng, năm, trống, đang tải.
- "HT" là màn hệ thống Nguồn hình ảnh (mở từ Cá nhân).
- S13b không phải màn hình trong app mà là thông báo hệ thống (Notification).
- Cập nhật bảng này mỗi khi thiết kế Figma thay đổi.
