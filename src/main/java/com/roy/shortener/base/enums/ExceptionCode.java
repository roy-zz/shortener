package com.roy.shortener.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

  // @formatter:off
  INVALID_URL(10000),
  DATA_NOT_FOUND(10001);
  // @formatter:on

  int value;

}
