# 02 – Danh sách màn hình

Mã màn hình (S01…) dùng thống nhất cho Figma, tên file code và bảng đối chiếu.

## A. Màn hình đã thiết kế (14)
| Mã | Tên | Thành phần chính | Phụ trách |
|---|---|---|---|
| S01 | Onboarding 1 | Logo, nút Skip, ảnh, tiêu đề, chấm trang, nút Continue | A |
| S02 | Onboarding 2 | Như S01, đổi nội dung | A |
| S03 | Onboarding 3 | Như S01, nút đổi thành Get Started, ẩn Skip | A |
| S04 | Tạo tài khoản | Ảnh đầu trang, ô tên, email, mật khẩu, nút Create account, link Log in | A |
| S05 | Thiết lập 1/3 | Thanh tiến độ, bộ đếm số người (− / +), chọn Just me / My family | A |
| S06 | Thiết lập 2/3 | Chọn 1 mục tiêu trong 4, nút Back / Continue | A |
| S07 | Thiết lập 3/3 | Chip khẩu vị chọn nhiều, ô món cần tránh, nút Finish setup | A |
| S08 | Trang chủ | Lời chào, thẻ Create Meal Plan, món hôm nay, ngân sách ngày, mục tiêu, 4 lối tắt | B |
| S09 | Trang chủ (cuộn) | Danh sách ngang "You might like" | B |
| S10 | Kế hoạch tuần | Tổng chi phí, vòng 97%, chọn ngày, món 4 bữa, gợi ý tái sử dụng nguyên liệu | B |
| S11 | Danh sách mua sắm | Tổng tạm tính, tiến độ 2/12, nhóm, checkbox, xóa, nút thêm (+) | C |
| S12 | Danh sách mua sắm (cuộn) | Thêm nhóm Dairy, nút Back to meal plan | C |
| S13 | Lịch sử | Thẻ tuần này (View plan / Plan again), danh sách tuần trước | B |
| S14 | Hồ sơ | Thẻ người dùng, mục tiêu hiện tại, danh sách cài đặt, Log out | A |

Thanh điều hướng dưới (5 tab): Home, Meal Plan, Shopping, History, Profile, dùng chung cho S08–S14. Do C làm.

## B. Màn hình còn thiếu, cần thiết kế thêm trên Figma
Đề yêu cầu "toàn bộ thiết kế", nên các màn dưới đây cần có trước khi nộp tuần 1:

| Mã | Tên | Lý do cần |
|---|---|---|
| S15 | Đăng nhập | S04 có link "Already have an account? Log in" |
| S16 | Đang tạo kế hoạch (loading/AI) | Nút Create Meal Plan ở S08 |
| S17 | Chi tiết món ăn | Mũi tên (>) ở món S08, S10 |
| S18 | Thêm món vào danh sách mua sắm | Nút (+) ở S11 |
| S19 | Chi tiết kế hoạch cũ | Nút View plan, mũi tên ở S13 |
| S20 | Tủ đồ (My pantry) | Lối tắt ở S08 |
| S21 | Dinh dưỡng (Nutrition) | Lối tắt ở S08 |
| S22 | Yêu thích (Favorites) | Lối tắt ở S08 |
| S23 | Sửa hồ sơ / Personal details | Nút Edit ở S14 |
| S24 | Các trang cài đặt con | Dietary goal, Household size, Budget, Food preferences, Notifications, Appearance |

Nếu thiếu thời gian, ghi rõ trong tài liệu những màn nào chưa làm và lý do, hoặc hỏi giảng viên có chấp nhận phạm vi MVP không.

## C. Các trạng thái cần có
Ngoài màn hình tĩnh, nên thiết kế (hoặc ghi chú) cho: ô nhập báo lỗi (sai email, mật khẩu < 8 ký tự), danh sách trống, đang tải, mất mạng.
