# MealMate Backend

REST API cho app Android MealMate và trang quản trị (admin dashboard).
Viết bằng **Node.js + Express 5**, dữ liệu đang lưu tạm trong bộ nhớ (`src/data/store.js`), tắt server là mất.

## Chạy
Cần Node.js 20 trở lên.
```bash
cd backend
npm install
npm run dev      # tự khởi động lại khi sửa code
npm test         # chạy toàn bộ test
```
Server chạy ở `http://localhost:3000` (đổi bằng biến `PORT`, xem `.env.example`).

- Từ **emulator Android**, gọi `http://10.0.2.2:3000/api/...` (10.0.2.2 là localhost của máy tính).
- Tài khoản admin mặc định khi chạy local: `admin@mealmate.local` / `admin123`.

## Cấu trúc
```
backend/
├── src/
│   ├── server.js            Khởi động server, tạo tài khoản admin mặc định
│   ├── app.js               Gắn middleware và các route
│   ├── config.js            Đọc biến môi trường
│   ├── data/seed.js         Dữ liệu mẫu (giống SampleData.kt bên app)
│   ├── data/store.js        Kho dữ liệu in-memory – thay bằng database sau này
│   ├── middleware/          requireAuth, requireAdmin, xử lý lỗi
│   ├── routes/              auth, me, recipes, mealPlans, admin
│   ├── services/            shoppingListBuilder (bản JS của ShoppingListBuilder.kt), mealPlanner, adminStats
│   └── utils/               HttpError, kiểm tra dữ liệu đầu vào
└── test/                    node:test
```

## Quy ước chung
- Body và response đều là JSON. Tiền tính bằng **đồng** (số nguyên), ngày dạng `YYYY-MM-DD`.
- Route cần đăng nhập gửi header `Authorization: Bearer <token>` (lấy từ `/api/auth/login`).
- Lỗi trả về `{ "error": "thông báo", "details": ... }` với mã 400 / 401 / 403 / 404 / 409 / 422.
- Danh sách có phân trang nhận `?page=1&limit=20`, trả `{ items, page, limit, total }`.

## API cho app

| Method | Đường dẫn | Màn hình | Mô tả |
|---|---|---|---|
| GET | `/api/health` | | Kiểm tra server |
| POST | `/api/auth/register` | S02 | `{ name, email, password }` → `{ user, token, expiresAt }` |
| POST | `/api/auth/login` | S02 | `{ email, password }` → `{ user, token, expiresAt }` |
| POST | `/api/auth/logout` | S16 | Hủy token hiện tại |
| GET | `/api/auth/me` | S16 | Thông tin người đang đăng nhập |
| GET / PUT | `/api/me/preferences` | S03, S16 | `{ servings, goal, weeklyBudget, tastes[], avoid[] }` |
| GET / POST | `/api/me/pantry` | S10 | Tủ bếp; POST `{ name, amount, unit }` |
| PATCH / DELETE | `/api/me/pantry/:id` | S10 | Sửa / xóa nguyên liệu trong tủ |
| GET | `/api/recipes?slot=&q=&maxPrice=` | | Món đã duyệt; `slot` = `BREAKFAST` `LUNCH` `DINNER` `SNACK` |
| GET | `/api/recipes/:id` | S08 | Chi tiết món |
| POST | `/api/meal-plans` | S05–S07 | `{ startDate?, servings? }` → thực đơn 7 ngày, đồng thời tạo danh sách mua sắm |
| GET | `/api/meal-plans` | S12b | Lịch sử thực đơn, mới nhất trước |
| GET | `/api/meal-plans/:id` | S07 | Thực đơn kèm `recipes` (tra theo id món) |
| GET / POST | `/api/meal-plans/:id/shopping-list` | S09 | `{ items, total, remaining }`; POST thêm món tự nhập |
| PATCH / DELETE | `/api/meal-plans/:id/shopping-list/:itemId` | S09 | PATCH `{ bought: true }` để tick đã mua |

Thực đơn hiện được tạo bằng `services/mealPlanner.js` (xoay vòng món theo bữa, bỏ món cần tránh) – chỗ này sẽ thay bằng gọi API AI.

## API cho admin dashboard
Tất cả nằm dưới `/api/admin`, cần token của tài khoản `role: "admin"`.

| Method | Đường dẫn | Trạng thái | Mô tả |
|---|---|---|---|
| GET | `/dashboard/overview` | ✅ | Thẻ tổng quan: người dùng, món, thực đơn, số thực đơn vượt ngân sách |
| GET | `/dashboard/activity?days=7` | ✅ | Thực đơn và người dùng mới theo ngày (biểu đồ cột) |
| GET | `/dashboard/popular-recipes?limit=5` | ✅ | Món được xếp vào thực đơn nhiều nhất |
| GET | `/users?q=&status=&role=` | ✅ | Danh sách người dùng (có phân trang) |
| GET | `/users/:id` | ✅ | Chi tiết + tùy chọn + số thực đơn |
| PATCH | `/users/:id` | ✅ | `{ status: "active" \| "locked", role: "user" \| "admin" }` |
| GET / POST | `/recipes` | ✅ | Xem tất cả món (mọi trạng thái) / thêm món mới (mặc định `draft`) |
| PUT / DELETE | `/recipes/:id` | ✅ | Sửa / xóa món |
| PATCH | `/recipes/:id/status` | ✅ | Duyệt hoặc ẩn: `draft` · `published` · `hidden` |
| POST | `/recipes/import` | 🟨 501 | Nhập món từ CSV/Excel |
| GET | `/meal-plans?userId=` | ✅ | Tất cả thực đơn (không kèm chi tiết ngày) |
| GET | `/reports/cost?period=` | 🟨 mẫu | Báo cáo chi phí theo nhóm thực phẩm |
| GET | `/reports/retention` | 🟨 mẫu | Tỉ lệ người dùng quay lại |
| GET | `/reports/export` | 🟨 501 | Xuất báo cáo CSV/Excel |
| GET | `/notifications` | 🟨 mẫu | Lịch sử thông báo đã gửi |
| POST | `/notifications` | 🟨 501 | Gửi thông báo hàng loạt (FCM) |
| GET | `/settings` | 🟨 mẫu | Cài đặt hệ thống |
| PUT | `/settings` | 🟨 501 | Lưu cài đặt hệ thống |
| GET | `/audit-logs` | 🟨 mẫu | Nhật ký thao tác admin |

✅ đã chạy với dữ liệu thật · 🟨 placeholder.
Route placeholder dạng GET trả `{ "placeholder": true, "note": "...", "data": ... }` để web admin dựng giao diện trước;
route placeholder dạng ghi trả **501** kèm `expectedBody` mô tả body dự kiến.

## Việc tiếp theo
- Lưu dữ liệu thật (SQLite/PostgreSQL hoặc MongoDB) – chỉ cần viết lại `src/data/store.js`.
- Thay `mealPlanner` bằng gọi API AI.
- Hoàn thiện các route placeholder ở trên.
- Nối app Android vào API (Retrofit/Ktor client) thay cho `SampleData`.
