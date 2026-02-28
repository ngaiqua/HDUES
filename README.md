# Dự án E-Learning HDUES

Đây là repository chính thức cho nền tảng E-Learning của HDUES, một ứng dụng web hiện đại được xây dựng bằng Spring Boot và Thymeleaf.

---

## Iteration 1 - Tiến độ & Cấu trúc

Tài liệu này mô tả cấu trúc và những thành tựu của dự án sau khi kết thúc Iteration 1, đóng vai trò là kim chỉ nam cho tất cả thành viên trong nhóm.

### 1. Changelog & Các tính năng đã hoàn thành

Công việc nền tảng cho dự án đã hoàn tất, giải quyết tất cả các mục tiêu kiến trúc chính cho iteration đầu tiên.

#### Đăng nhập Google (OAuth2) & Phân quyền
-   [x] **Login with Google:** Đăng nhập bằng tài khoản Google (Spring Security OAuth2).
-   [x] **4 vai trò:** Student, Lecturer, Training Department, Admin — mỗi vai trò có dashboard riêng.
-   [x] **Trang đăng nhập:** Layout 2 cột (trái: logo Trường Đại học Hải Dương, phải: nền xanh + nút "Login with Google").
-   [x] **Admin mặc định:** Email `dohoanganh28072004@gmail.com` luôn có vai trò Admin và được phép đăng nhập.
-   [x] **Duyệt email (Admin & Phòng đào tạo):** Chỉ email nằm trong danh sách "allowed" mới đăng nhập được (trừ admin). Có trang quản lý danh sách email được duyệt và **Import Excel** để duyệt nhiều tài khoản một lúc (cột 1: email, cột 2: role: STUDENT, LECTURER, TRAINING_DEPARTMENT, ADMIN).

-   [x] **Giải quyết mâu thuẫn Entity:** Đã chuẩn hóa sử dụng entity `Syllabus` và tạo mới entity `Setting` để quản lý các cấu hình động.
-   [x] **Xây dựng tầng Backend API:** Đã xây dựng một kiến trúc 3 tầng hoàn chỉnh (Controller, Service, Repository) cho các entity lõi (`User`, `Subject`, `Syllabus`, `Setting`).
-   [x] **Data Transfer Objects (DTOs):** Đã triển khai các DTO với các quy tắc validation chặt chẽ (`@NotNull`, `@Size`, v.v.) để đảm bảo tính toàn vẹn dữ liệu của API.
-   [x] **Tài liệu hóa API:** Đã tích hợp Swagger/OpenAPI để cung cấp tài liệu API tương tác, tự động cho tất cả các endpoint của backend.
-   [x] **Kiến trúc Frontend:** Đã thiết lập một bộ máy template Thymeleaf module hóa, có thể tái sử dụng, với một layout chính và các fragments chia sẻ.

### 2. Cây thư mục (Directory Tree)

Cấu trúc dự án hiện tại như sau:

```
HDUES/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── project/
    │   │               ├── ProjectApplication.java
    │   │               ├── controller/      # (Controller) Xử lý các HTTP request
    │   │               │   ├── AdminController.java
    │   │               │   └── ClassroomController.java
    │   │               ├── dto/             # (DTO) Các đối tượng truyền dữ liệu cho API
    │   │               │   ├── SettingDTO.java
    │   │               │   ├── SubjectDTO.java
    │   │               │   ├── SyllabusDTO.java
    │   │               │   └── UserDTO.java
    │   │               ├── entity/          # (Entity) Ánh xạ tới các bảng trong database
    │   │               │   └── Setting.java
    │   │               ├── service/         # (Service) Chứa logic nghiệp vụ chính
    │   │               │   ├── ...
    │   │               └── repository/      # (Repository) Tầng truy cập dữ liệu
    │   │                   ├── ...
    │   │
    │   └── resources/
    │       ├── static/              # Chứa các file tĩnh (CSS, JS, Images)
    │       └── templates/           # Chứa các view template của Thymeleaf
    │           ├── fragments/       # (Fragment) Các thành phần UI tái sử dụng (header, footer)
    │           │   ├── ...
    │           ├── layouts/         # (Layout) Khung layout chính của trang
    │           │   └── layout.html
    │           └── pages/           # (Page) Các trang con cụ thể của ứng dụng
    │               └── classroom/
    │                   └── dashboard.html
    │
    └── test/
```

