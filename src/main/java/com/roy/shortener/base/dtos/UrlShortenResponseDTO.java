package com.roy.shortener.base.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenResponseDTO implements Serializable {

  private String shortenUrl;

  private String originUrl;

  private int requestedCount;

}
