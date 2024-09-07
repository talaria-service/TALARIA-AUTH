package com.yonyk.talaria.auth.exception;

import com.yonyk.talaria.auth.exception.exceptionType.ExceptionType;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private final ExceptionType exceptionType;

  public CustomException(ExceptionType exceptionType) {
    super(exceptionType.message());
    this.exceptionType = exceptionType;
  }
}
