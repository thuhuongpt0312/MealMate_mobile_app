# 03 – Quy trình làm nhóm

## 1. Nguyên tắc
- Mỗi người làm trên **nhánh riêng**, không đẩy thẳng lên `main`.
- Mỗi người phụ trách các màn hình ghi ở file 02, để tránh sửa trùng file.
- Phần dùng chung (màu, font, nút, thanh điều hướng) do **C** làm trước và merge sớm.

## 2. Nhánh Git
```
main      ← bản ổn định, chỉ merge qua Pull Request
 └─ dev   ← nhánh tích hợp của cả nhóm
     ├─ feature/a-onboarding
     ├─ feature/b-home
     └─ feature/c-shopping
```
Đặt tên nhánh: `feature/<chữ-cái-thành-viên>-<tính-năng>`.

## 3. Một vòng làm việc
1. Cập nhật `dev` mới nhất.
2. Tạo nhánh `feature/...` từ `dev`.
3. Code, commit nhỏ và thường xuyên.
4. Mở Pull Request vào `dev`, điền mẫu PR (có ảnh chụp màn hình app).
5. **Một người khác** xem và duyệt, sau đó merge.
6. Cuối mỗi tuần, merge `dev` vào `main`.

## 4. Quy ước commit
Dạng: `loại: mô tả ngắn`
- `feat: thêm màn hình đăng ký (S04)`
- `fix: sửa lỗi checkbox không lưu`
- `style: chỉnh khoảng cách theo Figma (S10)`
- `docs: cập nhật bảng đối chiếu UI`

## 5. Gợi ý lộ trình (chỉnh theo lịch môn)
| Giai đoạn | Việc chính |
|---|---|
| Tuần 1 | Chọn đề tài, phân tích, hoàn thiện Figma, đưa tài liệu lên GitHub |
| Tuần 2 | Tạo project, theme, thanh điều hướng, dữ liệu mẫu (C làm, A và B xem trước) |
| Các tuần giữa | Mỗi người code màn hình của mình, PR liên tục |
| Gần cuối | Ghép, sửa lỗi, đối chiếu UI (file 05), quay demo |
| Cuối | Nộp, thuyết trình |

## 6. Họp nhóm
Mỗi tuần một buổi 15–20 phút: tuần trước làm gì, tuần này làm gì, đang vướng gì.

## 7. Cách làm trên GitHub không cần lệnh
- Người tạo repo: Settings → Collaborators → Add people để mời 2 bạn còn lại.
- Cài **GitHub Desktop** để clone, tạo nhánh, commit, push bằng nút bấm.
- Mở Pull Request ngay trên trang github.com.
