DROP TABLE IF EXISTS `url`;

CREATE TABLE `url`
(
    `id`              BIGINT(20)    NOT NULL AUTO_INCREMENT,
    `origin`          VARCHAR(1000) NOT NULL COMMENT '변경전 주소',
    `requested_count` INT(20) DEFAULT 1 COMMENT '변환 요청 횟수',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_origin` (`origin`)
);
