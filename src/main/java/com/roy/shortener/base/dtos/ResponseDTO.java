package com.roy.shortener.base.dtos;

import com.roy.shortener.base.exceptions.AbstractException;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseDTO<T> implements Serializable {

  private int code = HttpStatus.OK.value();

  private T response;

  public ResponseDTO(T response) {
    this.response = response;
  }

  public ResponseDTO(AbstractException exception) {
    this.code = exception.getCode().getValue();
    this.response = (T) new ResponseExceptionDTO(exception);
  }

  public ResponseDTO(Exception exception) {
    this.code = 500;
    this.response = (T) new ResponseExceptionDTO(exception);
  }
}
