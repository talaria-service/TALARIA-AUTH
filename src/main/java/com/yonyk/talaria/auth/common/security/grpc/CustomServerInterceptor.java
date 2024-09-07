package com.yonyk.talaria.auth.common.security.grpc;

import com.yonyk.talaria.auth.common.security.handler.SecurityExceptionHandler;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomServerInterceptor implements ServerInterceptor {

  private final SecurityExceptionHandler securityExceptionHandler;

  public CustomServerInterceptor(SecurityExceptionHandler securityExceptionHandler) {
    this.securityExceptionHandler = securityExceptionHandler;
  }

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {

    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
        serverCallHandler.startCall(new ExceptionHandlingServerCall<>(serverCall), metadata)) {

      /** 클라이언트의 요청 처리를 시도합니다. 예외가 발생하면 handleException 메서드가 호출됩니다. */
      @Override
      public void onHalfClose() {
        try {
          super.onHalfClose();
        } catch (Exception e) {
          handleException(serverCall, e);
        }
      }
    };
  }

  private <RespT> void handleException(ServerCall<RespT, ?> call, Exception e) {
    // 예외 종류에 따른 메세지 생성
    String message = securityExceptionHandler.getExceptionMessage(e);
    // 상태코드
    Status status = Status.UNAUTHENTICATED.withDescription(message);
    // 예외 로그 기록
    log.error("accessToken 검증 예외 발생: ", e);
    // gRPC 호출 종료 및 상태 코드 반환
    call.close(status, new Metadata());
  }

  private static class ExceptionHandlingServerCall<ReqT, RespT>
      extends ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> {
    protected ExceptionHandlingServerCall(ServerCall<ReqT, RespT> delegate) {
      super(delegate);
    }
  }
}
