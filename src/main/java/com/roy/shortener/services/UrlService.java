package com.roy.shortener.services;

import com.roy.shortener.base.domains.Url;
import com.roy.shortener.base.dtos.UrlShortenResponseDTO;
import com.roy.shortener.base.enums.ExceptionCode;
import com.roy.shortener.base.exceptions.InvalidParameterException;
import com.roy.shortener.base.utils.UrlTranslator;
import com.roy.shortener.repositories.UrlRepository;
import java.util.List;
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
  private final UrlTranslator urlTranslator;

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

  public boolean isValidUrl(String url) {

    Pattern pattern = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
    Matcher matcher = pattern.matcher(url);
    boolean isContainDot = url.contains(".");

    return (matcher.matches() == Boolean.TRUE) && (isContainDot == Boolean.TRUE);
  }

  public Url updateOrSaveUrl(String originUrl) {

    Url storedUrl = urlRepository.findTopByOrigin(originUrl).orElseGet(() ->
        Url.builder()
            .origin(originUrl)
            .requestedCount(0)
            .build());

    storedUrl.setRequestedCount(storedUrl.getRequestedCount() + 1);

    return urlRepository.save(storedUrl);

  }
  
  public List<Url> listUrls() {
    return urlRepository.findAll();
  }
}
