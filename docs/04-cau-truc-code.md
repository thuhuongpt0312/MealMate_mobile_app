# 04 – Cấu trúc code

> Công nghệ dưới đây là **đề xuất**. Cần xác nhận với giảng viên môn LTTBDĐ (Flutter, React Native hay Android native) trước khi bắt đầu.

## Công nghệ đề xuất: Flutter
| Hạng mục | Công cụ |
|---|---|
| Ngôn ngữ / framework | Dart + Flutter |
| Quản lý trạng thái | Provider (đơn giản) hoặc Riverpod |
| Điều hướng | go_router |
| Lưu dữ liệu cục bộ | shared_preferences hoặc sqflite |
| Dữ liệu mẫu | File JSON trong `assets/data/` |
| Font | Google Fonts (google_fonts) |
| Công cụ | Android Studio hoặc VS Code, Android Emulator, Git/GitHub Desktop, Figma |

## Cây thư mục
```
lib/
├── main.dart
├── app/
│   ├── router.dart          # điều hướng
│   └── theme.dart           # màu, font, bo góc lấy từ Figma
├── core/
│   └── widgets/             # nút, ô nhập, chip, thanh điều hướng dưới (C)
├── data/
│   ├── models/              # Recipe, MealPlan, ShoppingItem...
│   └── repositories/        # đọc JSON / lưu cục bộ
└── features/
    ├── onboarding/          # S01–S03 (A)
    ├── auth/                # S04, S15 (A)
    ├── setup/               # S05–S07 (A)
    ├── profile/             # S14 (A)
    ├── home/                # S08–S09 (B)
    ├── meal_plan/           # S10, S16, S17 (B)
    ├── history/             # S13 (B)
    └── shopping/            # S11–S12 (C)
assets/
├── images/
└── data/
```
Mỗi feature chia: `screens/`, `widgets/`, `providers/`.

## Quy ước đặt tên
- File: `snake_case`, ví dụ `s10_meal_plan_screen.dart`.
- Có mã màn hình trong tên file để dễ đối chiếu với Figma.
- Tên frame Figma cũng đặt theo mã: `S10 – Meal Plan`.

## Thiết kế token (điền từ Figma)
Mở Figma → chọn đối tượng → Dev Mode / Inspect để lấy giá trị chính xác.
| Token | Giá trị | Dùng cho |
|---|---|---|
| Màu chính (xanh lá đậm) | | Nút, tab đang chọn |
| Màu nền (kem) | | Nền màn hình |
| Màu chữ chính | | Tiêu đề |
| Màu chữ phụ | | Mô tả |
| Viền / thẻ nhạt | | Thẻ, ô nhập |
| Font | | |
| Bo góc nút / thẻ | | |
