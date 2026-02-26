-- ================================
-- ENUM TYPES
-- ================================

CREATE TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TYPE user_role AS ENUM (
    'STUDENT',
    'LECTURER',
    'ADMIN',
    'TRAINING_DEPARTMENT'
);

CREATE TYPE assignment_type AS ENUM ('QUIZ', 'ESSAY');

CREATE TYPE class_member_role AS ENUM ('STUDENT', 'LECTURER');


-- ================================
-- USERS
-- ================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status user_status DEFAULT 'ACTIVE',
    role user_role NOT NULL
);


-- ================================
-- SUBJECT
-- ================================

CREATE TABLE subject (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);


-- ================================
-- CLASSES
-- ================================

CREATE TABLE classes (
    id BIGSERIAL PRIMARY KEY,
    semester VARCHAR(50),
    name VARCHAR(255) NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);


-- ================================
-- CLASS_SUBJECT
-- ================================

CREATE TABLE class_subject (
    id BIGSERIAL PRIMARY KEY,
    class_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
    UNIQUE (class_id, subject_id)
);


-- ================================
-- CLASS_MEMBER
-- ================================

CREATE TABLE class_member (
    id BIGSERIAL PRIMARY KEY,
    class_subject_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role class_member_role NOT NULL,
    FOREIGN KEY (class_subject_id) REFERENCES class_subject(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (class_subject_id, user_id)
);


-- ================================
-- SYLLABUS
-- ================================

CREATE TABLE syllabus (
    id BIGSERIAL PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_by BIGINT,
    updated_by BIGINT,
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);


-- ================================
-- MATERIAL
-- ================================

CREATE TABLE material (
    id BIGSERIAL PRIMARY KEY,
    class_subject_id BIGINT NOT NULL,
    uploaded_by BIGINT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (class_subject_id) REFERENCES class_subject(id) ON DELETE CASCADE,
    FOREIGN KEY (uploaded_by) REFERENCES users(id)
);


-- ================================
-- ASSIGNMENT
-- ================================

CREATE TABLE assignment (
    id BIGSERIAL PRIMARY KEY,
    class_subject_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    type assignment_type NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP,
    created_by BIGINT,
    FOREIGN KEY (class_subject_id) REFERENCES class_subject(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id)
);


-- ================================
-- STUDENT_ASSIGN
-- ================================

CREATE TABLE student_assign (
    id BIGSERIAL PRIMARY KEY,
    assignment_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    score DOUBLE PRECISION,
    submitted_at TIMESTAMP,
    FOREIGN KEY (assignment_id) REFERENCES assignment(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE (assignment_id, user_id)
);


-- ================================
-- QUIZ
-- ================================

CREATE TABLE quiz (
    id BIGSERIAL PRIMARY KEY,
    assignment_id BIGINT NOT NULL,
    title VARCHAR(255),
    description TEXT,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    created_by BIGINT,
    FOREIGN KEY (assignment_id) REFERENCES assignment(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id)
);


-- ================================
-- QUIZ_QUESTION
-- ================================

CREATE TABLE quiz_question (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id) ON DELETE CASCADE
);


-- ================================
-- QUIZ_OPTION
-- ================================

CREATE TABLE quiz_option (
    id BIGSERIAL PRIMARY KEY,
    quiz_question_id BIGINT NOT NULL,
    quiz_answer VARCHAR(255) NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (quiz_question_id) REFERENCES quiz_question(id) ON DELETE CASCADE
);


-- ================================
-- SUBJECT_QUESTION
-- ================================

CREATE TABLE subject_question (
    id BIGSERIAL PRIMARY KEY,
    class_subject_id BIGINT NOT NULL,
    slot INT,
    content TEXT NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    time_open TIMESTAMP,
    time_close TIMESTAMP,
    FOREIGN KEY (class_subject_id) REFERENCES class_subject(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);


-- ================================
-- SUBJECT_DISCUSSION
-- ================================

CREATE TABLE subject_discussion (
    id BIGSERIAL PRIMARY KEY,
    subject_question_id BIGINT NOT NULL,
    answer TEXT NOT NULL,
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_question_id) REFERENCES subject_question(id) ON DELETE CASCADE,
    FOREIGN KEY (created_by) REFERENCES users(id)
);


-- ================================
-- FLASHCARD
-- ================================

CREATE TABLE flashcard (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255),
    description TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- ================================
-- FLASHCARD_QUESTION
-- ================================

CREATE TABLE flashcard_question (
    id BIGSERIAL PRIMARY KEY,
    flashcard_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    FOREIGN KEY (flashcard_id) REFERENCES flashcard(id) ON DELETE CASCADE
);


-- ================================
-- FLASHCARD_ANSWER
-- ================================

CREATE TABLE flashcard_answer (
    id BIGSERIAL PRIMARY KEY,
    flashcard_question_id BIGINT NOT NULL,
    answer TEXT NOT NULL,
    FOREIGN KEY (flashcard_question_id) REFERENCES flashcard_question(id) ON DELETE CASCADE
);