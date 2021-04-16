package com.roy.shortener.base.domains;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_origin", columnNames = {"origin"})
    }
)
public class Url implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "VARCHAR(1000) NOT NULL COMMENT '변경전 주소'")
  private String origin;

  @Builder.Default
  @Column(columnDefinition = "INT(20) DEFAULT 0 COMMENT '변경 요청 횟수'")
  private Integer requestedCount = 0;

}
