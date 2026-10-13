# 04 – Cấu trúc code (Kotlin + Jetpack Compose)

**Ngôn ngữ:** Kotlin · **UI:** Jetpack Compose + Material 3 · **IDE:** Android Studio · **Kiến trúc:** MVVM

## 1. Cấu trúc thư mục
```
app/src/main/java/vn/edu/uth/mealmate/
├── MainActivity.kt
├── navigation/            Routes.kt, MealMateApp.kt (NavHost + thanh đáy)
├── data/
│   ├── model/             Models.kt (Meal, Ingredient, PantryItem, ShoppingItem...)
│   ├── sample/            SampleData.kt (dữ liệu mẫu tiếng Việt, VNĐ)
│   └── (sau này) local/, repository/   Room, DataStore
├── domain/                ShoppingListBuilder.kt (logic cốt lõi, có unit test)
├── notifications/         MealMateNotifier.kt (thông báo nhắc nhở)
├── util/                  Format.kt (formatVnd)
└── ui/
    ├── theme/             Color.kt, Theme.kt
    ├── components/        PrimaryButton, PlaceholderScreen, (thêm thẻ, chip, ô nhập...)
    └── screens/           onboarding, auth, setup, home, plan, mealplan, shopping,
                           pantry, nutrition, favorites, history, notifications, stats, profile
```
Mỗi màn hình sau này gồm `XScreen.kt` (giao diện) và `XViewModel.kt` (trạng thái, dùng `StateFlow`).

## 2. Thư viện
| Giai đoạn | Thư viện | Dùng cho |
|---|---|---|
| Có sẵn trong khung | Navigation Compose, Material Icons Extended | Điều hướng, icon |
| Giai đoạn 3–4 | ViewModel Compose, Room, DataStore | Trạng thái, lưu thực đơn / tủ bếp / cài đặt |
| Giai đoạn 4 | WorkManager | Đặt lịch nhắc bữa ăn, đi chợ, cảnh báo ngân sách |
| Giai đoạn 2–3 | Coil | Hiển thị ảnh món |
| Giai đoạn 3 | Biểu đồ: tự vẽ bằng Canvas hoặc thư viện Vico | S11 Dinh dưỡng, S15 Thống kê |

## 3. Logic cốt lõi
`ShoppingListBuilder.build(meals, servings, pantry)`:
1. Gộp nguyên liệu trùng tên và đơn vị qua tất cả các bữa trong thực đơn.
2. Trừ lượng đang có trong tủ bếp.
3. Chỉ giữ phần còn thiếu và tính tiền → đây là danh sách mua sắm ở S09.

Chạy test: `./gradlew test` hoặc chuột phải `ShoppingListBuilderTest` → Run.

## 4. Quy ước
- Tên composable màn hình kết thúc bằng `Screen`; tham số điều hướng truyền qua lambda (`onBack`, `onCreatePlan`...), không truyền `NavController` xuống màn hình con.
- Chữ hiển thị đưa vào `res/values/strings.xml` khi hoàn thiện (hiện đang viết trực tiếp cho nhanh).
- Tiền luôn dùng `formatVnd(...)`, không tự ghép chuỗi.
- Màu lấy từ `AppColors`, không viết mã màu trực tiếp trong màn hình.

## 5. Design tokens (ước lượng, cần đối chiếu Figma Dev Mode)
| Token | Giá trị | Dùng cho |
|---|---|---|
| Cream | `#FBF8F1` | Nền |
| Surface | `#FFFEFA` | Thẻ, ô nhập |
| Green | `#3A7D5C` | Nút chính |
| GreenDark | `#255B42` | Banner |
| GreenSoft | `#E8F1EB` | Nền icon, chip |
| Orange | `#E8892B` | Cột vượt ngân sách |
| Ink / Muted / Line | `#1B2A22` / `#6B7A72` / `#E6E4DA` | Chữ chính / chữ phụ / viền |

Font: Be Vietnam Pro (hoặc font đúng với Figma) – thêm file font vào `res/font` và cấu hình trong `ui/theme`.
