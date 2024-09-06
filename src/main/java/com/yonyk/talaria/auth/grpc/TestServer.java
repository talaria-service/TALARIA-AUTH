package com.yonyk.talaria.auth.grpc;

import com.yonyk.HelloWorldServiceGrpc;
import com.yonyk.Talaria_Auth.*;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestServer extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {
  @Override
  public void hello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
    // 비즈니스 로직 구현
    HelloResponse response =
        HelloResponse.newBuilder().setText("Hello, " + request.getText()).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
