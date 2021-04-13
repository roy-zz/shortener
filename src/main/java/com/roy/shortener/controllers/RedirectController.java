package com.roy.shortener.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RedirectController {

  @RequestMapping("/{URL}")
  public String redirect(@PathVariable String URL) {
    return "redirect:http://www.naver.com";
  }
}
