create table plm_log_api
(
  id            bigint       not null primary key,
  date          datetime    not null,
  method        varchar(255) not null,
  status_code   SMALLINT not null,
  body_request  mediumtext   null,
  body_response mediumtext   null,
  apiName           varchar(255) not null,
  url           TEXT not null
);

create table plm_log_header
(
  `id`         bigint       not null primary key,
  `id_log_api` bigint       not null,
  `type`       varchar(255) not null,
  `name`      varchar(255) not null,
  `value`      TEXT not null,
  CONSTRAINT `plm_log_header_plm_log_api_id_fk` FOREIGN KEY (`id_log_api`)
  REFERENCES `plm_log_api` (`id`)
);
