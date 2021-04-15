package com.roy.shortener.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

  @GetMapping("/shorten")
  public String shorten() {
    return "shorten";
  }

  @RequestMapping("/")
  public String redirect(@PathVariable String URL) {
    return "redirect:http://www.naver.com";
  }
}
