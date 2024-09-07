package com.yonyk.talaria.auth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyk.talaria.auth.common.security.grpc.CustomGrpcAuthenticationReader;

import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;

@Configuration
public class GrpcSecurityConfig {
  @Bean
  public GrpcAuthenticationReader grpcAuthenticationReader() {
    return new CustomGrpcAuthenticationReader();
  }
}
