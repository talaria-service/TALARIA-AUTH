package com.yonyk.talaria.auth.common.security.grpc;

import org.springframework.stereotype.Component;

import com.yonyk.talaria.auth.common.security.handler.SecurityExceptionHandler;

import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomServerInterceptor implements ServerInterceptor {

  private final CustomGrpcAuthenticationReader customGrpcAuthenticationReader;
  private final SecurityExceptionHandler securityExceptionHandler;

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    try {
      // 인증객체 등록
      customGrpcAuthenticationReader.readAuthentication(serverCall, metadata);
    } catch (Exception e) {
      log.error("accessToken 검증 예외처리");
      // 예외 종류에 따른 메세지 생성
      String message = securityExceptionHandler.getExceptionMessage(e);
      // 상태코드
      Status status = Status.UNAUTHENTICATED.withDescription(message);
      // 클라이언트에게 응답처리
      serverCall.close(status, new Metadata());
      // 예외처리 후 빈 값을 리턴
      return new ServerCall.Listener<ReqT>() {};
    }
    // 예외가 없다면 서비스 클래스로 요청을 넘김
    return serverCallHandler.startCall(serverCall, metadata);
  }
}
