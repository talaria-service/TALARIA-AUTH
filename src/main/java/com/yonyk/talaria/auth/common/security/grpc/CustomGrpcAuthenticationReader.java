package com.yonyk.talaria.auth.common.security.grpc;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.yonyk.talaria.auth.common.security.util.JwtProvider;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGrpcAuthenticationReader implements GrpcAuthenticationReader {

  @Value("${jwt.access-token-header}")
  private String accessTokenHeader;

  private final JwtProvider jwtProvider;

  @Nullable
  @Override
  public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers)
      throws AuthenticationException {

    try {
      // 클라이언트 헤더에서 accessToken 가져오기
      String accessToken =
          headers.get(Metadata.Key.of(accessTokenHeader, Metadata.ASCII_STRING_MARSHALLER));
      // accessToken 파싱
      Authentication authentication = jwtProvider.getAuthentication(accessToken);
      // 인증객체 SecurityContextHolder 등록
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return authentication;
    } catch (Exception e) {
      log.error("accessToken 검증 예외발생: {}", e.getClass().getSimpleName());
      throw e;
    }
  }
}
