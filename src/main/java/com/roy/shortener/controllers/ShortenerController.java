package com.roy.shortener.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortener")
public class ShortenerController {

  @PostMapping(value = "/shorten", consumes = MediaType.APPLICATION_JSON_VALUE)
  public String shortenUrl() {
    return null;
  }
}
