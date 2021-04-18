package com.roy.shortener.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

  // @formatter:off
  INVALID_URL(10000),
  DATA_NOT_FOUND(10001),
  OUT_OF_LENGTH(10002);
  // @formatter:on

  int value;

}
