package com.roy.shortener.services.impl;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.dtos.UrlShortenResponseDTO;
import com.roy.shortener.base.enums.ExceptionCode;
import com.roy.shortener.base.exceptions.DataNotFoundException;
import com.roy.shortener.base.exceptions.InvalidParameterException;
import com.roy.shortener.base.exceptions.OutOfLengthException;
import com.roy.shortener.base.utils.UrlTranslator;
import com.roy.shortener.repositories.UrlRepository;
import com.roy.shortener.services.UrlService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

  private final UrlRepository urlRepository;
  private final UrlTranslator urlTranslator;
  private final String URL_PREFIX = "localhost:8888/";

  @Override
  @Transactional
  public UrlShortenResponseDTO shortenUrl(String originUrl) throws Exception {

    if (Boolean.FALSE == isValidUrl(originUrl)) {
      throw new InvalidParameterException(ExceptionCode.INVALID_URL);
    }

    Url url = updateOrSaveUrl(this.removeProtocol(originUrl));

    return UrlShortenResponseDTO.builder()
        .originUrl(originUrl)
        .shortenUrl(String.format("%s%s", URL_PREFIX, urlTranslator.urlEncoder(url.getId())))
        .requestedCount(url.getRequestedCount())
        .build();


  }

  @Override
  public boolean isValidUrl(String url) {

    Pattern pattern = Pattern.compile(
        "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]{2,6}(\\:[0-9]+)?(\\/\\S*)?$");
    Matcher matcher = pattern.matcher(url);
    boolean isContainDot = url.contains(".");

    return (matcher.matches() == Boolean.TRUE) && (isContainDot == Boolean.TRUE);
  }

  @Override
  public Url updateOrSaveUrl(String originUrl) throws Exception {

    Url storedUrl = urlRepository.findTopByOrigin(originUrl).orElseGet(() ->
        Url.builder()
            .origin(originUrl)
            .requestedCount(0)
            .build());

    storedUrl.setRequestedCount(storedUrl.getRequestedCount() + 1);

    Url savedUrl = urlRepository.save(storedUrl);

    if (urlTranslator.urlEncoder(savedUrl.getId()).length() > 8) {
      throw new OutOfLengthException(ExceptionCode.OUT_OF_LENGTH);
    }

    return savedUrl;

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
