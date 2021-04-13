package com.roy.shortener.base.exceptions;

import com.roy.shortener.base.enums.ExceptionCode;

public class InvalidParameterException extends AbstractException {

  public InvalidParameterException(ExceptionCode code) {
    super(code);
  }

}
