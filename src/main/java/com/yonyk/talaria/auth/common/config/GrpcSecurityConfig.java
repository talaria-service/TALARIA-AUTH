package com.yonyk.talaria.auth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yonyk.talaria.auth.common.security.grpc.CustomServerInterceptor;
import com.yonyk.talaria.auth.grpc.AuthorizationServiceGrpc.*;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;

@Configuration
@RequiredArgsConstructor
public class GrpcSecurityConfig {

  @Bean
  public GrpcServerConfigurer grpcServerConfigurer(
      AuthorizationServiceImplBase authenticationService, CustomServerInterceptor interceptor) {
    return serverBuilder -> {
      // 서버 서비스 클래스 등록
      serverBuilder.addService(authenticationService);
      // 서버 인터셉터 클래스 등록
      serverBuilder.intercept(interceptor);
    };
  }
}
