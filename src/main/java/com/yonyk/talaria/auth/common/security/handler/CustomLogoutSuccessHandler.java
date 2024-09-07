package com.yonyk.talaria.auth.common.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

  private final SecurityExceptionHandler securityExceptionHandler;

  public CustomLogoutSuccessHandler(SecurityExceptionHandler securityExceptionHandler) {
    this.securityExceptionHandler = securityExceptionHandler;
  }

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    log.info("로그아웃 성공");
    securityExceptionHandler.sendResponse("로그아웃 성공", HttpStatus.OK, response);
  }
}
