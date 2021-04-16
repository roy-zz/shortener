package com.roy.shortener.services;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.dtos.UrlShortenResponseDTO;

public interface UrlService {

  UrlShortenResponseDTO shortenUrl(String originUrl) throws Exception;

  boolean isValidUrl(String url);

  Url updateOrSaveUrl(String originUrl);

  String findOriginUrl(String shortenedUrl) throws Exception;

  String removeProtocol(String originUrl);
}
