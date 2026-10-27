# 01 – Ý tưởng và phân tích

## 1. Đề tài
**MealMate** – ứng dụng di động giúp lên thực đơn cả tuần dựa trên ngân sách, số người trong gia đình, mục tiêu ăn uống và khẩu vị. Ứng dụng tự tạo danh sách mua sắm từ thực đơn và tận dụng nguyên liệu đã có trong tủ để tiết kiệm tiền.

## 2. Vấn đề cần giải quyết
- Mỗi ngày không biết nấu gì, tốn thời gian nghĩ món.
- Đi chợ thiếu hoặc thừa nguyên liệu, lãng phí tiền.
- Khó cân đối dinh dưỡng và ngân sách cùng lúc.

## 3. Đối tượng sử dụng
Người đi làm, sinh viên ở trọ, gia đình nhỏ (1–5 người) muốn ăn uống hợp lý và tiết kiệm.

## 4. Chức năng chính (MVP – bắt buộc làm)
| Nhóm | Chức năng | Màn hình liên quan |
|---|---|---|
| Làm quen | Giới thiệu 3 bước, bỏ qua được | S01–S03 |
| Tài khoản | Đăng ký, đăng nhập | S04 |
| Thiết lập | Số người, mục tiêu, khẩu vị, món cần tránh | S05–S07 |
| Trang chủ | Lời chào, tạo kế hoạch, món hôm nay, ngân sách ngày | S08–S09 |
| Kế hoạch tuần | Xem thực đơn từng ngày, tổng chi phí, gợi ý tái sử dụng nguyên liệu | S10 |
| Mua sắm | Danh sách theo nhóm, tick đã mua, thêm, xóa | S11–S12 |
| Lịch sử | Xem và dùng lại kế hoạch cũ | S13 |
| Hồ sơ | Xem và sửa tùy chọn cá nhân | S14 |

## 5. Chức năng mở rộng (làm nếu còn thời gian)
- Tạo thực đơn bằng AI thật (gọi API), hiện đang có thể giả lập bằng dữ liệu mẫu.
- Tủ đồ (Pantry), Dinh dưỡng (Nutrition), Yêu thích (Favorites).
- Thông báo nhắc đi chợ, chế độ tối.

## 6. Luồng người dùng chính
```
Mở app → Giới thiệu (3 trang) → Đăng ký → Thiết lập 3 bước → Trang chủ
Trang chủ → Tạo kế hoạch → Kế hoạch tuần → Danh sách mua sắm
Trang chủ ↔ Kế hoạch ↔ Mua sắm ↔ Lịch sử ↔ Hồ sơ  (thanh điều hướng dưới)
```

## 7. Dữ liệu sơ bộ
| Thực thể | Trường chính |
|---|---|
| User | id, tên, email |
| Preference | số người, mục tiêu, ngân sách, khẩu vị[], món tránh[] |
| Recipe | id, tên, ảnh, thời gian nấu, giá/khẩu phần, calo, bữa ăn, nguyên liệu[] |
| MealPlan | id, ngày bắt đầu, ngày kết thúc, tổng chi phí, danh sách món theo ngày |
| ShoppingItem | id, tên, số lượng, nhóm, đã mua |
| PantryItem | id, tên, số lượng |

## 8. Nghiên cứu ứng dụng tương tự (nhóm tự điền)
| Ứng dụng | Điểm mạnh | Điểm yếu | MealMate khác ở đâu |
|---|---|---|---|
| | | | |
| | | | |
