-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS MessageProgram;
USE MessageProgram;

-- USER 테이블 생성
CREATE TABLE IF NOT EXISTS USER (
    user_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    is_online BOOLEAN DEFAULT FALSE,
    work_status VARCHAR(50) DEFAULT '업무 중',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- SESSION 테이블 생성
CREATE TABLE IF NOT EXISTS SESSION (
    session_id VARCHAR(128) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expired_at DATETIME NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE
);

-- MESSAGE 테이블 생성
CREATE TABLE IF NOT EXISTS MESSAGES (
    message_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    chatroom_id INT NOT NULL,
    sender_id VARCHAR(20) NOT NULL,
    sent DATETIME NOT NULL,
    is_system TINYINT(1),
    content TEXT
);

-- CHATROOM 테이블 생성
CREATE TABLE IF NOT EXISTS CHATROOM (
    chatroom_id   INT           AUTO_INCREMENT PRIMARY KEY,
    parentroom_id INT           DEFAULT NULL,
    closed        BOOLEAN       DEFAULT FALSE,
    is_private    BOOLEAN       DEFAULT FALSE,
    title         VARCHAR(255)  NOT NULL,
    created_at    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP

    FOREIGN KEY (parentroom_id) REFERENCES CHATROOM(chatroom_id)
);

CREATE TABLE IF NOT EXISTS CHATROOM_PARTICIPANT(
    chatroom_id INT NOT NULL,
    user_id VARCHAR(20) NOT NULL,

    PRIMARY KEY (chatroom_id, user_id),
    FOREIGN KEY (chatroom_id) REFERENCES CHATROOM(chatroom_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES USER(user_id) ON DELETE CASCADE
)

-- FRIEND 테이블 생성
CREATE TABLE IF NOT EXISTS FRIEND (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(30) NOT NULL,
    friend_id VARCHAR(30) NOT NULL,
    is_blocked BOOLEAN DEFAULT FALSE,
    is_pending BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(user_id),
    CONSTRAINT fk_friend FOREIGN KEY (friend_id) REFERENCES user(user_id)
);

-- SCHEDULE 테이블 생성
CREATE TABLE IF NOT EXISTS SCHEDULE (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    chatroom_id INT NOT NULL,
    title VARCHAR(50),
    schedule_date VARCHAR(14),
    schedule_time VARCHAR(8),
    memo TEXT
)