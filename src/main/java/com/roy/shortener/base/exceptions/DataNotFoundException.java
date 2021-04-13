package com.roy.shortener.base.exceptions;

import com.roy.shortener.base.enums.ExceptionCode;

public class DataNotFoundException extends AbstractException {

  public DataNotFoundException(ExceptionCode code) {
    super(code);
  }

}
