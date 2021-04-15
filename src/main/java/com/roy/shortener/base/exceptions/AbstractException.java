package com.roy.shortener.base.exceptions;

import com.roy.shortener.base.enums.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractException extends Exception {

  ExceptionCode code;

}
