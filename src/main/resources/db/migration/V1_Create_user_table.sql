CREATE TABLE USER(
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id VARCHAR(100),
    NAME VARCHAR(50),
    token CHAR(36),
    gmt_create BIGINT,
    gmt_modified BIGINT
    );