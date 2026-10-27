# 03 – Quy trình làm việc nhóm (3 người)

## 1. Vai trò và phân công
| Vai | Người | Màn hình / phần việc |
|---|---|---|
| **A – Trưởng nhóm, tài khoản, thông báo** | _(tên)_ | S01 Làm quen, S02 Tạo tài khoản, S03 Thiết lập, S13 Thông báo, S13b thông báo hệ thống, S14 Cài đặt thông báo, S16 Cá nhân, Nguồn hình ảnh, theme, khởi tạo repo |
| **B – Thực đơn** | _(tên)_ | S04 Trang chủ, S05 Tạo thực đơn, S06 AI đang tạo, S07 Thực đơn tuần, S08 Chi tiết món, S12 Yêu thích, thanh điều hướng đáy |
| **C – Mua sắm và thống kê** | _(tên)_ | S09 Mua sắm, S10 Tủ bếp, S11 Dinh dưỡng, S12b Lịch sử, S15 Thống kê chi phí (6 trạng thái), logic `ShoppingListBuilder` |

Mỗi người chỉ sửa trong thư mục màn hình của mình (`ui/screens/...`).
File dùng chung (`navigation/`, `ui/theme/`, `ui/components/`, `data/model/`) sửa bằng PR nhỏ và báo cả nhóm.

## 2. Git
- `main` luôn build được, **không push trực tiếp**.
- Mỗi việc một nhánh: `feature/S04-trang-chu`, `fix/shopping-checkbox`, `docs/tuan3`.
- Commit có tiền tố: `feat:`, `fix:`, `docs:`, `style:`, `refactor:`, `test:`.
  Ví dụ: `feat(home): thêm thẻ thực đơn hôm nay`.
- Mở Pull Request, **có ít nhất 1 người khác review** rồi mới Squash and merge.
- Trước khi tạo nhánh mới luôn `git pull` trên `main`.

Trong Android Studio: menu **Git → Pull / New Branch / Commit / Push**, hoặc dùng terminal:
```bash
git checkout main && git pull
git checkout -b feature/S04-trang-chu
git add . && git commit -m "feat(home): thêm thẻ thực đơn hôm nay"
git push -u origin feature/S04-trang-chu
```
Không commit các file sinh ra khi build (`build/`, `.gradle/`, `local.properties`) – đã có trong `.gitignore`.

## 3. Quản lý việc
GitHub Projects dạng Kanban: To do → Doing → Review → Done. Mỗi màn hình trong `02-danh-sach-man-hinh.md` là một issue, gán người phụ trách.

## 4. Lộ trình gợi ý (chỉnh theo lịch của thầy/cô)
| Giai đoạn | Việc |
|---|---|
| Đã xong | Ý tưởng, phân tích, thiết kế Figma, Việt hóa, thông báo, thống kê chi phí, nguồn hình ảnh |
| Giai đoạn 1 | Khởi tạo project Android Studio, theme, điều hướng, component dùng chung, dữ liệu mẫu |
| Giai đoạn 2 | Dựng giao diện các màn hình theo phân công |
| Giai đoạn 3 | Logic: tạo thực đơn, `ShoppingListBuilder`, tủ bếp, tính ngân sách, thống kê |
| Giai đoạn 4 | Lưu dữ liệu (Room/DataStore), thông báo nhắc nhở (WorkManager) |
| Giai đoạn 5 | Ghép nối, sửa lỗi, đối chiếu UI (`05-doi-chieu-ui.md`) |
| Cuối kỳ | Demo, báo cáo |

## 5. Khởi tạo project (trưởng nhóm làm một lần)
1. Android Studio → **New Project → Empty Activity** (Jetpack Compose).
2. Name: `MealMate`, Package name: **`vn.edu.uth.mealmate`**, Minimum SDK: API 26, Language: Kotlin.
3. Giải nén bộ khung và chép đè các thư mục `app/src/...`, `docs/`, `.github/` vào project (ghi đè `MainActivity.kt`, `ui/theme/Color.kt`, `ui/theme/Theme.kt`).
4. Thêm hai thư viện vào `app/build.gradle.kts` (mục `dependencies`) rồi bấm **Sync Now**:
   ```kotlin
   implementation("androidx.navigation:navigation-compose:2.8.9")
   implementation("androidx.compose.material:material-icons-extended")
   ```
5. Chạy thử trên emulator, rồi `git init`, commit, đẩy lên repo `MealMate_mobile_app` và mời hai bạn còn lại.
