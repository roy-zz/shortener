package com.roy.shortener.services;

import com.roy.shortener.repositories.UrlRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

  private final UrlRepository urlRepository;

  public String shortenUrl(String originUrl) throws Exception {

    return originUrl;

  }

  public boolean isValidUrl(String url) {

    Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
    Matcher matcher = pattern.matcher(url);
    boolean isContainDot = url.contains(".");

    return (matcher.matches() == Boolean.TRUE) && (isContainDot == Boolean.TRUE);

  }


}
