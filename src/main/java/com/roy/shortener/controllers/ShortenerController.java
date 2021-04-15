package com.roy.shortener.controllers;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.dtos.ResponseDTO;
import com.roy.shortener.base.exceptions.AbstractException;
import com.roy.shortener.services.UrlService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class ShortenerController {

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

  @GetMapping(value = "/shortend", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Url> listUrl() {
    return urlService.listUrls();
  }
}
