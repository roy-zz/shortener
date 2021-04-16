package com.roy.shortener.unit.services;

import com.roy.shortener.repositories.UrlRepository;
import com.roy.shortener.services.UrlService;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ExtendWith(MockitoExtension.class)
@DisplayName("URL 서비스 단위 테스트")
class UrlServiceTest {

  @Mock
  private UrlRepository urlRepository;

  @InjectMocks
  private UrlService urlService;

  @Test
  @DisplayName("URL 유효성 검사 테스트")
  void isValidUrlTest() {

    String[] trueExpectedUrls = {
        "https://www.google.com",
        "http://www.google.com",
        "www.google.com",
        "ww.google.com",
        "google.co.kr",
    };

    String[] falseExpectedUrls = {
        "http://",
        "https://",
        "www",
        "google",
        ""
    };

    Arrays.stream(trueExpectedUrls).forEach(i ->
        Assertions.assertTrue(this.urlService.isValidUrl(i))
    );

    Arrays.stream(falseExpectedUrls).forEach(i ->
        Assertions.assertFalse(this.urlService.isValidUrl(i))
    );
  }
}