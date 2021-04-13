package com.roy.shortener.base.domains;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_origin", columnNames = {"origin"})
    }
)
public class URL implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '변경전 주소'")
  private String origin;

  @Column(columnDefinition = "INT(20) DEFAULT 1 COMMENT '변경 요청 횟수'")
  private Integer requestedCount;

  @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT current_timestamp() COMMENT '데이터 생성 일시'")
  private LocalDateTime createdAt;

}
