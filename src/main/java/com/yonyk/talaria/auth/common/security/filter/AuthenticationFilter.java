package com.yonyk.talaria.auth.common.security.filter;

import java.io.IOException;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyk.talaria.auth.common.security.details.PrincipalDetails;
import com.yonyk.talaria.auth.common.security.handler.SecurityExceptionHandler;
import com.yonyk.talaria.auth.common.security.record.JwtRecord;
import com.yonyk.talaria.auth.common.security.redis.RefreshToken;
import com.yonyk.talaria.auth.common.security.redis.RefreshTokenRepository;
import com.yonyk.talaria.auth.common.security.util.CookieProvider;
import com.yonyk.talaria.auth.common.security.util.JwtProvider;
import com.yonyk.talaria.auth.controller.dto.LoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 로그인 인증 처리 필터
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  // jwt 관리 클래스
  private final JwtProvider jwtProvider;
  // 쿠키 관리 클래스
  private final CookieProvider cookieProvider;
  // redis 리포지토리
  private final RefreshTokenRepository refreshTokenRepository;
  // 예외 처리
  private final SecurityExceptionHandler securityExceptionHandler;

  // 클래스 생성 완료 후 초기화
  @PostConstruct
  public void init() {
    setFilterProcessesUrl("/api/members/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      // 회원가입 폼으로 들어온 정보를 LoginRecord로 변환
      LoginDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

      // getAuthenticationManager 클래스를 이용해 인증처리(이때 PrincipalDetailsService 사용됨)
      return getAuthenticationManager()
          .authenticate(
              // 회원가입 폼으로 들어온 정보가 특정 클래스로 래핑된다. null은 권한정보
              new UsernamePasswordAuthenticationToken(login.memberName(), login.password(), null));
    } catch (Exception e) {
      // 예외를 잡아서 unsuccessfulAuthentication 로 넘김
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }

  // 로그인 인증 성공시 실행되는 메소드
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {

    // 인증 객체를 이용해 jwt 생성
    JwtRecord jwtRecord = jwtProvider.getLoginToken(authResult);

    // 응답에 토큰 추가
    addTokensToResponse(response, jwtRecord);

    // redis에 refreshToken 추가
    storeRefreshToken(authResult, jwtRecord);

    log.info("로그인 성공 : 아이디({})", authResult.getName());
    securityExceptionHandler.sendResponse("로그인 성공", HttpStatus.OK, response);
  }

  // accessToken은 헤더에 저장, refreshToken은 쿠키에 저장
  private void addTokensToResponse(HttpServletResponse response, JwtRecord jwtRecord) {
    response.setHeader(jwtProvider.accessTokenHeader, jwtProvider.prefix + jwtRecord.accessToken());
    response.addCookie(cookieProvider.createRefreshTokenCookie(jwtRecord.refreshToken()));
  }

  // redis에 refreshToken 저장, memberId는 String으로 변환 후 저장
  private void storeRefreshToken(Authentication authResult, JwtRecord jwtRecord) {
    long memberId = ((PrincipalDetails) authResult.getPrincipal()).getMemberId();
    refreshTokenRepository.save(
        new RefreshToken(jwtRecord.refreshToken(), String.valueOf(memberId)));
  }

  // 로그인 인증 실패시 실행되는 메소드
  // attemptAuthentication 에서 발생한 예외들이 이곳으로 전달된다.
  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {

    // 예외 종류 확인
    Throwable e = failed.getCause();
    String message = securityExceptionHandler.getExceptionMessage((Exception) e);

    // 클라이언트에게 응답
    log.error("로그인 실패 : {}", message);
    securityExceptionHandler.sendResponse(message, HttpStatus.UNAUTHORIZED, response);
  }
}
