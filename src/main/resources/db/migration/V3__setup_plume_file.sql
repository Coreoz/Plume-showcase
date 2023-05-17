CREATE TABLE `PLM_FILE`
(
    `unique_name`        VARCHAR(255)   NOT NULL,
    `file_type`          VARCHAR(255)   NOT NULL,
    `mime_type`          VARCHAR(255)   NULL,
    `file_size`          DECIMAL(19, 0) NULL,
    `file_original_name` VARCHAR(255)   NULL,
    `file_extension`     VARCHAR(10)    NULL,
    `checksum`           VARCHAR(255)   NULL,
    `creation_date`      TIMESTAMP      NOT NULL,
    PRIMARY KEY (`unique_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `PLM_FILE_DATA`
(
    `unique_name` VARCHAR(255) NOT NULL,
    `data`        BLOB         NOT NULL,
    FOREIGN KEY (unique_name) REFERENCES PLM_FILE (unique_name)
)
    DEFAULT CHARSET = utf8;

CREATE TABLE `PLM_USER_FILE`
(
    `user_id`          bigint(20)   NOT NULL,
    `file_unique_name` VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES PLM_USER (id),
    FOREIGN KEY (file_unique_name) REFERENCES PLM_FILE (unique_name)
)
    DEFAULT CHARSET = utf8;
