package com.roy.shortener.controllers;

import com.roy.shortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class ShortenerController {

  private final UrlService urlService;

  @GetMapping(value = "/shorten")
  public String shortenUrl(@RequestParam String originUrl) throws Exception {
    return urlService.shortenUrl(originUrl);
  }
}
