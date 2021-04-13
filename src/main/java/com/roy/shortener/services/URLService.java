package com.roy.shortener.services;

import com.roy.shortener.repositories.URLRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class URLService {

  private final URLRepository urlRepository;

  public String shortenURL(String originURL) throws Exception {

    return originURL;
    
  }
}
