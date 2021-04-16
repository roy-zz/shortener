package com.roy.shortener.repositories;

import com.roy.shortener.base.configurations.flyway.FlywayConfiguration;
import com.roy.shortener.base.domains.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@Import(FlywayConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@DisplayName("URL 리포지토리 테스트")
class UrlRepositoryTest {

  private final String originUrl = "https://www.google.com";

  private static Long storedId = null;

  @Autowired
  private UrlRepository urlRepository;

  @Test
  @Order(1)
  @Rollback(false)
  @DisplayName("URL INSERT 테스트")
  void urlInsertTest() {

    Url urlEntityForCase1 = Url.builder()
        .origin(originUrl)
        .build();

    Url storedUrlEntity1 = this.urlRepository.save(urlEntityForCase1);

    Assertions.assertEquals(storedUrlEntity1.getOrigin(), urlEntityForCase1.getOrigin());

    storedId = storedUrlEntity1.getId();

  }

  @Test
  @Order(2)
  @Rollback(false)
  @DisplayName("URL SELECT 테스트")
  void urlSelectTest() {

    Url storedUrlEntity1 = this.urlRepository.findTopByOrigin(originUrl).get();

    Assertions.assertEquals(storedId, storedUrlEntity1.getId());

  }
}