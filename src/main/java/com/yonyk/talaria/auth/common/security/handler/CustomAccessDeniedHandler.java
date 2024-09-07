package com.yonyk.talaria.auth.common.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.yonyk.talaria.auth.exception.exceptionType.SecurityExceptionType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final SecurityExceptionHandler securityExceptionHandler;

  public CustomAccessDeniedHandler(SecurityExceptionHandler securityExceptionHandler) {
    this.securityExceptionHandler = securityExceptionHandler;
  }

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {
    log.error("권한 검증 오류 발생");
    securityExceptionHandler.sendResponse(
        SecurityExceptionType.UNAUTHRIZED_REQUEST.getMessage(), HttpStatus.FORBIDDEN, response);
  }
}
