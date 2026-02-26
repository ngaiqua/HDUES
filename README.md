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
    │   │               │   ├── AdminController.java
    │   │               │   └── ClassroomController.java
    │   │               ├── dto/             # (Data Transfer Objects) Đối tượng truyền dữ liệu giữa các tầng.
    │   │               │   ├── SettingDTO.java
    │   │               │   ├── SubjectDTO.java
    │   │               │   ├── SyllabusDTO.java
    │   │               │   └── UserDTO.java
    │   │               ├── entity/          # (Entities) Ánh xạ tới các bảng trong cơ sở dữ liệu.
    │   │               │   └── Setting.java
    │   │               ├── service/         # (Services) Chứa business logic của ứng dụng.
    │   │               │   ├── ...
    │   │               └── repository/      # (Repositories) Tương tác với cơ sở dữ liệu.
    │   │                   ├── ...
    │   │
    │   └── resources/
    │       └── ...
```

### Roles of Directories

-   `controller`: Handles incoming HTTP requests and maps them to the appropriate service methods.
-   `dto`: Data Transfer Objects used to shape incoming and outgoing data for APIs.
-   `entity`: JPA entities that map to database tables.
-   `service`: Contains the core business logic of the application.
-   `repository`: Manages data access and persistence with the database.
-   `templates`: The root for all Thymeleaf view templates.

### Architectural Decisions

#### Why `Setting.java` Entity?

The `Setting` entity was introduced to manage dynamic configurations and options within the application without hardcoding them. This provides several key advantages:

1.  **Flexibility:** System administrators can add, remove, or modify options (like user roles, semester types, or notification preferences) directly through an admin interface without needing to redeploy the application.
2.  **Maintainability:** It centralizes all system-wide parameters in one place in the database, making them easy to manage and audit.
3.  **Scalability:** As the application grows, new types of settings can be easily added by simply inserting new records with a new `type`, ensuring the system can adapt to future requirements.
