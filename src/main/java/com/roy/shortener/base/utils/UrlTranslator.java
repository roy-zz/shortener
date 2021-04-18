package com.roy.shortener.base.utils;

import org.springframework.stereotype.Component;

@Component
public class UrlTranslator {

  private final String BASE62_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public String urlEncoder(long id) {

    StringBuffer stringBuffer = new StringBuffer();

    while (id > 0) {
      stringBuffer.append(BASE62_CHARACTERS.charAt((int) (id % BASE62_CHARACTERS.length())));
      id /= BASE62_CHARACTERS.length();
    }

    return stringBuffer.toString();

  }

  public long urlDecoder(String url) {

    long sum = 0;
    long power = 1;

    for (int i = 0; i < url.length(); i++) {
      sum += BASE62_CHARACTERS.indexOf(url.charAt(i)) * power;
      power *= BASE62_CHARACTERS.length();
    }

    return sum;
  }


}
