package com.roy.shortener.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roy.shortener.base.dtos.ResponseDTO;
import com.roy.shortener.base.enums.ExceptionCode;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("URL 단축 서비스 통합 테스트")
public class ShortenerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private final String originUrl = "www.google.com";

  private final String shortenUrl = "/url/shorten";

  private static String originUrlByTestOrder1;

  private static String shortenUrlByTestOrder1;

  private static int requestedCountByTestOrder1;

  @Test
  @Order(1)
  @DisplayName("정상 URL 테스트")
  void normalUrlShortenTest() throws Exception {

    final MvcResult mvcResult = mockMvc
        .perform(get(shortenUrl)
            .param("originUrl", originUrl)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    final ResponseDTO responseDTO = objectMapper
        .readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);

    LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) responseDTO
        .getResponse();

    originUrlByTestOrder1 = (String) response.get("originUrl");
    shortenUrlByTestOrder1 = (String) response.get("shortenUrl");
    requestedCountByTestOrder1 = (int) response.get("requestedCount");

  }

  @Test
  @Order(2)
  @DisplayName("동일 URL 테스트")
  void duplicatedUrlShortenTest() throws Exception {

    // 1. 동일한 단축된 URL RETURN
    // 2. requestedCount 증가
    final MvcResult mvcResult = mockMvc
        .perform(get(shortenUrl)
            .param("originUrl", originUrl)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    final ResponseDTO responseDTO = objectMapper
        .readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);

    LinkedHashMap<String, Object> response = (LinkedHashMap<String, Object>) responseDTO
        .getResponse();

    Assertions.assertEquals(originUrlByTestOrder1, response.get("originUrl"));
    Assertions.assertEquals(shortenUrlByTestOrder1, response.get("shortenUrl"));
    Assertions.assertEquals((int) response.get("requestedCount"), requestedCountByTestOrder1 + 1);

  }

  @Test
  @Order(3)
  @DisplayName("무효한 URL 테스트")
  void invalidUrlShortenTest() throws Exception {

    final String invalidUrl = "www";

    final MvcResult mvcResult = mockMvc
        .perform(get(shortenUrl)
            .param("originUrl", invalidUrl)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

    final ResponseDTO responseDTO = objectMapper
        .readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);

    Assertions.assertEquals(ExceptionCode.INVALID_URL.getValue(), responseDTO.getCode());

  }

  @Test
  @Order(4)
  @DisplayName("Redirect URL 테스트")
  void notExistRedirectUrlTest() throws Exception {

    final ResultActions resultActions = mockMvc.perform(get("http://" + shortenUrlByTestOrder1));

    resultActions.andExpect(redirectedUrl("http://" + originUrl));

  }
}
