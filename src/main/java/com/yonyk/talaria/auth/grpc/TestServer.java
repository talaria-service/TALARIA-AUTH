package com.yonyk.talaria.auth.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestServer extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
  @Override
  public void getAuthentication(
      AuthenticationProto.AuthRequest request,
      StreamObserver<AuthenticationProto.AuthResponse> responseObserver) {

    super.getAuthentication(request, responseObserver);
  }
}
