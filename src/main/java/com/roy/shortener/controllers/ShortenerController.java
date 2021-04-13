package com.roy.shortener.controllers;

import com.roy.shortener.services.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class ShortenerController {

  private final URLService urlService;

  @GetMapping(value = "/shorten")
  public String shortenUrl(@RequestParam String originURL) throws Exception {
    return urlService.shortenURL(originURL);
  }
}
