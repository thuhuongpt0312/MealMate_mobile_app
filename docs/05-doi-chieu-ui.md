# 05 – Đối chiếu UI (Figma và app thật)

Yêu cầu của đề: cuối kỳ đối chiếu giữa đồ án đã code và UI đã thiết kế. File này là bảng theo dõi từ đầu để không dồn cuối kỳ.

## Cách đối chiếu mỗi màn hình
1. Xuất frame Figma ra PNG (Export, 2x) vào `docs/design/figma-export/`, đặt tên `S10.png`.
2. Chạy app trên **cùng một kích thước máy ảo** cho cả nhóm, chụp màn hình vào `docs/design/app-screenshots/`, cùng tên `S10.png`.
3. Đặt hai ảnh cạnh nhau, hoặc chồng ảnh Figma lên app ở độ mờ 50% để thấy lệch.
4. Điền bảng bên dưới và sửa code đến khi khớp.

## Tiêu chí kiểm tra
- Bố cục và khoảng cách (padding, margin)
- Màu sắc, font, cỡ chữ, độ đậm
- Biểu tượng và ảnh
- Bo góc, viền, bóng đổ
- Trạng thái (đang chọn, đã tick, bị vô hiệu)
- Nội dung chữ
- Hành vi điều hướng (bấm đâu sang đâu)

Ký hiệu: ✅ khớp · ⚠️ lệch nhẹ · ❌ chưa làm / lệch nhiều

## Bảng theo dõi
| Mã | Màn hình | File code | Người làm | Bố cục | Màu/Font | Trạng thái | Điều hướng | Ghi chú lệch |
|---|---|---|---|---|---|---|---|---|
| S01 | Onboarding 1 | | A | | | | | |
| S02 | Onboarding 2 | | A | | | | | |
| S03 | Onboarding 3 | | A | | | | | |
| S04 | Tạo tài khoản | | A | | | | | |
| S05 | Thiết lập 1/3 | | A | | | | | |
| S06 | Thiết lập 2/3 | | A | | | | | |
| S07 | Thiết lập 3/3 | | A | | | | | |
| S08 | Trang chủ | | B | | | | | |
| S09 | Trang chủ (cuộn) | | B | | | | | |
| S10 | Kế hoạch tuần | | B | | | | | |
| S11 | Mua sắm | | C | | | | | |
| S12 | Mua sắm (cuộn) | | C | | | | | |
| S13 | Lịch sử | | B | | | | | |
| S14 | Hồ sơ | | A | | | | | |

## Các điểm đã thấy trên bản thiết kế hiện tại
Khi đối chiếu nên xem lại các chi tiết nhỏ này trong Figma:
- S06: tên mục và mô tả đang dính sát nhau ("Eat balanced meals" và mô tả cùng một dòng), nên kiểm tra lại khoảng cách hoặc xuống dòng.
- S05, S06, S07: nút Continue sát đáy và lệch so với khung, kiểm tra vùng an toàn (safe area).
- S11–S12: đang dùng ảnh chụp ở các độ rộng khác nhau (370, 333 px), nên thống nhất một kích thước khung khi xuất.
