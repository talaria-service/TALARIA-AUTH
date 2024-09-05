package com.yonyk.talaria.auth.grpc;

import com.yonyk.HelloWorldServiceGrpc;
import com.yonyk.Talaria_Auth;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TestClient {
  public static void main(String[] args) {

    // GRPC 서버와의 채널 생성
    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    // GRPC 스텁 생성
    HelloWorldServiceGrpc.HelloWorldServiceBlockingStub stub =
        HelloWorldServiceGrpc.newBlockingStub(channel);
    // 요청 생성
    Talaria_Auth.HelloRequest request =
        Talaria_Auth.HelloRequest.newBuilder().setText("World").build();
    // 요청 전송 및 응답 수신
    Talaria_Auth.HelloResponse response = stub.hello(request);

    // 응답 출력
    System.out.println("응답 메세지: " + response.getText());

    // 채널 종료
    channel.shutdown();
  }
}
