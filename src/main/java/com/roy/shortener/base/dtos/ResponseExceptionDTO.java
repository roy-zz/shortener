package com.roy.shortener.base.dtos;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResponseExceptionDTO implements Serializable {

  private String message;

  public ResponseExceptionDTO(Exception exception) {
    this.message = exception.getMessage();
  }
}
