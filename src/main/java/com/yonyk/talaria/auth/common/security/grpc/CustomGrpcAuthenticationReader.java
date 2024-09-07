package com.yonyk.talaria.auth.common.security.grpc;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.yonyk.talaria.auth.common.security.util.JwtProvider;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;

@Slf4j
@RequiredArgsConstructor
public class CustomGrpcAuthenticationReader implements GrpcAuthenticationReader {

  @Value("${jwt.access-token-header}")
  private String accessTokenHeader;

  private JwtProvider jwtProvider;

  @Nullable
  @Override
  public Authentication readAuthentication(ServerCall<?, ?> call, Metadata headers)
      throws AuthenticationException {

    String accessToken =
        headers.get(Metadata.Key.of(accessTokenHeader, Metadata.ASCII_STRING_MARSHALLER));
    try {
      return jwtProvider.getAuthentication(accessToken);
    } catch (Exception e) {
      log.error("accessToken 검증 예외발생", e);
      throw new AuthenticationException("accessToken 검증 예외발생", e) {};
    }
  }
}
