
DROP TABLE IF EXISTS `url`;

CREATE TABLE `url` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `origin` varchar(255) NOT NULL COMMENT '변경전 주소',
    `requested_count` int(20) DEFAULT 1 COMMENT '변환 요청 횟수',
    `created_at` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '데이터 생성 일시',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_origin` (`origin`)
);