### 3. Quyết định Kiến trúc

#### Tại sao lại có Entity `Setting.java`?

Entity `Setting` được đưa vào để quản lý các cấu hình và tùy chọn động của ứng dụng mà không cần phải "hardcode" (ghi cứng vào code). Điều này mang lại nhiều lợi thế quan trọng:

1.  **Linh hoạt (Flexibility):** Quản trị viên (Admin) có thể thêm, xóa, hoặc sửa các tùy chọn (ví dụ: vai trò người dùng, loại học kỳ, trạng thái...) trực tiếp thông qua giao diện quản trị mà không cần phải deploy lại ứng dụng.
2.  **Dễ bảo trì (Maintainability):** Tập trung tất cả các tham số hệ thống vào một nơi duy nhất trong database, giúp chúng dễ dàng được quản lý và kiểm tra.
3.  **Khả năng mở rộng (Scalability):** Khi ứng dụng phát triển, các loại cấu hình mới có thể được thêm vào một cách dễ dàng bằng cách chèn các bản ghi mới với một `type` mới, đảm bảo hệ thống có thể thích ứng với các yêu cầu trong tương lai.

### 4. Hướng dẫn Chạy & Kiểm chứng

Hướng dẫn này giải thích cách chạy ứng dụng và xác minh các công việc đã hoàn thành.

#### a. Kiểm chứng Backend API

Các API backend đã được tài liệu hóa và có thể kiểm thử thông qua Swagger UI.

1.  **Chạy ứng dụng** từ IDE của bạn hoặc bằng lệnh `mvn spring-boot:run`.
2.  **Truy cập Swagger UI:** Mở trình duyệt và điều hướng đến:
    -   `http://localhost:8080/swagger-ui.html`
3.  **Khám phá:** Bây giờ bạn có thể khám phá controller `Admin Management` và thực thi các `GET` endpoint trực tiếp từ trình duyệt để xem dữ liệu live từ ứng dụng.

#### b. Kiểm chứng Frontend Layout

Bộ máy template Thymeleaf có thể được xác minh bằng cách truy cập trang demo.

1.  **Chạy ứng dụng.**
2.  **Truy cập trang Demo:** Mở trình duyệt và điều hướng đến:
    -   `http://localhost:8080/classroom/dashboard`
3.  **Xác minh:** Trang web sẽ render chính xác với header và footer chung, xác nhận rằng hệ thống layout và fragment đang hoạt động như mong đợi.

#### c. Đăng nhập Google & Logo

1.  **Cơ sở dữ liệu:** Tạo database PostgreSQL (mặc định `postgres` hoặc sửa `spring.datasource.url` trong `application.properties`).
2.  **Logo trang đăng nhập:** Đặt logo Trường Đại học Hải Dương vào `src/main/resources/static/images/uhd-logo.png`. Nếu không có file, trang sẽ hiển thị chữ "TRƯỜNG ĐẠI HỌC HẢI DƯƠNG" và "UHD".
3.  **Đăng nhập:** Vào `http://localhost:8080/login` → "Login with Google". Email `dohoanganh28072004@gmail.com` đăng nhập với vai trò Admin. Các email khác cần được Admin/Phòng đào tạo thêm vào danh sách "Duyệt email đăng nhập" (hoặc import Excel) trước khi đăng nhập.

### 5. Code Review & Các bước tiếp theo

-   **Những điểm đã làm tốt:** Dự án hiện có một kiến trúc backend và frontend mạnh mẽ, tách biệt (decoupled), giúp đẩy nhanh tốc độ phát triển trong tương lai.
-   **Đề xuất cho Iteration 2:**
    1.  **Global Exception Handling:** Triển khai một `@ControllerAdvice` để chuẩn hóa các response lỗi của API.
    2.  **Tự động hóa DTO Mapping:** Cân nhắc tích hợp một thư viện như **MapStruct** để giảm code lặp lại khi chuyển đổi giữa Entity và DTO.
