package com.roy.shortener.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

  // @formatter:off
  DATA_NOT_FOUND(30000);
  // @formatter:on

  int value;

}
