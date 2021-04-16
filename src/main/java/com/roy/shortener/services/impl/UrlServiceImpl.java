package com.roy.shortener.services.impl;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.dtos.UrlShortenResponseDTO;
import com.roy.shortener.base.enums.ExceptionCode;
import com.roy.shortener.base.exceptions.DataNotFoundException;
import com.roy.shortener.base.exceptions.InvalidParameterException;
import com.roy.shortener.base.utils.UrlTranslator;
import com.roy.shortener.repositories.UrlRepository;
import com.roy.shortener.services.UrlService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

  private final UrlRepository urlRepository;
  private final UrlTranslator urlTranslator;

  @Override
  public UrlShortenResponseDTO shortenUrl(String originUrl) throws Exception {

    if (Boolean.FALSE == isValidUrl(originUrl)) {
      throw new InvalidParameterException(ExceptionCode.INVALID_URL);
    }

    Url url = updateOrSaveUrl(originUrl);

    return UrlShortenResponseDTO.builder()
        .originUrl(originUrl)
        .shortenUrl(urlTranslator.urlEncoder(url.getId()))
        .requestedCount(url.getRequestedCount())
        .build();
  }

  @Override
  public boolean isValidUrl(String url) {

    url = removeProtocol(url);

    Pattern pattern = Pattern.compile("^(?:www\\.)?[a-zA-Z0-9./]+$");
    Matcher matcher = pattern.matcher(url);
    boolean isContainDot = url.contains(".");

    return (matcher.matches() == Boolean.TRUE) && (isContainDot == Boolean.TRUE);
  }

  @Override
  public Url updateOrSaveUrl(String originUrl) {

    Url storedUrl = urlRepository.findTopByOrigin(originUrl).orElseGet(() ->
        Url.builder()
            .origin(originUrl)
            .requestedCount(0)
            .build());

    storedUrl.setRequestedCount(storedUrl.getRequestedCount() + 1);

    return urlRepository.save(storedUrl);

  }

  @Override
  public String findOriginUrl(String shortenedUrl) throws Exception {

    long rowId = urlTranslator.urlDecoder(shortenedUrl);
    Url storedUrl = urlRepository.findById(rowId)
        .orElseThrow(() -> new DataNotFoundException(ExceptionCode.DATA_NOT_FOUND));

    return storedUrl.getOrigin();

  }

  @Override
  public String removeProtocol(String originUrl) {

    String[] protocols = {"http://", "https://"};

    for (String protocol : protocols) {
      originUrl = originUrl.replace(protocol, "");
    }

    return originUrl;
  }
}
