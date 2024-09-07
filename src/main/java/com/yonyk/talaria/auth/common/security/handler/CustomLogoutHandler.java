package com.yonyk.talaria.auth.common.security.handler;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.yonyk.talaria.auth.common.security.redis.RefreshToken;
import com.yonyk.talaria.auth.common.security.redis.RefreshTokenRepository;
import com.yonyk.talaria.auth.common.security.util.CookieProvider;
import com.yonyk.talaria.auth.exception.CustomException;
import com.yonyk.talaria.auth.exception.exceptionType.SecurityExceptionType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomLogoutHandler implements LogoutHandler {

  private final CookieProvider cookieProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final SecurityExceptionHandler securityExceptionHandler;

  public CustomLogoutHandler(
      CookieProvider cookieProvider,
      RefreshTokenRepository refreshTokenRepository,
      SecurityExceptionHandler securityExceptionHandler) {
    this.cookieProvider = cookieProvider;
    this.refreshTokenRepository = refreshTokenRepository;
    this.securityExceptionHandler = securityExceptionHandler;
  }

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    try {
      // 리퀘스트에서 refreshToken 만료기한 0으로 설정
      Cookie findCookie = cookieProvider.deleteRefreshTokenCookie(request);
      // 리퀘스트에 쿠키 추가
      response.addCookie(findCookie);
      // redis에서 refreshToken 삭제
      deleteRefreshTokenInRedis(findCookie);
    } catch (Exception e) {
      log.error("로그아웃 오류: {}", e.getMessage());
      try {
        securityExceptionHandler.sendResponse(e.getMessage(), HttpStatus.NOT_FOUND, response);
      } catch (IOException ex) {
        log.error("로그아웃 오류: {}", e.getMessage());
      }
    }
  }

  // redis에서 refreshToken 삭제하는 메소드
  private void deleteRefreshTokenInRedis(Cookie findCookie) {
    // redis에서 refreshToken 찾기
    Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(findCookie.getValue());
    if (refreshToken.isPresent()) {
      // redis에서 refreshToken 삭제
      refreshTokenRepository.delete(refreshToken.get());
    } else {
      throw new CustomException(SecurityExceptionType.REFRESHTOKEN_NOT_FOUND);
    }
  }
}
