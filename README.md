# MealMate – Ứng dụng lập kế hoạch bữa ăn và danh sách mua sắm

Đồ án nhóm môn **Lập trình thiết bị di động (LTTBDĐ)** – Trường Đại học Giao thông vận tải TP.HCM (UTH).

## Thông tin nhóm
| Thành viên | MSSV | Vai trò chính |
|---|---|---|
| Thành viên A | | Onboarding, đăng ký, thiết lập, hồ sơ |
| Thành viên B | | Trang chủ, kế hoạch tuần, lịch sử |
| Thành viên C | | Nền tảng chung, danh sách mua sắm, tủ đồ |

## Liên kết quan trọng
- Figma: _dán link file Figma của nhóm vào đây_
- Giảng viên: _tên giảng viên_

## Mục lục tài liệu
| Tài liệu | Nội dung |
|---|---|
| [01 – Ý tưởng và phân tích](docs/01-y-tuong-phan-tich.md) | Đề tài, đối tượng, chức năng, dữ liệu |
| [02 – Danh sách màn hình](docs/02-danh-sach-man-hinh.md) | 14 màn hình đã thiết kế + màn còn thiếu |
| [03 – Quy trình làm nhóm](docs/03-quy-trinh-nhom.md) | Chia việc, nhánh Git, quy tắc PR |
| [04 – Cấu trúc code](docs/04-cau-truc-code.md) | Cây thư mục và công nghệ đề xuất |
| [05 – Đối chiếu UI](docs/05-doi-chieu-ui.md) | Bảng đối chiếu Figma và app thật |
| [Backend API](backend/README.md) | Server Node.js, API cho app và admin dashboard |

## Chạy backend
```bash
cd backend
npm install
npm run dev   # http://localhost:3000, từ emulator gọi http://10.0.2.2:3000
npm test      # test API và logic tạo thực đơn
```

## Chạy app Android
1. Bật backend như trên (app lấy toàn bộ dữ liệu từ API).
2. Mở thư mục gốc bằng Android Studio, chọn emulator rồi bấm **Run**, hoặc dùng terminal:
   ```bash
   ./gradlew installDebug   # cài lên emulator đang mở
   ./gradlew test           # unit test (định dạng tiền/ngày, nhóm mua sắm, ShoppingListBuilder)
   ```
3. Chạy trên **điện thoại thật** (cùng Wi-Fi với máy tính): truyền IP LAN của máy chạy backend
   ```bash
   ./gradlew installDebug -Pmealmate.apiBaseUrl=http://192.168.1.10:3000/api/
   ```

Lưu ý:
- Backend lưu dữ liệu trong bộ nhớ, tắt server là mất tài khoản. Khi đó app tự đăng xuất (server trả 401) và quay về màn đăng nhập.
- Bản debug mới cho phép gọi HTTP và log request; bản release chặn HTTP không mã hóa.

## Tiến độ theo tuần
| Tuần | Nội dung | Trạng thái |
|---|---|---|
| 1 | Chọn đề tài, phân tích, thiết kế UI trên Figma, đẩy lên GitHub | Đang làm |
| 2 | | |
