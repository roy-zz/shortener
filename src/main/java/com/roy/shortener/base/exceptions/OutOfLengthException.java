package com.roy.shortener.base.exceptions;

import com.roy.shortener.base.enums.ExceptionCode;

public class OutOfLengthException extends AbstractException {

  public OutOfLengthException(ExceptionCode code) {
    super(code);
  }

}
