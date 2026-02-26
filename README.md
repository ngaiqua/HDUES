# HDUES E-Learning Project

This is the official repository for the HDUES E-Learning platform, a modern web application built with Spring Boot and Thymeleaf.

## Iteration 1 - Project Structure

Below is the standard directory structure for the project as of Iteration 1. This structure is designed to be scalable and maintainable.

```
HDUES/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── project/
    │   │               ├── ProjectApplication.java
    │   │               ├── controller/      # (Controllers) Xử lý các HTTP requests từ client.
    │   │               │   └── ClassroomController.java
    │   │               ├── service/         # (Services) Chứa business logic của ứng dụng.
    │   │               │   ├── AssignmentService.java
    │   │               │   ├── MaterialService.java
    │   │               │   └── QuizService.java
    │   │               └── repository/      # (Repositories) Tương tác với cơ sở dữ liệu.
    │   │                   ├── AssignmentRepository.java
    │   │                   ├── MaterialRepository.java
    │   │                   └── QuizRepository.java
    │   │
    │   └── resources/
    │       ├── static/              # Chứa các tài sản tĩnh (CSS, JS, Images).
    │       └── templates/           # Chứa các template view của Thymeleaf.
    │           ├── fragments/       # (Fragments) Các thành phần UI tái sử dụng (header, footer...).
    │           │   ├── footer.html
    │           │   ├── head.html
    │           │   ├── header.html
    │           │   └── scripts.html
    │           │
    │           ├── layouts/         # (Layouts) Bộ khung (template) chính cho các trang.
    │           │   └── layout.html
    │           │
    │           └── pages/           # (Pages) Các trang con cụ thể của ứng dụng.
    │               └── classroom/   # Chứa các trang liên quan đến lớp học.
    │                   └── dashboard.html
    │
    └── test/
        └── java/
```

### Roles of Directories

-   `controller`: Handles incoming HTTP requests and maps them to the appropriate service methods.
-   `service`: Contains the core business logic of the application.
-   `repository`: Manages data access and persistence with the database.
-   `templates`: The root for all Thymeleaf view templates.
    -   `fragments`: Reusable UI components like headers, footers, and sidebars.
    -   `layouts`: The main application layout/template that other pages will inherit from.
    -   `pages`: Specific views for different parts of the application (e.g., classroom dashboard, course details).
