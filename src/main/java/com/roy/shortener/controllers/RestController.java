package com.roy.shortener.controllers;

import com.roy.shortener.base.dtos.ResponseDTO;
import com.roy.shortener.base.exceptions.AbstractException;
import com.roy.shortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class RestController {

  private final UrlService urlService;

  @GetMapping(value = "/shorten", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseDTO shortenUrl(@RequestParam String originUrl) throws Exception {

    try {
      return new ResponseDTO(urlService.shortenUrl(originUrl));
    } catch (AbstractException exception) {
      return new ResponseDTO(exception);
    } catch (Exception exception) {
      return new ResponseDTO(exception);
    }
  }
}
