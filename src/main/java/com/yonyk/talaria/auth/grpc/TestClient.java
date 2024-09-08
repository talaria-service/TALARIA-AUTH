package com.yonyk.talaria.auth.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClient {

  private AuthenticationClientInterceptor interceptor;

  public static void main(String[] args) {

    String accessTokenHeader = "Authorization";
    String accessToken =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW5zdW5nMTIiLCJhdXRob3JpdGllcyI6IlJPTEVfVVNFUiIsImV4cCI6MTcyNTc4NDU2OX0.NzdEQWQiSdkxAItZSabdl_koDuFYR5c7eAzz2avHWoI";

    // 인터셉터 생성
    AuthenticationClientInterceptor interceptor = new AuthenticationClientInterceptor();
    interceptor.setInterceptor(accessTokenHeader, accessToken);

    // GRPC 서버와의 채널 생성
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .intercept(interceptor)
            .build();

    // GRPC 스텁 생성
    AuthorizationServiceGrpc.AuthorizationServiceBlockingStub stub =
        AuthorizationServiceGrpc.newBlockingStub(channel);

    // 요청 생성
    AuthorizationProto.AuthRequest request = AuthorizationProto.AuthRequest.newBuilder().build();

    // 요청 전송 및 응답 수신
    AuthorizationProto.AuthResponse response = null;

    try {
      response = stub.getAuthentication(request);
      System.out.println("이름: " + response.getMemberName());
      System.out.println("권한: " + response.getMemberRoleList());
    } catch (StatusRuntimeException e) {
      log.error(e.getMessage());
    }
    // 채널 종료
    channel.shutdown();
  }
}
