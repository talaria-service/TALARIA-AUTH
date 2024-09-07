package com.yonyk.talaria.auth.exception.exceptionType;

import org.springframework.http.HttpStatus;

public interface ExceptionType {
  HttpStatus status();

  String message();

  default int getHttpStatusCode() {
    return status().value();
  }
}
