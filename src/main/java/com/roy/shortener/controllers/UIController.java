package com.roy.shortener.controllers;

import com.roy.shortener.services.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UIController {

  private final UrlService urlService;

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/{shortenedUrl:[0-9a-zA-Z]+}")
  public String redirect(@PathVariable String shortenedUrl) throws Exception {

    return String.format("redirect:http://%s", urlService.findOriginUrl(shortenedUrl));

  }
}
