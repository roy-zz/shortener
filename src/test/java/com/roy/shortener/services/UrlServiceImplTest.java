package com.roy.shortener.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.exceptions.DataNotFoundException;
import com.roy.shortener.base.utils.UrlTranslator;
import com.roy.shortener.repositories.UrlRepository;
import com.roy.shortener.services.impl.UrlServiceImpl;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@ExtendWith(MockitoExtension.class)
@DisplayName("URL 서비스 단위 테스트")
class UrlServiceImplTest {

  @Mock
  private UrlRepository urlRepository;

  @Mock
  private UrlTranslator urlTranslator;

  @InjectMocks
  private UrlServiceImpl urlService;
  
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

  @Test
  @DisplayName("단축된 URL 저장 및 업데이트 테스트")
  void updateOrSaveUrl() {

    final String originUrl = "www.google.com";

    // CASE - 1
    // 같은 URL을 저장하려하면 새로 저장하지 않고 requested_count만 업데이트한다.
    Url urlForCase1 = Url.builder()
        .id(1L)
        .origin(originUrl)
        .requestedCount(10)
        .build();

    Url expectedResultForCase1 = Url.builder()
        .id(1L)
        .origin(originUrl)
        .requestedCount(11)
        .build();

    given(this.urlRepository.findTopByOrigin(originUrl))
        .willReturn(Optional.of(urlForCase1));

    when(this.urlRepository.save(any()))
        .thenAnswer(new Answer<Url>() {
          public Url answer(InvocationOnMock invocation) {
            Object[] arguments = invocation.getArguments();
            return (Url) arguments[0];
          }
        });

    Assertions.assertEquals(expectedResultForCase1, this.urlService.updateOrSaveUrl(originUrl));

    // CASE - 2
    // 같은 URL이 없는 경우 새로운 엔티티를 생성하여 저장한다.
    Url expectedResultForCase2 = Url.builder()
        .origin(originUrl)
        .requestedCount(1)
        .build();

    given(this.urlRepository.findTopByOrigin(originUrl))
        .willReturn(Optional.empty());

    Assertions.assertEquals(expectedResultForCase2, this.urlService.updateOrSaveUrl(originUrl));

  }

  @Test
  @DisplayName("URL 조회 테스트")
  void findOriginUrl() throws Exception {

    // CASE - 1
    // 입력받은 URL 조회가 가능한 경우
    String urlForCase1 = "localhost:8888/1";
    long rowIdForCase1 = 1L;
    String originUrlForCase1 = "www.google1.com";
    Url urlEntityForCase1 = Url.builder()
        .origin(originUrlForCase1)
        .build();

    given(this.urlTranslator.urlDecoder(urlForCase1))
        .willReturn(rowIdForCase1);
    given(this.urlRepository.findById(rowIdForCase1))
        .willReturn(Optional.of(urlEntityForCase1));

    Assertions.assertEquals(originUrlForCase1, this.urlService.findOriginUrl(urlForCase1));

    // CASE - 2
    // 입력받은 URL 조회가 불가능한 경우
    String urlForCase2 = "localhost:8888/2";
    long rowIdForCase2 = 2L;
    given(this.urlTranslator.urlDecoder(urlForCase2))
        .willReturn(rowIdForCase2);
    given(this.urlRepository.findById(rowIdForCase2))
        .willReturn(Optional.empty());

    Assertions.assertThrows(DataNotFoundException.class,
        () -> this.urlService.findOriginUrl(urlForCase2));

  }

  @Test
  @DisplayName("프로토콜 리무버 테스트")
  void removeProtocolTest() {

    String urlForCase1 = "http://www.google.com";
    String expectedResultForCase1 = "www.google.com";

    Assertions.assertEquals(expectedResultForCase1, this.urlService.removeProtocol(urlForCase1));

    String urlForCase2 = "https://www.google.com";
    String expectedResultForCase2 = "www.google.com";

    Assertions.assertEquals(expectedResultForCase2, this.urlService.removeProtocol(urlForCase2));

  }
}