package com.yonyk.talaria.auth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyk.talaria.auth.common.security.grpc.CustomGrpcAuthenticationReader;
import com.yonyk.talaria.auth.common.security.util.JwtProvider;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;

@Configuration
@RequiredArgsConstructor
public class GrpcSecurityConfig {

  // jwt 관리 클래스
  private final JwtProvider jwtProvider;

  @Bean
  public GrpcAuthenticationReader grpcAuthenticationReader() {
    return new CustomGrpcAuthenticationReader(jwtProvider);
  }
}
