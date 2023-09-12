CREATE TABLE `PLM_FILE`
(
    `unique_name`        VARCHAR(255) NOT NULL,
    `file_type`          VARCHAR(255) NOT NULL,
    `mime_type`          VARCHAR(255) NULL,
    `file_size`          DECIMAL(19, 0) NULL,
    `file_original_name` VARCHAR(255) NULL,
    `file_extension`     VARCHAR(10) NULL,
    `checksum`           VARCHAR(255) NULL,
    `creation_date`      TIMESTAMP    NOT NULL,
    PRIMARY KEY (`unique_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `PLM_FILE_DATA`
(
    `unique_name` VARCHAR(255) NOT NULL PRIMARY KEY,
    `data`        MEDIUMBLOB   NOT NULL,
    FOREIGN KEY (unique_name) REFERENCES PLM_FILE (unique_name)
) DEFAULT CHARSET = utf8;

CREATE TABLE `SWC_USER_FILE`
(
    `user_id`          bigint(20) NOT NULL,
    `picture_unique_name` VARCHAR(255) NOT NULL,
    `excel_unique_name` VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES PLM_USER (id),
    FOREIGN KEY (picture_unique_name) REFERENCES PLM_FILE (unique_name),
    FOREIGN KEY (excel_unique_name) REFERENCES PLM_FILE (unique_name)
) DEFAULT CHARSET = utf8;

-- DATA EXAMPLE

INSERT INTO PLM_FILE
VALUES ('e58ed763-928c-4155-bee9-fdbaaadc15f3.xlsx',
        'EXCEL',
        'application/vnd.ms-excel',
        1,
        'numbers.xlsx',
        'xlsx',
        'random_checksum',
        CURRENT_TIME());

INSERT INTO PLM_FILE_DATA
VALUES ('e58ed763-928c-4155-bee9-fdbaaadc15f3.xlsx',
        RAWTOHEX('Test'));

INSERT INTO PLM_FILE
VALUES ('b2dfabaa-5171-11ee-be56-0242ac120002.png', 'PICTURE', 'image/png', 1, 'beach.png', 'png',
        'random_checksum', CURRENT_TIME());

INSERT INTO PLM_FILE_DATA
VALUES ('b2dfabaa-5171-11ee-be56-0242ac120002.png',
        RAWTOHEX('Test'));

INSERT INTO SWC_USER_FILE
VALUES (
           1,
           'b2dfabaa-5171-11ee-be56-0242ac120002.png',
           'e58ed763-928c-4155-bee9-fdbaaadc15f3.xlsx'
       );

